<cfcomponent displayName="JMSListener" output="false">
  <!---
  --->
  <cffunction name="onIncomingMessage" access="public" returntype="void" output="false">
    <cfargument name="event" type="struct" required="true" />

    <!--- Defined local variables --->
    <cfset var callback = "" />
    <cfset var properties = "" />
    <cfset var message = "" />

    <cftry>
      <cfset callback = arguments.event.data.callback />
      <cfset properties = arguments.event.data.properties />
      <cfset message = arguments.event.data.message />

      <!--- Handle message --->
      <cfinvoke method="handleMessage">
        <cfinvokeargument name="properties" value="#properties#" />
        <cfinvokeargument name="message" value="#message#" />
      </cfinvoke>
      <cfset callback.commit() />

      <!--- Handle errors --->
      <cfcatch type="any">
        <cfset callback.rollback() />
        <cfrethrow />
      </cfcatch>
    </cftry>

    <cfreturn>
  </cffunction>

  <!---
    Override this method in implementation component
  --->
  <cffunction name="handleMessage" access="public" returntype="void" output="false">
    <cfargument name="properties" type="struct" required="true" />
    <cfargument name="message" type="any" required="true" />

    <!--- --->
    <cfthrow type="NotImplementedException" message="Method handleMessage() not implemented" />
  </cffunction>
</cfcomponent>
