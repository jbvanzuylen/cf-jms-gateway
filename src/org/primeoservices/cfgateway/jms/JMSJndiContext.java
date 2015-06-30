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
package org.primeoservices.cfgateway.jms;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.primeoservices.cfgateway.jms.utils.JMSUtils;

public class JMSJndiContext
{
  private Context context;

  private Map<String, Object> cache;

  public JMSJndiContext(final Properties env) throws NamingException
  {
    this.context = new InitialContext(env);
    this.cache = new HashMap<String, Object>();
  }

  @SuppressWarnings("unchecked")
  public <T> T lookup(final Class<T> clazz, final String name) throws NamingException
  {
    T object = (T) this.cache.get(name);
    if (object == null)
    {
      object = (T) this.context.lookup(name);
      this.cache.put(name, object);
    }
    return object;
  }

  @Override
  protected void finalize() throws Throwable
  {
    JMSUtils.closeQuietly(this.context);
    this.context = null;
  }
}
