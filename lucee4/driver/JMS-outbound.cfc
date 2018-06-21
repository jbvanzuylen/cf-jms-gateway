<cfcomponent extends="Gateway">
  <cfset config = createObject("java", "org.primeoservices.cfgateway.jms.lucee.LuceeJMSConfiguration") />

  <cfset fields = array(
    group("JNDI Context", "JNDI Context", 3),
    field("Provider URL", "jndi:java.naming.provider.url", "", true, "", "text"),
    field("Intial Context Factory", "jndi:java.naming.factory.initial", "", true, "", "text"),
    group("JMS Connection", "JMS Connection", 3),
    field("Connection Factory", "connectionFactory", "", true, "JNDI name of the factory to use to create a connection to the JMS server", "text"),
    field("User Name", "username", "", false, "The username to connect to the JMS server", "text"),
    field("Password", "password", "", false, "The password to connect to the JMS server", "password"),
    field("Client ID", "clientId", "", false, "", "text"),
    group("Outbound Options", "Outbound Options", 3),
    field("Destination Name", "destinationName", "", false, "JNDI name of the JMS destination", "text"),
    field("Persistent", "persistent", toString(config.DEFAULT_PERSISTENT), false, "Should messages be stored persistently on the server", "radio", "true,false"),
    field("Priority", "priority", toString(config.DEFAULT_PRIORITY), false, "", "text"),
    field("Time To Live", "ttl", toString(config.DEFAULT_TTL), false, "Number of milliseconds the message should be waiting to be delivered before being deleted", "text")
  ) />

  <cffunction name="getClass" access="public" returntype="string" output="false">
    <cfreturn "org.primeoservices.cfgateway.jms.lucee.LuceeJMSSenderGateway" />
  </cffunction>

  <cffunction name="getCFCPath" access="public" returntype="string" output="false">
    <cfreturn "" />
  </cffunction>

  <cffunction name="getLabel" access="public" returntype="string" output="false">
    <cfreturn "JMS - Outbound" />
  </cffunction>

  <cffunction name="getDescription" access="public" returntype="string" output="false">
    <cfreturn "Sends to a destination on a JMS server" />
  </cffunction>

  <cffunction name="onBeforeUpdate" access="public" returntype="void" output="false">
    <cfargument name="cfcPath" required="true" type="string" />
    <cfargument name="startupMode" required="true" type="string" />
    <cfargument name="custom" required="true" type="struct" />
  </cffunction>

  <cffunction name="getListenerCfcMode" access="public" returntype="string" output="false">
    <cfreturn "none" />
  </cffunction>

  <cffunction name="getListenerPath" access="public" returntype="string" output="false">
    <cfreturn "" />
  </cffunction>
</cfcomponent>
