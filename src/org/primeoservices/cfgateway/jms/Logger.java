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

public interface Logger
{
  public void debug(String message);

  public void debug(String message, Throwable t);

  public void info(String message);

  public void info(String message, Throwable t);

  public void warn(String message);

  public void warn(String message, Throwable t);

  public void error(String message);

  public void error(String message, Throwable t);

  public void fatal(String message);

  public void fatal(String message, Throwable t);
}
