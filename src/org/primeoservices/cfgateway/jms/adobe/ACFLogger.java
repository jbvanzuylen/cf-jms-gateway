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
package org.primeoservices.cfgateway.jms.adobe;

import org.primeoservices.cfgateway.jms.Logger;

public class ACFLogger implements Logger
{
  private coldfusion.eventgateway.Logger logger;

  public ACFLogger(final coldfusion.eventgateway.Logger logger)
  {
    this.logger = logger;
  }

  @Override
  public void debug(final String message)
  {
    this.logger.debug(message);
  }

  @Override
  public void debug(final String message, final Throwable t)
  {
    this.logger.debug(message, t);
  }

  @Override
  public void info(final String message)
  {
    this.logger.info(message);
  }

  @Override
  public void info(final String message, final Throwable t)
  {
    this.logger.info(message, t);
  }

  @Override
  public void warn(final String message)
  {
    this.logger.warn(message);
  }

  @Override
  public void warn(final String message, final Throwable t)
  {
    this.logger.warn(message, t);
  }

  @Override
  public void error(final String message)
  {
    this.logger.error(message);
  }

  @Override
  public void error(final String message, final Throwable t)
  {
    this.logger.error(message, t);
  }

  @Override
  public void fatal(final String message)
  {
    this.logger.fatal(message);
  }

  @Override
  public void fatal(final String message, final Throwable t)
  {
    this.logger.fatal(message, t);
  }
}
