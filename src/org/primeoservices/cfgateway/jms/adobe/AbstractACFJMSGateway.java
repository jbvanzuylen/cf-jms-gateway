/*
 * Copyright 2016 Jean-Bernard van Zuylen
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
package org.primeoservices.cfgateway.jms.adobe;

import org.primeoservices.cfgateway.jms.JMSConfiguration;
import org.primeoservices.cfgateway.jms.JMSExchanger;
import org.primeoservices.cfgateway.jms.JMSGateway;
import org.primeoservices.cfgateway.jms.Logger;

import coldfusion.eventgateway.GenericGateway;

public abstract class AbstractACFJMSGateway extends GenericGateway implements JMSGateway
{
  private JMSConfiguration config;

  private Logger log;

  public AbstractACFJMSGateway(final String gatewayID, final String configFilePath)
  {
    super(gatewayID);
    this.config = new ACFJMSConfiguration();
    this.log = new ACFLogger(this.getGatewayServices().getLogger());
  }

  @Override
  public String getId()
  {
    return this.getGatewayID();
  }

  @Override
  public JMSConfiguration getConfiguration()
  {
    return this.config;
  }

  @Override
  public Logger getLogger()
  {
    return this.log;
  }

  @Override
  public boolean isRunning()
  {
    return this.getStatus() == RUNNING;
  }

  @Override
  protected void startGateway() throws Exception
  {
    this.getExchanger().start();
  }

  @Override
  protected void stopGateway() throws Exception
  {
    this.getExchanger().stop();
  }

  protected abstract JMSExchanger getExchanger();

  @Override
  public void handleError()
  {
    // TODO Auto-generated method stub
  }
}
