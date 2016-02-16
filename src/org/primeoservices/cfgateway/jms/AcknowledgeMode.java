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
package org.primeoservices.cfgateway.jms;

import javax.jms.Session;

/**
 * The different modes to acknowledge the reception of a JMS message
 * 
 * @author Jean-Bernard van Zuylen
 */
public enum AcknowledgeMode
{
  AUTO(Session.AUTO_ACKNOWLEDGE),
  CLIENT(Session.CLIENT_ACKNOWLEDGE),
  DUPS_OK(Session.DUPS_OK_ACKNOWLEDGE);

  private int ackMode;

  private AcknowledgeMode(final int ackMode)
  {
    this.ackMode = ackMode;
  }

  /**
   * Returns
   * 
   * @return
   */
  public int toInt()
  {
    return this.ackMode;
  }

  /**
   * 
   * @param s
   * 
   * @return
   */
  public static AcknowledgeMode fromString(final String s)
  {
    if (s == null || s.length() == 0) return null;
    for (AcknowledgeMode value : values())
    {
      if (value.toString().equalsIgnoreCase(s))
      {
        return value;
      }
    }
    throw new IllegalArgumentException("Unknown acknowledge mode: " + s);
  }
}
