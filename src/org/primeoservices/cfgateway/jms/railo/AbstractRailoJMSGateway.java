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
package org.primeoservices.cfgateway.jms.railo;

import java.io.IOException;
import java.util.Map;

import org.primeoservices.cfgateway.jms.JMSConfiguration;
import org.primeoservices.cfgateway.jms.JMSExchanger;
import org.primeoservices.cfgateway.jms.JMSGateway;
import org.primeoservices.cfgateway.jms.Logger;
import org.primeoservices.cfgateway.jms.StopOnShutdownTask;

import railo.runtime.gateway.Gateway;

public abstract class AbstractRailoJMSGateway implements JMSGateway, Gateway
{
  private String id;

  private int state = Gateway.STOPPED;

  private JMSConfiguration config;

  private Logger log;

  @SuppressWarnings({"rawtypes", "unchecked"})
  protected void init(final String id, final Map config, final Logger log)
  {
    this.id = id;
    this.config = new RailoJMSConfiguration(config);
    this.log = log;
    Runtime.getRuntime().addShutdownHook(new Thread(new StopOnShutdownTask(this)));
  }

  @Override
  public String getId()
  {
    return this.id;
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
  public Object getHelper()
  {
    return null;
  }

  @Override
  public int getState()
  {
    return this.state;
  }

  @Override
  public boolean isRunning()
  {
    return this.state == RUNNING;
  }

  @Override
  public void start() throws Exception
  {
    this.doStart();
  }

  @Override
  public void doStart() throws IOException
  {
    if (this.isRunning()) return;
    this.state = STARTING;
    final ClassLoader loader = Thread.currentThread().getContextClassLoader();
    try
    {
      Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
      this.getExchanger().start();
      this.state = RUNNING;
    }
    catch (Throwable t)
    {
      this.state = FAILED;
      this.log.error("Unable to start gateway", t);
      final IOException ex = new IOException("Unable to start gateway", t);
      throw ex;
    }
    finally
    {
      Thread.currentThread().setContextClassLoader(loader);
    }
  }

  @Override
  public void stop() throws Exception
  {
    this.doStop();
  }

  @Override
  public void doStop() throws IOException
  {
    if (!this.isRunning()) return;
    this.state = STOPPING;
    final ClassLoader loader = Thread.currentThread().getContextClassLoader();
    try
    {
      Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
      this.getExchanger().stop();
      this.state = STOPPED;
    }
    catch (Throwable t)
    {
      this.state = FAILED;
      this.log.error("Unable to stop gateway", t);
      final IOException ex = new IOException("Unable to stop gateway", t);
      throw ex;
    }
    finally
    {
      Thread.currentThread().setContextClassLoader(loader);
    }
  }

  @Override
  public void doRestart() throws IOException
  {
    this.doStop();
    this.doStart();
  }

  protected abstract JMSExchanger getExchanger();

  @Override
  public void handleError()
  {
    // TODO Auto-generated method stub
  }
}
