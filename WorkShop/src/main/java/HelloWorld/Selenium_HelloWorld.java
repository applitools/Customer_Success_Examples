package HelloWorld;

/**
 * Created by manojkumar on 01/02/2018.
 */

import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Selenium_HelloWorld {

    public static void main(String[] args) {

        //Setup ChromeDriver executable path
//        System.setProperty("webdriver.chrome.driver", "C://Users/path");

        //Setup IEDriver executable path
        //System.setProperty("webdriver.ie.driver", "C://Users/path");
        // Open a Chrome browser.
        WebDriver driver = new ChromeDriver();

        // Open a IE browser.
        //WebDriver driver = new InternetExplorerDriver();


        // Initialize the eyes SDK and set your private API key.
        Eyes eyes = new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));

        try{

            // Start the test and set the browser's viewport size to 700x400.
            eyes.open(driver, "applitools page", "ApplitoolsHomePage-Manoj",
                    new RectangleSize(600, 500));

            // Navigate the browser to the "hello world!" web-site.
            driver.get("https://applitools.com/helloworld");

            // Visual checkpoint #1.
            eyes.checkWindow("Hello World");

            driver.findElement(By.tagName("button")).click();

            eyes.checkWindow("OK");

            eyes.close();


        } finally {

            // Close the browser.
            driver.quit();

            // If the test was aborted before eyes.close was called, ends the test as aborted.
            eyes.abortIfNotClosed();
        }

    }

}
