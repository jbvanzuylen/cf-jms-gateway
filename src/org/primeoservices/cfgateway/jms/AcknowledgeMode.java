package org.primeoservices.cfgateway.jms;

import javax.jms.Session;

/**
 * The different modes to acknowledge the reception of a JMS message
 * 
 * @author Jean-Bernard van Zuylen
 */
public enum AcknowledgeMode
{
	/**
	 * 
	 */
	AUTO(Session.AUTO_ACKNOWLEDGE),
	/**
	 * 
	 */
	CLIENT(Session.CLIENT_ACKNOWLEDGE),
	/**
	 * 
	 */
	DUPS_OK(Session.DUPS_OK_ACKNOWLEDGE);
	
	private int ackMode;
	
	private AcknowledgeMode(final int ackMode)
	{
		this.ackMode = ackMode;
	}
	
	/**
	 * Returns 
	 * 
	 * @return
	 */
	public int toInt()
	{
		return this.ackMode;
	}

	/**
	 * 
	 * @param s
	 * 
	 * @return
	 */
	public static AcknowledgeMode fromString(final String s)
	{
		if (s == null || s.length() == 0) return null;
		for (AcknowledgeMode value : values())
		{
			if (value.toString().equalsIgnoreCase(s))
			{
				return value;
			}
		}
		throw new IllegalArgumentException("Unknown acknowledge mode: " + s);
	}
}
