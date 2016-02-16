/*
 * Copyright 2016 Jean-Bernard van Zuylen
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

public class CastUtils
{
  /**
   * This utilities class should never be initialized
   */
  private CastUtils()
  {
  }

  public static boolean toBooleanValue(final Object o)
  {
    if (o instanceof Boolean) return ((Boolean) o).booleanValue();
    return false;
  }

  public static int toIntValue(final Object o)
  {
    if (o instanceof Integer) return ((Integer) o).intValue();
    return 0;
  }

  public static long toLongValue(final Object o)
  {
    if (o instanceof Long) return ((Long) o).longValue();
    return 0;
  }
}
