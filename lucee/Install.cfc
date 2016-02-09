<cfcomponent displayname="Install" output="false">
  <!--- Extension name --->
  <cfset variables.name = "JMS Gateway" />

  <cfset variables.libraries = array(
    "jms-gateway.jar",
    "javax.jms-api-2.0.jar"
  ) />

  <cfset variables.drivers = array(
    "JMS-inbound.cfc",
    "JMS-outbound.cfc"
  ) />

  <cfset variables.listeners = array(
    "JMSListener.cfc"
  ) />

  <!---
    Called from Lucee to validate values
  --->
  <cffunction name="validate" access="public" returntype="void" output="false">
    <cfargument name="error" type="struct" />
    <cfargument name="path" type="string" />
    <cfargument name="config" type="struct" />
    <cfargument name="step" type="numeric" />

    <!--- Nothing to do --->
  </cffunction>

  <!---
    Called from Lucee to install the gateway
  --->
  <cffunction name="install" access="public" returntype="string" output="false">
    <cfargument name="error" type="struct" />
    <cfargument name="path" type="string" />
    <cfargument name="config" type="struct" />

    <!--- Defined local variables --->
    <cfset var serverPath = expandPath('{lucee-web-directory}') />
    <cfset var dirPath = "" />
    <cfset var dirContent = "" />

    <!--- Update jars --->
    <cfset dirPath = arguments.path & "lib" />
    <cfdirectory action="list" directory="#dirPath#" type="file" filter="*.jar" name="dirContent">
    <cfloop query="dirContent">
      <cffile action="copy"
              source="#dirPath#/#dirContent.name#"
              destination="#serverPath#/lib/#dirContent.name#"
      />
    </cfloop>

    <!--- Update drivers --->
    <cfset dirPath = arguments.path & "driver" />
    <cfdirectory action="list" directory="#dirPath#" type="file" filter="*.cfc" name="dirContent">
    <cfloop query="dirContent">
      <cffile action="copy"
              source="#dirPath#/#dirContent.name#"
              destination="#serverPath#/context/admin/gdriver/#dirContent.name#"
      />
    </cfloop>

    <!--- Update listeners --->
    <cfif NOT directoryExists("#serverPath#/components/lucee/extension/gateway/jms")>
      <cfdirectory action="create" directory="#serverPath#/components/lucee/extension/gateway/jms" />
    </cfif>
    <cfset dirPath = arguments.path & "listener" />
    <cfdirectory action="list" directory="#dirPath#" type="file" filter="*.cfc" name="dirContent">
    <cfloop query="dirContent">
      <cffile action="copy"
              source="#dirPath#/#dirContent.name#"
              destination="#serverPath#/components/lucee/extension/gateway/jms/#dirContent.name#"
      />
    </cfloop>

    <cfreturn "#variables.name# has been successfully installed<br><br>" />
  </cffunction>

  <!---
    Called from Lucee to update the gateway
  --->
  <cffunction name="update" access="public" returntype="string" output="false">
    <cfargument name="error" type="struct" />
    <cfargument name="path" type="string" />
    <cfargument name="config" type="struct" />
    <cfargument name="previousConfig" type="struct" />

    <!--- Uninstall old version--->
    <cfset uninstall(arguments.path, arguments.previousConfig) />

    <!--- Install new version --->
    <cfset install(arguments.error, arguments.path, arguments.config) />
  </cffunction>

  <!---
    Called from Lucee to uninstall the gateway
  --->
  <cffunction name="uninstall" access="public" returntype="string" output="false">
    <cfargument name="path" type="string" />
    <cfargument name="config" type="struct" />

    <!--- Defined local variables --->
    <cfset var serverPath = expandPath('{lucee-web-directory}') />
    <cfset var library = "" />
    <cfset var driver = "" />
    <cfset var listener = "" />
    <cfset var errors = arrayNew(1) />
    <cfset var message = "" />

    <!--- Remove the listeners --->
    <cfloop array="#variables.listeners#" index="listener">
      <cfset removeFile("#serverPath#/components/lucee/extension/gateway/jms/#listener#", errors) />
    </cfloop>
    <cfdirectory action="delete" directory="#serverPath#/components/lucee/extension/gateway/jms" />

    <!--- Remove the drivers --->
    <cfloop array="#variables.drivers#" index="driver">
      <cfset removeFile("#serverPath#/context/admin/gdriver/#driver#", errors) />
    </cfloop>

    <!--- Remove the libraries --->
    <cfloop array="#variables.libraries#" index="library">
      <cfset removeFile("#serverPath#/lib/#library#", errors) />
    </cfloop>

    <!--- Build return message --->
    <cfif arrayLen(errors)>
      <cfset message &= "The following files couldn't be deleted and should be removed manually:<br><br>" />
      <cfset message &= arrayTolist(errors, "<br>") />
      <cfset message &= "<br><br>" />
    </cfif>
    <cfset message &= "#variables.name# has been uninstalled<br><br>" />

    <cfreturn message />
  </cffunction>

  <!---
    Called from here to try to delete a file
  --->
  <cffunction name="removeFile" access="private" returntype="void" output="false">
    <cfargument name="filePath" type="string" />
    <cfargument name="errors" type="array" />

    <cftry>
      <cffile action="delete" file="#arguments.filePath#" />

      <cfcatch type="any">
        <cfset arrayAppend(arguments.errors, arguments.filePath) />
      </cfcatch>
    </cftry>
  </cffunction>
</cfcomponent>