package org.primeoservices.cfgateway.jms.railo;

import java.io.IOException;
import java.util.Map;

import org.primeoservices.cfgateway.jms.JMSExchanger;
import org.primeoservices.cfgateway.jms.JMSSender;

import railo.runtime.gateway.GatewayEngine;

public class RailoJMSSenderGateway extends AbstractRailoJMSGateway
{
  private JMSSender sender;

  @Override
  @SuppressWarnings("rawtypes")
  public void init(final GatewayEngine engine, final String id, final String cfcPath, final Map config)
  {
    this.init(id, config, new RailoLogger(this, engine));
    this.sender = new JMSSender(this);
  }

  @Override
  protected JMSExchanger getExchanger()
  {
    return this.sender;
  }

  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  public String sendMessage(final Map data) throws IOException
  {
    final ClassLoader loader = Thread.currentThread().getContextClassLoader();
    try
    {
      Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
      this.sender.postMessage(data);
      return "SEND";
    }
    catch (Throwable t)
    {
      final IOException ex = new IOException("Unable to send message", t);
      throw ex;
    }
    finally
    {
      Thread.currentThread().setContextClassLoader(loader);
    }
  }

  @Override
  public void handleMessage(final Map<String, Object> data)
  {
    throw new UnsupportedOperationException();
  }
}
