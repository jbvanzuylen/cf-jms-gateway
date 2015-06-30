package org.primeoservices.cfgateway.jms.adobe;

import java.util.Map;

import org.primeoservices.cfgateway.jms.JMSExchanger;
import org.primeoservices.cfgateway.jms.JMSSender;

import coldfusion.eventgateway.CFEvent;

public class ACFJMSSenderGateway extends AbstractACFJMSGateway
{
  private JMSSender sender;

  public ACFJMSSenderGateway(final String gatewayID, final String configFilePath)
  {
    super(gatewayID, configFilePath);
    this.sender = new JMSSender(this);
  }

  @Override
  protected JMSExchanger getExchanger()
  {
    return this.sender;
  }

  @Override
  @SuppressWarnings("unchecked")
  public String outgoingMessage(final CFEvent event)
  {
    try
    {
      this.sender.postMessage(event.getData());
      return "SEND";
    }
    catch (Throwable t)
    {
      this.getLogger().error("Unable to post message to the JMS server", t);
      return "EXCEPTION: " + t.getMessage();
    }
  }

  @Override
  public void handleMessage(final Map<String, Object> data)
  {
    throw new UnsupportedOperationException();
  }
}
