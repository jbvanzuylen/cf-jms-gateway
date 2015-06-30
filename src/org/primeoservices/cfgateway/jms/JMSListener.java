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

public class JMSListener extends JMSExchanger implements MessageListener
{
  private static final String SUBSCRIBER_NAME_SUFFIX = ".subscriber";

  private Session session;

  private MessageConsumer consumer;

  // private int failureCount = 0;

  /**
   * Creates a new listener for the specified gateway and JMS session
   * 
   * @param gateway
   * @param session
   */
  public JMSListener(final JMSGateway gateway)
  {
    super(gateway);
  }

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

  @Override
  public void stop() throws Exception
  {
    JMSUtils.closeQuietly(this.consumer);
    this.consumer = null;
    JMSUtils.closeQuietly(this.session);
    this.session = null;
    this.getConnection().disconnect();
  }

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
   * private void onMessageFailure(final Message message) { this.failureCount++; if (this.failureCount > maxFailure && maxFailure > -1) { // Maximum
   * retries reached this.ackMessage(message); } else {
   * 
   * } }
   * 
   * 
   * private void ackMessage(final Message message) { message.acknowledge(); this.failureCount = 0; if (this.session.getTransacted())
   * this.session.commit();
   * 
   * 
   * }
   **/

  /**
   * Class that allows the
   * 
   * @author Jean-Bernard van Zuylen
   */
  public class Callback
  {
    private Message message;

    private Session session;

    /**
     * 
     * 
     * @param message
     * @param session
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
