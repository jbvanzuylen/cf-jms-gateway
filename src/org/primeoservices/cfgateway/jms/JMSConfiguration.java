package org.primeoservices.cfgateway.jms;

import javax.naming.NamingException;

public interface JMSConfiguration
{
	public static final boolean DEFAULT_PERSISTENT = false;

	public static final int DEFAULT_PRIORITY = 4;
	
	public static final long DEFAULT_TTL = 1000 * 60 * 60 * 24 * 7;
	
	public static final AcknowledgeMode DEFAULT_ACK_MODE = AcknowledgeMode.AUTO;
	
	public static final boolean DEFAULT_TRANSACTED = false;
	
	public static final boolean DEFAULT_DURABLE = false;
	
	public JMSJndiContext getJndiContext() throws NamingException;
	
	public String getConnectionFactory();
	
	public String getUserName();
	
	public String getPassword();
	
	public String getClientId();
	
	public boolean isPersistent();
	
	public int getPriority();
	
	public int getTimeToLive();
	
	public String getDestinationName();
	
	public String getSelector();
	
	public AcknowledgeMode getAckMode();
	
	public boolean isTransacted();
	
	public boolean isDurable();
}
