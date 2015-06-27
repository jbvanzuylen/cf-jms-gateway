package org.primeoservices.cfgateway.jms.railo;

import java.io.IOException;
import java.util.Map;

import org.primeoservices.cfgateway.jms.JMSExchanger;
import org.primeoservices.cfgateway.jms.JMSSender;

import railo.runtime.gateway.GatewayEnginePro;
import railo.runtime.gateway.GatewayException;

public class RailoJMSSenderGateway extends AbstractRailoJMSGateway
{
	private JMSSender sender;
	
	@Override
	@SuppressWarnings("rawtypes")
	public void init(final GatewayEnginePro engine, final String id, final String cfcPath, final Map config)
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
			final GatewayException ge = new GatewayException("Unable to send message: " + t.getMessage());
			ge.initCause(t);
			throw ge;
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
