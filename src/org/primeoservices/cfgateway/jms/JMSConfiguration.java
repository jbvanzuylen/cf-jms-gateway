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

import javax.naming.NamingException;

public interface JMSConfiguration
{
  public static final boolean DEFAULT_PERSISTENT = false;

  public static final int DEFAULT_PRIORITY = 4;

  public static final long DEFAULT_TTL = 1000 * 60 * 60 * 24 * 7;

  public static final AcknowledgeMode DEFAULT_ACK_MODE = AcknowledgeMode.AUTO;

  public static final boolean DEFAULT_TRANSACTED = false;

  public static final boolean DEFAULT_DURABLE = false;

  public JMSJndiContext getJndiContext() throws NamingException;

  public String getConnectionFactory();

  public String getUserName();

  public String getPassword();

  public String getClientId();

  public boolean isPersistent();

  public int getPriority();

  public int getTimeToLive();

  public String getDestinationName();

  public String getSelector();

  public AcknowledgeMode getAckMode();

  public boolean isTransacted();

  public boolean isDurable();
}
