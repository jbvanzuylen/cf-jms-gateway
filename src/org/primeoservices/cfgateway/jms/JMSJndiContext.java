package org.primeoservices.cfgateway.jms;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.primeoservices.cfgateway.jms.utils.JMSUtils;

public class JMSJndiContext
{
	private Context context;
	
	private Map<String, Object> cache;

	public JMSJndiContext(final Properties env) throws NamingException
	{
		this.context = new InitialContext(env);
		this.cache = new HashMap<String, Object>();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T lookup(final Class<T> clazz, final String name) throws NamingException
	{
		T object = (T) this.cache.get(name);
		if (object == null)
		{
			object = (T) this.context.lookup(name);
			this.cache.put(name, object);
		}
		return object;
	}
	
	@Override
	protected void finalize() throws Throwable
	{
		JMSUtils.closeQuietly(this.context);
		this.context = null;
	}
}
