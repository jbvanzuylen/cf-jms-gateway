package org.primeoservices.cfgateway.jms.adobe;

import org.primeoservices.cfgateway.jms.Logger;

public class ACFLogger implements Logger
{
  private coldfusion.eventgateway.Logger logger;

  public ACFLogger(final coldfusion.eventgateway.Logger logger)
  {
    this.logger = logger;
  }

  @Override
  public void debug(final String message)
  {
    this.logger.debug(message);
  }

  @Override
  public void debug(final String message, final Throwable t)
  {
    this.logger.debug(message, t);
  }

  @Override
  public void info(final String message)
  {
    this.logger.info(message);
  }

  @Override
  public void info(final String message, final Throwable t)
  {
    this.logger.info(message, t);
  }

  @Override
  public void warn(final String message)
  {
    this.logger.warn(message);
  }

  @Override
  public void warn(final String message, final Throwable t)
  {
    this.logger.warn(message, t);
  }

  @Override
  public void error(final String message)
  {
    this.logger.error(message);
  }

  @Override
  public void error(final String message, final Throwable t)
  {
    this.logger.error(message, t);
  }

  @Override
  public void fatal(final String message)
  {
    this.logger.fatal(message);
  }

  @Override
  public void fatal(final String message, final Throwable t)
  {
    this.logger.fatal(message, t);
  }
}
