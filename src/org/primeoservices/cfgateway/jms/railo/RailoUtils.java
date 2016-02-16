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
package org.primeoservices.cfgateway.jms.railo;

import java.io.PrintWriter;
import java.io.StringWriter;

import railo.loader.engine.CFMLEngineFactory;
import railo.runtime.type.Struct;

/**
 * @author Jean-Bernard van Zuylen
 */
public class RailoUtils
{
  /**
   * This utilities class should never be initialized
   */
  private RailoUtils()
  {
  }

  /**
   * Creates a new structure
   * 
   * @return the structure just created
   */
  public static Struct createStruct()
  {
    return CFMLEngineFactory.getInstance().getCreationUtil().createStruct();
  }

  /**
   * Creates a log message from specified <code>Throwable</code>
   * 
   * @param t the <code>Throwable</code> for which the log message is to be created
   * 
   * @return a message to be logged for the given <code>Throwable</code>
   */
  public static String createLogMessage(final Throwable t)
  {
    final StringWriter sw = new StringWriter();
    final PrintWriter pw = new PrintWriter(sw);
    pw.println(t.getMessage());
    t.printStackTrace(pw);
    pw.close();
    return sw.toString();
  }
}
