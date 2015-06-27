package org.primeoservices.cfgateway.jms.railo;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.primeoservices.cfgateway.jms.Logger;

import railo.runtime.gateway.GatewayEnginePro;
import railo.runtime.gateway.GatewayPro;

public class RailoLogger implements Logger
{
	private GatewayPro gateway;
	
	private GatewayEnginePro engine;
	
	public RailoLogger(final GatewayPro gateway, final GatewayEnginePro engine)
	{
		this.gateway = gateway;
		this.engine = engine;
	}

	@Override
	public void debug(final String message)
	{
		this.log(GatewayEnginePro.LOGLEVEL_DEBUG, message);
	}

	@Override
	public void debug(final String message, final Throwable t)
	{
		this.log(GatewayEnginePro.LOGLEVEL_DEBUG, message, t);
	}

	@Override
	public void info(final String message)
	{
		this.log(GatewayEnginePro.LOGLEVEL_INFO, message);
	}

	@Override
	public void info(final String message, final Throwable t)
	{
		this.log(GatewayEnginePro.LOGLEVEL_INFO, message, t);
	}

	@Override
	public void warn(final String message)
	{
		this.log(GatewayEnginePro.LOGLEVEL_WARN, message);
	}

	@Override
	public void warn(final String message, final Throwable t)
	{
		this.log(GatewayEnginePro.LOGLEVEL_WARN, message, t);
	}

	@Override
	public void error(final String message)
	{
		this.log(GatewayEnginePro.LOGLEVEL_ERROR, message);
	}

	@Override
	public void error(final String message, final Throwable t)
	{
		this.log(GatewayEnginePro.LOGLEVEL_ERROR, message, t);
	}

	@Override
	public void fatal(final String message)
	{
		this.log(GatewayEnginePro.LOGLEVEL_FATAL, message);
	}

	@Override
	public void fatal(final String message, final Throwable t) 
	{
		this.log(GatewayEnginePro.LOGLEVEL_FATAL, message, t);
	}
	
	private void log(final int level, final String message)
	{
		this.engine.log(this.gateway, level, message);
	}
	
	private void log(final int level, final String message, final Throwable t)
	{
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		pw.println(message);
		t.printStackTrace(pw);
		pw.close();
		this.engine.log(this.gateway, level, sw.toString());
	}
}
