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

import java.util.Map;

import org.primeoservices.cfgateway.jms.JMSExchanger;
import org.primeoservices.cfgateway.jms.JMSSender;

import coldfusion.eventgateway.CFEvent;

public class ACFJMSSenderGateway extends AbstractACFJMSGateway
{
  private JMSSender sender;

  public ACFJMSSenderGateway(final String gatewayID, final String configFilePath)
  {
    super(gatewayID, configFilePath);
    this.sender = new JMSSender(this);
  }

  @Override
  protected JMSExchanger getExchanger()
  {
    return this.sender;
  }

  @Override
  @SuppressWarnings("unchecked")
  public String outgoingMessage(final CFEvent event)
  {
    try
    {
      this.sender.postMessage(event.getData());
      return "SEND";
    }
    catch (Throwable t)
    {
      this.getLogger().error("Unable to post message to the JMS server", t);
      return "EXCEPTION: " + t.getMessage();
    }
  }

  @Override
  public void handleMessage(final Map<String, Object> data)
  {
    throw new UnsupportedOperationException();
  }
}
