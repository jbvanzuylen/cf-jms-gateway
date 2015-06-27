package org.primeoservices.cfgateway.jms.railo;

import java.io.IOException;
import java.util.Map;

import org.primeoservices.cfgateway.jms.JMSConfiguration;
import org.primeoservices.cfgateway.jms.JMSExchanger;
import org.primeoservices.cfgateway.jms.JMSGateway;
import org.primeoservices.cfgateway.jms.Logger;
import org.primeoservices.cfgateway.jms.StopOnShutdownTask;

import railo.runtime.gateway.GatewayException;
import railo.runtime.gateway.GatewayPro;

public abstract class AbstractRailoJMSGateway implements JMSGateway, GatewayPro
{
	private String id;

	private int state = GatewayPro.STOPPED;
	
	private JMSConfiguration config;
	
	private Logger log;

	@SuppressWarnings({"rawtypes", "unchecked"})
	protected void init(final String id, final Map config, final Logger log)
	{
		this.id = id;
		this.config = new RailoJMSConfiguration(config);
		this.log = log;
		Runtime.getRuntime().addShutdownHook(new Thread(new StopOnShutdownTask(this)));
	}
	
	@Override
	public String getId()
	{
		return this.id;
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
	public Object getHelper()
	{
		return null;
	}
	
	@Override
	public int getState()
	{
		return this.state;
	}
	
	@Override
	public boolean isRunning()
	{
		return this.state == RUNNING;
	}

	@Override
	public void start() throws Exception
	{
		this.doStart();
	}

	@Override
	public void doStart() throws IOException
	{
		if (this.isRunning()) return;
		this.state = STARTING;
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try
		{
			Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
			this.getExchanger().start();
			this.state = RUNNING;
		}
		catch (Throwable t)
		{
			this.state = FAILED;
			this.log.error("Unable to start gateway", t);
			final GatewayException ge = new GatewayException("Unable to start gateway: " + t.getMessage());
			ge.initCause(t);
			throw ge;
		}
		finally
		{
			Thread.currentThread().setContextClassLoader(loader);
		}
	}

	@Override
	public void stop() throws Exception
	{
		this.doStop();
	}

	@Override
	public void doStop() throws IOException
	{
		if (!this.isRunning()) return;
		this.state = STOPPING;
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try
		{
			Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
			this.getExchanger().stop();
			this.state = STOPPED;
		}
		catch (Throwable t)
		{
			this.state = FAILED;
			this.log.error("Unable to stop gateway", t);
			final GatewayException ge = new GatewayException("Unable to stop gateway: " + t.getMessage());
			ge.initCause(t);
			throw ge;
		}
		finally
		{
			Thread.currentThread().setContextClassLoader(loader);
		}
	}

	@Override
	public void doRestart() throws IOException
	{
		this.doStop();
		this.doStart();
	}

	protected abstract JMSExchanger getExchanger();

	@Override
	public void handleError()
	{
		// TODO Auto-generated method stub
	}
}
