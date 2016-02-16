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

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.primeoservices.cfgateway.jms.utils.JMSUtils;

/**
 * A class handling the connection between the event gateway and the JMS provider
 * 
 * @author Jean-Bernard van Zuylen
 */
public class JMSConnection
{
  private JMSGateway gateway;

  private Logger log;

  private Connection connection;

  /**
   * Creates a new connection object for the specified gateway
   * 
   * @param gateway the gateway for which the connection object is to be created
   * 
   * @return the connection object just created
   */
  public JMSConnection(final JMSGateway gateway)
  {
    this.gateway = gateway;
    this.log = gateway.getLogger();
  }

  /**
   * Connects to the JMS provider
   * 
   * @throws Exception in case of an error when connecting
   */
  public void connect() throws Exception
  {
    final JMSConfiguration config = this.gateway.getConfiguration();
    final ConnectionFactory factory = config.getJndiContext().lookup(ConnectionFactory.class, config.getConnectionFactory());
    if (config.getUserName() != null && config.getUserName().length() > 0)
    {
      this.connection = factory.createConnection(config.getUserName(), config.getPassword());
    }
    else
    {
      this.connection = factory.createConnection();
    }
    if (config.getClientId() != null && config.getClientId().length() > 0)
    {
      this.connection.setClientID(config.getClientId());
    }
    this.connection.setExceptionListener(new DefaultExceptionListener(this.gateway));
    this.connection.start();
    this.log.debug("Connection opened");
  }

  /**
   * Disconnects from the JMS provider
   * 
   * @throws Exception in case of an error when disconnecting
   */
  public void disconnect() throws Exception
  {
    JMSUtils.closeQuietly(this.connection);
    this.connection = null;
  }

  /**
   * Creates a new session for this connection
   * 
   * @param transacted true if the session to be created is transacted, false otherwise
   * @param acknowledgeMode indicates whether the event gateway will acknowledge the messages received
   * 
   * @return the session just created
   * 
   * @throws Exception if case of an error when creating a new session
   */
  public Session createSession(final boolean transacted, final int acknowledgeMode) throws Exception
  {
    return this.connection.createSession(transacted, acknowledgeMode);
  }
}
