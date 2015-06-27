package org.primeoservices.cfgateway.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.NamingException;

import org.primeoservices.cfgateway.jms.utils.JMSUtils;

/**
 * 
 * @author Jean-Bernard van Zuylen
 */
public class JMSConnection
{
	private JMSGateway gateway;
	
	private Logger log;
	
	private Connection connection;

	/**
	 * Creates a new connection class for the specified gateway
	 * 
	 * @param gateway
	 */
	public JMSConnection(final JMSGateway gateway)
	{
		this.gateway = gateway;
		this.log = gateway.getLogger();
	}

	/**
	 * Connects to the JMS server
	 * 
	 * @throws JMSException
	 * @throws NamingException
	 */
	public void connect() throws JMSException, NamingException
	{
		final JMSConfiguration config = this.gateway.getConfiguration();
		final ConnectionFactory factory = config.getJndiContext().lookup(ConnectionFactory.class, config.getConnectionFactory());
		if (config.getUserName() != null && config.getUserName().length() > 0)
		{
			this.connection = factory.createConnection(config.getUserName(), config.getPassword());
		}
		else
		{
			this.connection = factory.createConnection();
		}
		if (config.getClientId() != null && config.getClientId().length() > 0)
		{
			this.connection.setClientID(config.getClientId());
		}
		this.connection.setExceptionListener(new DefaultExceptionListener(this.gateway));
		this.connection.start();
		this.log.debug("Connection opened");
	}
		
	/**
	 * Disconnects from the JMS server
	 * 
	 * @throws JMSException
	 */
	public void disconnect() throws JMSException
	{
		JMSUtils.closeQuietly(this.connection);
		this.connection = null;
	}
	
	/**
	 * Returns 
	 * 
	 * @throws Exception
	 */
	public Session createSession(final boolean transacted, final int acknowledgeMode) throws JMSException
	{
		return this.connection.createSession(transacted, acknowledgeMode);
	}
}
