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

import java.util.Map;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.primeoservices.cfgateway.jms.utils.JMSUtils;

/**
 * A class to send messages from the event gateway to the JMS provider
 * 
 * @author Jean-Bernard van Zuylen
 */
public class JMSSender extends JMSExchanger
{
  public static final String MESSAGE_KEY = "message";

  public static final String PROPERTIES_KEY = "properties";

  public static final String PRIORITY_KEY = "priority";

  public static final String TTL_KEY = "ttl";

  public static final String PERSISTENT_KEY = "persitent";

  private Session session;

  private MessageProducer producer;

  /**
   * Creates a new sender object for the specified gateway
   * 
   * @param gateway the gateway for which the sender object is to be created
   * 
   * @return the sender object just created
   */
  public JMSSender(final JMSGateway gateway)
  {
    super(gateway);
  }

  /**
   * Starts this sender
   * 
   * @throws Exception in case of an error when starting
   */
  @Override
  public void start() throws Exception
  {
    this.getConnection().connect();
    final JMSConfiguration config = this.getGateway().getConfiguration();
    this.session = this.getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
    final Destination destination = config.getJndiContext().lookup(Destination.class, config.getDestinationName());
    this.producer = this.session.createProducer(destination);
    this.getLog().debug("Message producer started");
  }

  /**
   * Stops this sender
   * 
   * @throws Exception in case of an error when stopping
   */
  @Override
  public void stop() throws Exception
  {
    JMSUtils.closeQuietly(this.producer);
    this.producer = null;
    JMSUtils.closeQuietly(this.session);
    this.session = null;
    this.getConnection().disconnect();
  }

  /**
   * Posts a message to the JMS provider with the specified data
   * 
   * @param data the data of the message to be posted
   * 
   * @throws Exception in case of an error when sending a message
   */
  public void postMessage(final Map<String, Object> data) throws Exception
  {
    final JMSConfiguration config = this.getGateway().getConfiguration();
    final int mode = JMSUtils.extractDeliveryMode(config, data);
    final int priority = JMSUtils.extractPriority(config, data);
    final long ttl = JMSUtils.extractTimeToLive(config, data);
    final Message message = JMSUtils.createMessage(this.session, data);
    this.producer.send(message, mode, priority, ttl);
  }
}
