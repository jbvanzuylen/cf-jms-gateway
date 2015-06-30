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
