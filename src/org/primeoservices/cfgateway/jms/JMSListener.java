/*
 * Copyright 2015 Jean-Bernard van Zuylen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primeoservices.cfgateway.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.primeoservices.cfgateway.jms.utils.JMSUtils;

/**
 * A class to receive messages from a JMS provider and pass them a event gateway
 * 
 * @author Jean-Bernard van Zuylen
 */
public class JMSListener extends JMSExchanger implements MessageListener
{
  private static final String SUBSCRIBER_NAME_SUFFIX = ".subscriber";

  private Session session;

  private MessageConsumer consumer;

  /**
   * Creates a new listener object for the specified event gateway
   * 
   * @param gateway the gateway for which the listener object is to be created
   * 
   * @return the listener object just created
   */
  public JMSListener(final JMSGateway gateway)
  {
    super(gateway);
  }

  /**
   * Starts this listener
   * 
   * @throws Exception in case of an error when starting
   */
  @Override
  public void start() throws Exception
  {
    final JMSConnection conn = this.getConnection();
    conn.connect();
    final JMSConfiguration config = this.getGateway().getConfiguration();
    this.session = conn.createSession(config.isTransacted(), config.getAckMode().toInt());
    final Destination destination = config.getJndiContext().lookup(Destination.class, config.getDestinationName());
    if (Topic.class.isAssignableFrom(destination.getClass()) && config.isDurable())
    {
      this.consumer = this.session.createDurableSubscriber((Topic) destination, this.getGateway().getId() + SUBSCRIBER_NAME_SUFFIX, config.getSelector(), false);
    }
    else
    {
      this.consumer = this.session.createConsumer(destination, config.getSelector(), false);
    }
    this.consumer.setMessageListener(this);
    this.getLog().debug("Message consumer started");
  }

  /**
   * Stops this listener
   * 
   * @throws Exception if case of an error when stopping
   */
  @Override
  public void stop() throws Exception
  {
    JMSUtils.closeQuietly(this.consumer);
    this.consumer = null;
    JMSUtils.closeQuietly(this.session);
    this.session = null;
    this.getConnection().disconnect();
  }

  /**
   * Passes the specified message to the event gateway
   * 
   * @param message the message to be passed to the event gateway
   */
  @Override
  public void onMessage(final Message message)
  {
    if (!TextMessage.class.isAssignableFrom(message.getClass()))
    {
      this.getLog().warn("Currently only supporting text messages. Skip it.");
      return;
    }
    try
    {
      final Map<String, Object> data = new HashMap<String, Object>();
      data.put("callback", new Callback(message, this.session));
      data.put("properties", JMSUtils.extractProperties(message));
      data.put("message", ((TextMessage) message).getText());
      this.getGateway().handleMessage(data);
    }
    catch (Throwable t)
    {
      this.getLog().error("Error while delivering message", t);
    }
  }

  /**
   * A class that allows the event gateway to acknowledge, commit or roll back the current message
   */
  public class Callback
  {
    private Message message;

    private Session session;

    /**
     * Creates a new <code>Callback</code> object specified message and session
     * 
     * @param message the message for which the callback is to be created
     * @param session the session to the JMS server
     */
    private Callback(final Message message, final Session session)
    {
      this.message = message;
      this.session = session;
    }

    /**
     * Acknowledges the current message
     */
    public void acknowledge()
    {
      JMSListener.this.getLog().debug("Acknowledging message");
      try
      {
        this.message.acknowledge();
      }
      catch (JMSException e)
      {
        JMSListener.this.getLog().error("Error while acknowledging message", e);
      }
    }

    /**
     * Commits the current message
     */
    public void commit()
    {
      JMSListener.this.getLog().debug("Committing message");
      try
      {
        this.session.commit();
      }
      catch (JMSException e)
      {
        JMSListener.this.getLog().error("Error while committing message", e);
      }
    }

    /**
     * Rolls back the current message
     */
    public void rollback()
    {
      JMSListener.this.getLog().debug("Rolling back message");
      try
      {
        this.session.rollback();
      }
      catch (JMSException e)
      {
        JMSListener.this.getLog().error("Error while rolling back message", e);
      }
    }
  }
}
