package org.primeoservices.cfgateway.jms.adobe;

import org.primeoservices.cfgateway.jms.JMSConfiguration;
import org.primeoservices.cfgateway.jms.JMSExchanger;
import org.primeoservices.cfgateway.jms.JMSGateway;
import org.primeoservices.cfgateway.jms.Logger;

import coldfusion.eventgateway.GenericGateway;

public abstract class AbstractACFJMSGateway extends GenericGateway implements JMSGateway
{
  private JMSConfiguration config;

  private Logger log;

  public AbstractACFJMSGateway(final String gatewayID, final String configFilePath)
  {
    super(gatewayID);
    this.config = new ACFJMSConfiguration();
    this.log = new ACFLogger(this.getGatewayServices().getLogger());
  }

  @Override
  public String getId()
  {
    return this.getGatewayID();
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
  public boolean isRunning()
  {
    return this.getStatus() == RUNNING;
  }

  @Override
  protected void startGateway() throws Exception
  {
    this.getExchanger().start();
  }

  @Override
  protected void stopGateway() throws Exception
  {
    this.getExchanger().stop();
  }

  protected abstract JMSExchanger getExchanger();

  @Override
  public void handleError()
  {
    // TODO Auto-generated method stub
  }
}
