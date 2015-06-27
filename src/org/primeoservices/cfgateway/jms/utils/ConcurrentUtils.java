package org.primeoservices.cfgateway.jms.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ConcurrentUtils
{
  private static final ExecutorService DEFAULT_EXECUTOR = Executors.newCachedThreadPool();

  private static final ScheduledExecutorService DEFAULT_SCHEDULED_EXECUTOR = Executors.newScheduledThreadPool(10);

  /**
   * This utilities class should never be initialized
   */
  private ConcurrentUtils()
  {
  }

  public static Future<Object> execute(final Runnable task)
  {
    return DEFAULT_EXECUTOR.submit(Executors.callable(task));
  }

  public static <T> ScheduledFuture<T> schedule(final Callable<T> callable, final long delay, final TimeUnit timeUnit)
  {
    return DEFAULT_SCHEDULED_EXECUTOR.schedule(callable, delay, timeUnit);
  }
}
