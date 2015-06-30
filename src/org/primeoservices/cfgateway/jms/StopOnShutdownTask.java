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

public class StopOnShutdownTask implements Runnable
{
  private JMSGateway gateway;

  private Logger log;

  public StopOnShutdownTask(final JMSGateway gateway)
  {
    this.gateway = gateway;
    this.log = gateway.getLogger();
  }

  @Override
  public void run()
  {
    this.log.error("Stopping gateway " + this.gateway.getId() + " on JVM shutdown");
    try
    {
      this.gateway.stop();
    }
    catch (Throwable t)
    {
      this.log.warn("Error while trying to stop gateway on JVM shutdown", t);
    }
  }
}
