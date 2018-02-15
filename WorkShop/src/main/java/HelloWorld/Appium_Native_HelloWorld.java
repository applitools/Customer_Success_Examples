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
import java.util.concurrent.TimeUnit;

//


class Appium_Native_HelloWorld {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {

        // Initialize the eyes SDK and set your private API key.
        Eyes eyes = new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));


        // Set the desired capabilities.
        DesiredCapabilities dc = DesiredCapabilities.android();
        dc.setCapability("deviceName", "Google Nexus 9");
        dc.setCapability("platformName", "Android");
        dc.setCapability("appPackage", "com.android.calculator2");
        dc.setCapability("appActivity", "com.android.calculator2.Calculator");

        // Open browser.
        AppiumDriver driver = new AppiumDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), dc);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        try {
//            How to switch WebDriver context to WEBVIEW

//            Set contextNames = driver.getContextHandles();
//            driver.context(contextNames.toArray()[0].toString());
//            driver.context("WEBVIEW")

            // Start the test.
            eyes.open(driver, "Hello World!", "My first Appium Native Java test!");


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