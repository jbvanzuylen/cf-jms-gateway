package org.primeoservices.cfgateway.jms.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;

import org.primeoservices.cfgateway.jms.JMSConfiguration;
import org.primeoservices.cfgateway.jms.JMSSender;

/**
 * 
 * @author Jean-Bernard van Zuylen
 */
public class JMSUtils
{
  /**
   * This utilities class should never be initialized
   */
  private JMSUtils()
  {
  }

  /**
   * Unconditionally close a <code>Context</code>
   * 
   * @param context the context to be closed
   */
  public static void closeQuietly(final Context context)
  {
    if (context == null) return;
    try
    {
      context.close();
    }
    catch (Throwable t)
    {
      // ignore
    }
  }

  /**
   * Unconditionally close a <code>Connection</code>
   * 
   * @param connection the connection to be closed
   */
  public static void closeQuietly(final Connection connection)
  {
    if (connection == null) return;
    try
    {
      connection.stop();
      connection.close();
    }
    catch (Throwable t)
    {
      // ignore
    }
  }

  /**
   * Unconditionally close a <code>Session</code>
   * 
   * @param session the session to be closed
   */
  public static void closeQuietly(final Session session)
  {
    if (session == null) return;
    try
    {
      session.close();
    }
    catch (Throwable t)
    {
      // ignore
    }
  }

  /**
   * Unconditionally close a <code>Producer</code>
   * 
   * @param producer the producer to be closed
   */
  public static void closeQuietly(final MessageProducer producer)
  {
    if (producer == null) return;
    try
    {
      producer.close();
    }
    catch (Throwable t)
    {
      // ignore
    }
  }

  /**
   * Unconditionally close a <code>Consumer</code>
   * 
   * @param consumer the consumer to be closed
   */
  public static void closeQuietly(final MessageConsumer consumer)
  {
    if (consumer == null) return;
    try
    {
      consumer.close();
    }
    catch (Throwable t)
    {
      // ignore
    }
  }

  /**
   * 
   * @param session
   * @param data
   * @return
   * @throws JMSException
   */
  @SuppressWarnings("unchecked")
  public static Message createMessage(final Session session, final Map<String, Object> data) throws JMSException
  {
    final Object message = data.get(JMSSender.MESSAGE_KEY);
    if (message == null) throw new JMSException("Missing message");
    final Message msg = session.createTextMessage((String) message);
    final Object properties = data.get(JMSSender.PROPERTIES_KEY);
    if (properties != null) setProperties(msg, (Map<String, Object>) properties);
    return msg;
  }

  /**
   * 
   * @param message
   * @param properties
   * @throws JMSException
   */
  public static void setProperties(final Message message, final Map<String, Object> properties) throws JMSException
  {
    for (Map.Entry<String, Object> entry : properties.entrySet())
    {
      message.setObjectProperty(entry.getKey(), entry.getValue());
    }
  }

  /**
   * Extracts
   * 
   * @param config
   * @param data
   * @return
   */
  public static int extractDeliveryMode(final JMSConfiguration config, final Map<String, Object> data)
  {
    final Object value = data.get(JMSSender.PERSISTENT_KEY);
    final boolean persistent;
    if (value == null) persistent = config.isPersistent();
    else persistent = CastUtils.toBooleanValue(value);
    return (persistent) ? DeliveryMode.PERSISTENT : DeliveryMode.NON_PERSISTENT;
  }

  /**
   * Extracts
   * 
   * @param config
   * @param data
   * @return
   */
  public static int extractPriority(final JMSConfiguration config, final Map<String, Object> data)
  {
    final Object value = data.get(JMSSender.PRIORITY_KEY);
    return (value == null) ? config.getPriority() : CastUtils.toIntValue(value);
  }

  /**
   * 
   * @param config
   * @param data
   * @return
   */
  public static long extractTimeToLive(final JMSConfiguration config, final Map<String, Object> data)
  {
    final Object value = data.get(JMSSender.TTL_KEY);
    return (value == null) ? config.getTimeToLive() : CastUtils.toLongValue(value);
  }

  /**
   * Extract the properties of the specified <code>Message</code>
   * 
   * @param message the JMS message whose properties are to be extracted
   * 
   * @return
   * 
   * @throws JMSException in case of an error
   */
  @SuppressWarnings("unchecked")
  public static Map<String, Object> extractProperties(final Message message) throws JMSException
  {
    final List<String> names = Collections.list(message.getPropertyNames());
    final Map<String, Object> properties = new HashMap<String, Object>(names.size());
    for (String name : names)
    {
      properties.put(name, message.getObjectProperty(name));
    }
    return properties;
  }
}
