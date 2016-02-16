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
package org.primeoservices.cfgateway.jms;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.primeoservices.cfgateway.jms.utils.ConcurrentUtils;

public class DefaultExceptionListener implements ExceptionListener
{
  private JMSGateway gateway;

  private Logger log;

  /**
   * Creates a new listener for the specified gateway and JMS session
   * 
   * @param gateway
   */
  public DefaultExceptionListener(final JMSGateway gateway)
  {
    this.gateway = gateway;
    this.log = gateway.getLogger();
  }

  @Override
  public void onException(final JMSException exception)
  {
    this.log.error("JMS connection error occured (" + this.gateway.getId() + ")", exception);
    try
    {
      this.gateway.stop();
    }
    catch (Exception e)
    {
      // ignore
    }
    ConcurrentUtils.execute(new StartGatewayTask());
  }

  public class StartGatewayTask implements Runnable
  {
    public void run()
    {
      boolean restarted = false;
      while (!restarted && !DefaultExceptionListener.this.gateway.isRunning())
      {
        try
        {
          DefaultExceptionListener.this.gateway.stop();
        }
        catch (Exception e)
        {
          DefaultExceptionListener.this.log.error("Unable to stop", e);
        }

        try
        {
          DefaultExceptionListener.this.log.error("Trying to restart JMS connection");
          DefaultExceptionListener.this.gateway.start();
          restarted = true;
        }
        catch (Exception e)
        {
          DefaultExceptionListener.this.log.error("Error trying to restart JMS connection", e);
          try
          {
            Thread.sleep(10000);
          }
          catch (Exception ie)
          {
            // ignored
          }
        }
      }
    }
  }
}
