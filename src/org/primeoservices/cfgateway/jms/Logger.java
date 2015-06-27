package org.primeoservices.cfgateway.jms;

public interface Logger
{
	public void debug(String message);
	
	public void debug(String message, Throwable t);
	
	public void info(String message);
	
	public void info(String message, Throwable t);
	
	public void warn(String message);
	
	public void warn(String message, Throwable t);
	
	public void error(String message);
	
	public void error(String message, Throwable t);
	
	public void fatal(String message);
	
	public void fatal(String message, Throwable t);
}
