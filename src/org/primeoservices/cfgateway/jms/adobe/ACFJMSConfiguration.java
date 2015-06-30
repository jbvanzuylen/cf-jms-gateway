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
