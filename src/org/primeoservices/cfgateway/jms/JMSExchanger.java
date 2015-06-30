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

/**
 * 
 * @author Jean-Bernard
 */
public abstract class JMSExchanger
{
  private JMSGateway gateway;

  private JMSConnection connection;

  private Logger log;

  /**
   * Creates a new <code>JMSExchanger</code> object for the specified gateway
   * 
   * @param gateway the gateway for which the 
   */
  protected JMSExchanger(final JMSGateway gateway)
  {
    this.gateway = gateway;
    this.log = gateway.getLogger();
    this.connection = new JMSConnection(gateway);
  }

  /**
   * Returns the gateway for this exchanger
   * 
   * @return the gateway for this exchanger
   */
  protected JMSGateway getGateway()
  {
    return this.gateway;
  }

  /**
   * Returns the connection to the JMS provider for this exchanger
   * 
   * @return the connection to the JMS provider for this exchanger
   */
  protected JMSConnection getConnection()
  {
    return this.connection;
  }

  protected Logger getLog()
  {
    return this.log;
  }

  /**
   * Starts this exchanger between the JMS provider and the gateway
   * 
   * @throws Exception
   */
  public abstract void start() throws Exception;

  /**
   * Stops this exchanger
   * 
   * @throws Exception
   */
  public abstract void stop() throws Exception;
}
