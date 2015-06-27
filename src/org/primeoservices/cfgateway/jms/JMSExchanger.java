package org.primeoservices.cfgateway.jms;

public abstract class JMSExchanger
{
	private JMSGateway gateway;
	
	private JMSConnection connection;
	
	private Logger log;
	
	protected JMSExchanger(final JMSGateway gateway)
	{
		this.gateway = gateway;
		this.log = gateway.getLogger();
		this.connection = new JMSConnection(gateway);
	}
	
	protected JMSGateway getGateway()
	{
		return this.gateway;
	}

	protected JMSConnection getConnection()
	{
		return this.connection;
	}

	protected Logger getLog()
	{
		return this.log;
	}

	public abstract void start() throws Exception;
	
	public abstract void stop() throws Exception;
}
