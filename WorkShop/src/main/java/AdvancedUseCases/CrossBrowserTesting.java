package AdvancedUseCases;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import org.junit.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by liranbarokas on 09/01/2018.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrossBrowserTesting {

    public static WebDriver driver;
    public static Eyes eyes;
    public static BatchInfo batch;
    private static String testedPageUrl = "http://the-internet.herokuapp.com/login";
    private static String AppName = "Cross Browsers";
    private static String TestName = "Herokuapp - Login";


    @BeforeClass
    public static void ClassSetup(){
        batch = new BatchInfo("CrossBrowserTesting");

    }

    @Before
    public void setup(){
        eyes = new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        eyes.setForceFullPageScreenshot(true);
        eyes.setStitchMode(StitchMode.CSS);
        eyes.setMatchLevel(MatchLevel.LAYOUT);
        eyes.setBatch(batch);
        eyes.setBaselineEnvName("CrossBrowserTesting");  // Environment = OS + Browser + Viewport Size

    }

    @Test
    public void testChrome(){
        driver=new ChromeDriver();

// Optional - How to run Chrome in Headless mode
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("disable-infobars");
//        chromeOptions.addArguments("--headless");
//        driver=new ChromeDriver(chromeOptions);

        eyes.open(driver, AppName, TestName , new RectangleSize(600, 400));

        driver.get(testedPageUrl);

        eyes.checkWindow("Chrome");
    }

    @Test
    public void testFireFox(){
        driver=new FirefoxDriver();
        eyes.open(driver, AppName, TestName , new RectangleSize(600, 400));

        driver.get(testedPageUrl);

        eyes.checkWindow("FireFox");
    }

    @After
    public void tearDown() {
        driver.close();
        eyes.close();
    }



}
