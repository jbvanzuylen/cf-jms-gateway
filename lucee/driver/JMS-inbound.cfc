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
    group("Inbound Options", "Inboud Options", 3),
    field("Destination Name", "destinationName", "", false, "JNDI name of the JMS destination", "text"),
    field("Selector", "selector", "", false, "An expression used to filter messages delivered to this gateway", "text"),
    field("Ack Mode", "ackMode", toString(config.DEFAULT_ACK_MODE), false, "Method to acknowledge any messages delivered to this gateway", "radio", "AUTO,CLIENT,DUPL_OK"),
    field("Transacted", "transacted", toString(config.DEFAULT_TRANSACTED), false, "Receiving messages in a transaction", "radio", "true,false"),
    field("Durable", "durable", toString(config.DEFAULT_DURABLE), false, "When the destination is a topic, use this option to indicate whether a durable subscription should be created or not", "radio", "true,false"),
    group("Retry Options", "Retry options", 3),
    field("Retry Count", "retryCount", "", false, "Number of times a failed message should be retried (-1 for no limit)", "text"),
    field("Retry Delay", "retryDelay", "", false, "The delay before retrying a failed message", "text"),
    group("Error notification", "Error notification", 3),
    field("E-mail", "email", "", false, "", "text")
  ) />

  <cffunction name="getClass" access="public" returntype="string" output="false">
    <cfreturn "org.primeoservices.cfgateway.jms.lucee.LuceeJMSListenerGateway" />
  </cffunction>

  <cffunction name="getCFCPath" access="public" returntype="string" output="false">
    <cfreturn "" />
  </cffunction>

  <cffunction name="getLabel" access="public" returntype="string" output="false">
    <cfreturn "JMS - Inbound" />
  </cffunction>

  <cffunction name="getDescription" access="public" returntype="string" output="false">
    <cfreturn "Reads from a destination on a JMS server" />
  </cffunction>

  <cffunction name="onBeforeUpdate" access="public" returntype="void" output="false">
    <cfargument name="cfcPath" required="true" type="string" />
    <cfargument name="startupMode" required="true" type="string" />
    <cfargument name="custom" required="true" type="struct" />
  </cffunction>

  <cffunction name="getListenerCfcMode" access="public" returntype="string" output="false">
    <cfreturn "required" />
  </cffunction>

  <cffunction name="getListenerPath" access="public" returntype="string" output="false">
    <cfreturn "" />
  </cffunction>
</cfcomponent>
