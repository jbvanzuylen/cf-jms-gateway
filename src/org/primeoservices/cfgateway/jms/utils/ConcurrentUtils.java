/*
 * Copyright 2015 Jean-Bernard van Zuylen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
