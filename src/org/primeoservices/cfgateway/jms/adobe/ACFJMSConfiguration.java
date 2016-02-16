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

import javax.naming.NamingException;

import org.primeoservices.cfgateway.jms.AcknowledgeMode;
import org.primeoservices.cfgateway.jms.JMSConfiguration;
import org.primeoservices.cfgateway.jms.JMSJndiContext;

public class ACFJMSConfiguration implements JMSConfiguration
{

  @Override
  public JMSJndiContext getJndiContext() throws NamingException
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getConnectionFactory()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getUserName()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getPassword()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getClientId()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isPersistent()
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int getPriority()
  {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getTimeToLive()
  {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getDestinationName()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getSelector()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AcknowledgeMode getAckMode()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isTransacted()
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isDurable()
  {
    // TODO Auto-generated method stub
    return false;
  }

}
