package org.primeoservices.cfgateway.jms.adobe;

import java.util.Map;

import org.primeoservices.cfgateway.jms.JMSExchanger;
import org.primeoservices.cfgateway.jms.JMSListener;

import coldfusion.eventgateway.CFEvent;

public class ACFJMSListenerGateway extends AbstractACFJMSGateway
{
	private JMSListener listener;
	
	public ACFJMSListenerGateway(final String gatewayID, final String configFilePath)
	{
		super(gatewayID, configFilePath);
		this.listener = new JMSListener(this);
	}
	
	@Override
	protected JMSExchanger getExchanger()
	{
		return this.listener;
	}

	@Override
	public String outgoingMessage(final CFEvent event)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void handleMessage(final Map<String, Object> data)
	{
		final CFEvent event = new CFEvent(this.getId());
		event.setGatewayType(GATEWAY_TYPE);
		event.setOriginatorID(this.getConfiguration().getDestinationName());
		event.setData(data);
		this.sendMessage(event);
	}
}
