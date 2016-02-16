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
import org.primeoservices.cfgateway.jms.JMSListener;

import coldfusion.eventgateway.CFEvent;

public class ACFJMSListenerGateway extends AbstractACFJMSGateway
{
  private JMSListener listener;

  public ACFJMSListenerGateway(final String gatewayID, final String configFilePath)
  {
    super(gatewayID, configFilePath);
    this.listener = new JMSListener(this);
  }

  @Override
  protected JMSExchanger getExchanger()
  {
    return this.listener;
  }

  @Override
  public String outgoingMessage(final CFEvent event)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public void handleMessage(final Map<String, Object> data)
  {
    final CFEvent event = new CFEvent(this.getId());
    event.setGatewayType(GATEWAY_TYPE);
    event.setOriginatorID(this.getConfiguration().getDestinationName());
    event.setData(data);
    this.sendMessage(event);
  }
}
