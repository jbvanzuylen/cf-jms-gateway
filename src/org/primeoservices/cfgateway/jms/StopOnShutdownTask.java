package org.primeoservices.cfgateway.jms;

public class StopOnShutdownTask implements Runnable
{
  private JMSGateway gateway;

  private Logger log;

  public StopOnShutdownTask(final JMSGateway gateway)
  {
    this.gateway = gateway;
    this.log = gateway.getLogger();
  }

  @Override
  public void run()
  {
    this.log.error("Stopping gateway " + this.gateway.getId() + " on JVM shutdown");
    try
    {
      this.gateway.stop();
    }
    catch (Throwable t)
    {
      this.log.warn("Error while trying to stop gateway on JVM shutdown", t);
    }
  }
}
