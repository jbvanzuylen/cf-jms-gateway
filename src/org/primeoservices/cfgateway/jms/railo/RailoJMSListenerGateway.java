package org.primeoservices.cfgateway.jms.railo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.primeoservices.cfgateway.jms.JMSExchanger;
import org.primeoservices.cfgateway.jms.JMSListener;

import railo.runtime.gateway.GatewayEnginePro;
import railo.runtime.gateway.GatewayException;

public class RailoJMSListenerGateway extends AbstractRailoJMSGateway
{
	private GatewayEnginePro engine;
	
	private JMSListener listener;

	@Override
	@SuppressWarnings("rawtypes")
	public void init(final GatewayEnginePro engine, final String id, final String cfcPath, final Map config)
	{
		this.init(id, config, new RailoLogger(this, engine));
		this.engine = engine;
		this.listener = new JMSListener(this);
	}

	@Override
	protected JMSExchanger getExchanger()
	{
		return this.listener;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public String sendMessage(final Map data) throws GatewayException
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void handleMessage(final Map<String, Object> data) throws IOException
	{
		final RailoEvent event = new RailoEvent(this.getId());
		event.setGatewayType(GATEWAY_TYPE);
		event.setOriginatorId(this.getConfiguration().getDestinationName());
		event.setData(data);
		final Map<String, RailoEvent> arguments = new HashMap<String, RailoEvent>(1);
		arguments.put("event", event);
		final boolean success = this.engine.invokeListener(this, LISTENER_INVOKE_METHOD, arguments);
		if (!success) throw new IOException("Error while invoke listener cfc");
	}
}
