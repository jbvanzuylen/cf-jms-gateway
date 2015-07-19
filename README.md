JMS Gateways for ColdFusion
===============================

# How to install

### Railo

* Copy the `.jar` files needed for the connection to your favorite JMS provider in the `WEB-INF\railo\lib` folder of your web root
* Restart Railo
* Download the latest version [here](https://github.com/jbvanzuylen/cf-jms-gateway/releases/download/v0.0.5/jms-gateway-ext.zip)

__Install as an extension__

* Connect to the Railo Web Administration
* Go to `Extension > Application`
* Scroll down to the bottom of the page
* Click on `Browse` button in the `Upload new extension` section
* Select the ZIP file you have downloaded above
* Hit the `Upload` button and follow the instructions

__Manual installation__

* Unzip the file you have downloaded above
* Copy the `.cfc` files from the `driver` folder to the `WEB-INF\railo\context\admin\gdriver` directory in your web root
* Copy the `.jar` files from the `lib` folder to the `WEB-INF\railo\lib` directory in your web root
* Restart Railo
