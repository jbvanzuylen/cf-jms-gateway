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
package org.primeoservices.cfgateway.jms.lucee;

import java.io.IOException;
import java.util.Map;

import org.primeoservices.cfgateway.jms.JMSExchanger;
import org.primeoservices.cfgateway.jms.JMSListener;

import lucee.runtime.gateway.GatewayEngine;
import lucee.runtime.type.Collection.Key;
import lucee.runtime.type.Struct;

public class LuceeJMSListenerGateway extends AbstractLuceeJMSGateway
{
  private static final Key GATEWAY_ID_KEY = LuceeUtils.createKey("gatewayId");

  private static final Key GATEWAY_TYPE_KEY = LuceeUtils.createKey("gatewayType");

  private static final Key DATA_KEY = LuceeUtils.createKey("data");
  
  private static final Key EVENT_KEY = LuceeUtils.createKey("event");

  private GatewayEngine engine;

  private JMSListener listener;

  @Override
  @SuppressWarnings("rawtypes")
  public void init(final GatewayEngine engine, final String id, final String cfcPath, final Map config)
  {
    this.init(id, config, new LuceeLogger(this, engine));
    this.engine = engine;
    this.listener = new JMSListener(this);
  }

  @Override
  protected JMSExchanger getExchanger()
  {
    return this.listener;
  }

  @Override
  @SuppressWarnings("rawtypes")
  public String sendMessage(final Map data) throws IOException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  @SuppressWarnings("unchecked")
  public void handleMessage(final Map<String, Object> data) throws IOException
  {
    final Struct event = LuceeUtils.createStruct();
    event.setEL(GATEWAY_ID_KEY, this.getId());
    event.setEL(GATEWAY_TYPE_KEY, GATEWAY_TYPE);
    event.setEL(DATA_KEY, LuceeUtils.toStruct(data));
    final Struct arguments = LuceeUtils.createStruct();
    arguments.put(EVENT_KEY, event);
    final boolean success = this.engine.invokeListener(this, LISTENER_INVOKE_METHOD, arguments);
    if (!success) throw new IOException("Error while invoke listener cfc");
  }
}
