### DigitalSkills - Car Tax Check

This is a Selenium based test which uses Chrome browser to run the scenario. Browser, driver path, vehicle details and timeout can be configured in the cartaxcheck.properties

### required tools/softwares:

Maven
Chrome webdriver
JDK 1.8
Chrome browser
Cucumber

## To run this this test, please follow steps below:

* Clone this project and open it as a Maven project into your workspace.
* Make sure that you have maven and java 8 installed or configured in your machine/IDE
* Download the chromedriver from https://sites.google.com/a/chromium.org/chromedriver/
* Save the driver to a path in your machine.
* In resources/cartaxchecktest.properties and pom.xml, update chrome.driver.path value to the path you have stored in your machine.
* Execute the CarTaxCheckWebPageTest.java -> checkVehicleCarTax as JUnit Test.
* Alternatively, Maven clean install can be run on project/pom file.
* If cucumber plugin is available and configured correctly, feature/CarTaxCheck.feature can be executed as cucumber test