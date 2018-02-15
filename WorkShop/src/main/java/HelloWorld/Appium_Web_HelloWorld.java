package HelloWorld;

/**
 * Created by liranbarokas on 10/12/2017.
 */


import com.applitools.eyes.selenium.Eyes;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//


class Appium_Web_HelloWorld {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {

        // Initialize the eyes SDK and set your private API key.
        Eyes eyes = new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));


        // Set the desired capabilities.
        DesiredCapabilities dc = DesiredCapabilities.android();

        dc.setCapability("browserName", "Browser");
        dc.setCapability("app", "");
        dc.setCapability("deviceName", "Google Nexus 9");
        dc.setCapability("platformName", "Android");
//        dc.setCapability("autoWebview","true");
//        dc.setCapability("platformVersion", "5.0.1");

        // Open browser.
        AppiumDriver driver = new AppiumDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), dc);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        try {
//            How to switch WebDriver context to WEBVIEW
//            Set contextNames = driver.getContextHandles();
//            driver.context(contextNames.toArray()[0].toString());
//            driver.context("WEBVIEW")

            // Start the test.
            eyes.open(driver, "Hello World!", "My first Appium web Java test!");

            // Navigate the browser to the "hello world!" web-site.
            driver.get("https://applitools.com/helloworld");

            Thread.sleep(8000);

            // Visual validation point #1.
            eyes.checkWindow("Hello!");

            // End the test.
            eyes.close();

        } finally {

            // Close the browser.
            driver.quit();

            // If the test was aborted before eyes.close was called, ends the test as aborted.
            eyes.abortIfNotClosed();

        }
    }
}