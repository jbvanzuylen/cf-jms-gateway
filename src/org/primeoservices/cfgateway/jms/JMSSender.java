package org.primeoservices.cfgateway.jms;

import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.NamingException;

import org.primeoservices.cfgateway.jms.utils.JMSUtils;

public class JMSSender extends JMSExchanger
{
	public static final String MESSAGE_KEY = "message";

	public static final String PROPERTIES_KEY = "properties";
	
	public static final String PRIORITY_KEY = "priority";
	
	public static final String TTL_KEY = "ttl";
	
	public static final String PERSISTENT_KEY = "persitent";

	private Session session;
	
	private MessageProducer producer;

	/**
	 * Creates a new JMS sender for the specified gateway
	 * 
	 * @param gateway
	 */
	public JMSSender(final JMSGateway gateway)
	{
		super(gateway);
	}

	@Override
	public void start() throws Exception
	{
		this.getConnection().connect();
		final JMSConfiguration config = this.getGateway().getConfiguration();
		this.session = this.getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
		final Destination destination = config.getJndiContext().lookup(Destination.class, config.getDestinationName());
		this.producer = this.session.createProducer(destination);
		this.getLog().debug("Message producer started");
	}

	@Override
	public void stop() throws Exception
	{
		JMSUtils.closeQuietly(this.producer);
		this.producer = null;
		JMSUtils.closeQuietly(this.session);
		this.session = null;
		this.getConnection().disconnect();
	}

	/**
	 * Posts a message to the JMS server with the specified data
	 * 
	 * @param data
	 * 
	 * @throws JMSException
	 * @throws NamingException
	 */
	public void postMessage(final Map<String, Object> data) throws JMSException, NamingException
	{
		final JMSConfiguration config = this.getGateway().getConfiguration();
		final int mode = JMSUtils.extractDeliveryMode(config, data);
		final int priority = JMSUtils.extractPriority(config, data);
		final long ttl = JMSUtils.extractTimeToLive(config, data);
		final Message message = JMSUtils.createMessage(this.session, data);
		this.producer.send(message, mode, priority, ttl);
	}
}
