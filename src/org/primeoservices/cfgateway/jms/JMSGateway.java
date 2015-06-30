package org.primeoservices.cfgateway.jms;

import java.io.IOException;
import java.util.Map;

/**
 * 
 * @author Jean-Bernard van Zuylen
 */
public interface JMSGateway
{
  public static final String GATEWAY_TYPE = "JMS";

  public static final String LISTENER_INVOKE_METHOD = "onIncomingMessage";

  /**
   * Returns the id of this gateway
   * 
   * @return the gateway id
   */
  public String getId();

  /**
   * Returns the configuration of this gateway
   * 
   * @return
   */
  public JMSConfiguration getConfiguration();

  /**
   * Returns the logger of this gateway
   * 
   * @return
   */
  public Logger getLogger();

  /**
   * Handles
   * 
   * @param data
   * @throws IOException
   */
  public void handleMessage(Map<String, Object> data) throws IOException;

  /**
   * Handles an error on this gateway
   */
  public void handleError();

  /**
   * Indicates if this gateway is running
   * 
   * @return true if the gateway is running, false otherwise
   */
  public boolean isRunning();

  /**
   * Starts this gateway
   * 
   * @throws Exception if an error occurs while starting the gateway
   */
  public void start() throws Exception;

  /**
   * Stops this gateway
   * 
   * @throws Exception if an error occurs while stopping the gateway
   */
  public void stop() throws Exception;
}
