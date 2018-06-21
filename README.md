# cf-jms-gateway

__JMS Gateways for ColdFusion__

# How to install

### Lucee 4

* Copy the `.jar` files needed for the connection to your favorite JMS server in the `WEB-INF\lucee\lib` folder of your web root (check the documentation of the JMS provider for info)
* Restart Lucee
* Download the latest version [here](https://github.com/jbvanzuylen/cf-jms-gateway/releases/download/v0.2.0/cf-jms-gateway-lucee-ext.zip)

__Install as an extension__

* Connect to the Lucee Web Administration
* Go to `Extension > Application`
* Scroll down to the bottom of the page
* Click on `Browse` button in the `Upload new extension` section
* Select the ZIP file you have downloaded above
* Hit the `Upload` button and follow the instructions

__Manual installation__

* Unzip the file you have downloaded above
* Copy the `.cfc` files from the `driver` folder to the `WEB-INF\lucee\context\admin\gdriver` directory in your web root
* Copy the `.jar` files from the `lib` folder to the `WEB-INF\lucee\lib` directory in your web root
* Restart Lucee
