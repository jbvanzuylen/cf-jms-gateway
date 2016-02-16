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
import org.primeoservices.cfgateway.jms.JMSSender;

import lucee.runtime.gateway.GatewayEngine;

public class LuceeJMSSenderGateway extends AbstractLuceeJMSGateway
{
  private JMSSender sender;

  @Override
  @SuppressWarnings("rawtypes")
  public void init(final GatewayEngine engine, final String id, final String cfcPath, final Map config)
  {
    this.init(id, config, new LuceeLogger(this, engine));
    this.sender = new JMSSender(this);
  }

  @Override
  protected JMSExchanger getExchanger()
  {
    return this.sender;
  }

  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  public String sendMessage(final Map data) throws IOException
  {
    final ClassLoader loader = Thread.currentThread().getContextClassLoader();
    try
    {
      Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
      this.sender.postMessage(data);
      return "SEND";
    }
    catch (Throwable t)
    {
      final IOException ex = new IOException("Unable to send message", t);
      throw ex;
    }
    finally
    {
      Thread.currentThread().setContextClassLoader(loader);
    }
  }

  @Override
  public void handleMessage(final Map<String, Object> data)
  {
    throw new UnsupportedOperationException();
  }
}
