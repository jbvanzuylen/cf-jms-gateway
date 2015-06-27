package org.primeoservices.cfgateway.jms.railo;

import java.util.Map;

import railo.runtime.type.Struct;
import railo.runtime.type.StructImpl;

public class RailoEvent extends StructImpl
{
	private static final long serialVersionUID = 6526079809320234235L;

	private static final String GATEWAY_ID_KEY = "GATEWAYID";
	
	private static final String GATEWAY_TYPE_KEY = "GATEWAYTYPE";
	
	private static final String ORIGINATOR_ID_KEY = "ORIGINATORID";

	private static final String DATA_KEY = "DATA";
	
	public RailoEvent(final String gatewayId)
	{
		this.put(GATEWAY_ID_KEY, gatewayId);
	}
	
	public void setGatewayType(final String gatewayType)
	{
		this.put(GATEWAY_TYPE_KEY, gatewayType);
	}
	
	public void setOriginatorId(final String originatorId)
	{
		this.put(ORIGINATOR_ID_KEY, originatorId);
	}
	
	public void setData(final Map<String, Object> data)
	{
		this.put(DATA_KEY, toStruct(data));
	}
	
	@SuppressWarnings("unchecked")
	private static Struct toStruct(final Map<String, Object> map)
	{
		if (map == null) return null;
		final Struct struct = new StructImpl();
		for (Map.Entry<String, Object> entry : map.entrySet())
		{
			final Object value = entry.getValue();
			if (Map.class.isAssignableFrom(value.getClass()))
			{
				struct.put(entry.getKey(), toStruct((Map<String, Object>) value));
			}
			else
			{
				struct.put(entry.getKey(), entry.getValue());
			}
		}
		return struct;
	}
}
