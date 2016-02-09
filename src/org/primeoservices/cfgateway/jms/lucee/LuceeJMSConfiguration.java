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
package org.primeoservices.cfgateway.jms.lucee;

import java.util.Map;
import java.util.Properties;

import javax.naming.NamingException;

import org.primeoservices.cfgateway.jms.AcknowledgeMode;
import org.primeoservices.cfgateway.jms.JMSConfiguration;
import org.primeoservices.cfgateway.jms.JMSJndiContext;

public class LuceeJMSConfiguration implements JMSConfiguration
{
  private static final String JNDI_PROPERTY_PREFIX = "jndi:";

  private static final String CONNECTION_FACTORY_KEY = "connectionFactory";

  private static final String USERNAME_KEY = "username";

  private static final String PASSWORD_KEY = "password";

  private static final String CLIENT_ID_KEY = "clientId";

  private static final String PERSISTENT_KEY = "persistent";

  private static final String PRIORITY_KEY = "priority";

  private static final String TTL_KEY = "ttl";

  private static final String DESTINATION_NAME_KEY = "destinationName";

  private static final String SELECTOR_KEY = "selector";

  private static final String ACK_MODE_KEY = "ackMode";

  private static final String TRANSACTED_KEY = "transacted";

  private static final String DURABLE_KEY = "durable";

  private Map<String, String> config;

  public LuceeJMSConfiguration(final Map<String, String> config)
  {
    this.config = config;
  }

  @Override
  public JMSJndiContext getJndiContext() throws NamingException
  {
    return this.createJndiContext();
  }

  private JMSJndiContext createJndiContext() throws NamingException
  {
    final Properties env = new Properties();
    for (Map.Entry<String, String> entry : this.config.entrySet())
    {
      if (entry.getKey().startsWith(JNDI_PROPERTY_PREFIX))
      {
        env.setProperty(entry.getKey().substring(JNDI_PROPERTY_PREFIX.length()), entry.getValue());
      }
    }
    return new JMSJndiContext(env);
  }

  @Override
  public String getConnectionFactory()
  {
    return this.config.get(CONNECTION_FACTORY_KEY);
  }

  @Override
  public String getUserName()
  {
    return this.config.get(USERNAME_KEY);
  }

  @Override
  public String getPassword()
  {
    return this.config.get(PASSWORD_KEY);
  }

  @Override
  public String getClientId()
  {
    return this.config.get(CLIENT_ID_KEY);
  }

  @Override
  public boolean isPersistent()
  {
    return Boolean.valueOf(this.config.get(PERSISTENT_KEY));
  }

  @Override
  public int getPriority()
  {
    return Integer.valueOf(this.config.get(PRIORITY_KEY));
  }

  @Override
  public int getTimeToLive()
  {
    return Integer.valueOf(this.config.get(TTL_KEY));
  }

  @Override
  public String getDestinationName()
  {
    return this.config.get(DESTINATION_NAME_KEY);
  }

  @Override
  public String getSelector()
  {
    return this.config.get(SELECTOR_KEY);
  }

  @Override
  public AcknowledgeMode getAckMode()
  {
    return AcknowledgeMode.fromString(this.config.get(ACK_MODE_KEY));
  }

  @Override
  public boolean isTransacted()
  {
    return Boolean.valueOf(this.config.get(TRANSACTED_KEY));
  }

  @Override
  public boolean isDurable()
  {
    return Boolean.valueOf(this.config.get(DURABLE_KEY));
  }

  /**
   * Validates the specified configuration
   * 
   * @param config the configuration to be validated
   * 
   * @throws Exception
   */
  public static void validate(final Map<String, String> config) throws Exception
  {
    final String priority = config.get(PRIORITY_KEY);
    try
    {
      Integer.valueOf(priority);
    }
    catch (Exception e)
    {
      throw new Exception("Priority must be numeric");
    }
  }
}
