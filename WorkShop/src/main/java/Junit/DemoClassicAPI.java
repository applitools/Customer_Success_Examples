package Junit;

import Utils.EyesFactory;
import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@RunWith(JUnit4.class)
public class DemoClassicAPI {

    private Eyes eyes;
    private WebDriver driver;
    private static String testedPageUrl = "http://applitools.github.io/demo/TestPages/FramesTestPage/"; //TODO change link to Herokap
    private static String appName="Applitools_Demo";
    private static BatchInfo batch;

// TODO add link to Juint Rules
    @Rule
    public final TestName testName = new TestName();

    @BeforeClass
    public static void beforeClass(){
        batch = new BatchInfo("TestSuitName");
    }

    @Before
    public void setup(){
        // Optional - How to run Chrome in Headless mode
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("disable-infobars");
//        chromeOptions.addArguments("--headless");
//        driver=new ChromeDriver(chromeOptions);

        driver = new ChromeDriver();

        driver.get(testedPageUrl);

        // If Running tests in Parallel and Batch them together
//        eyes = EyesFactory.get();
//        EyesFactory.setBatch(batchName);

        eyes = new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        eyes.setBatch(batch);

        // Full page Screenshot Setting
        eyes.setForceFullPageScreenshot(true);
        eyes.setStitchMode(StitchMode.CSS);

        // Hide Scroll bars
        eyes.setHideScrollbars(true);

        // Set different Match Levels
//        eyes.setMatchLevel(MatchLevel.CONTENT);
        eyes.setMatchLevel(MatchLevel.LAYOUT);
//        eyes.setMatchLevel(MatchLevel.EXACT);
//        eyes.setMatchLevel(MatchLevel.STRICT);

         // Set Match TimeOut at the test level - TODO Add Link to article
//        eyes.setMatchTimeout(3000); // The default value is 2000 ms

        // Disabling eyes
//        eyes.setIsDisabled(false);

        // Add Custom Properties
//        eyes.addProperty("MyProperty", testName.getMethodName());
//        eyes.clearProperties();

        // How to ignore cursor diff
//        eyes.setIgnoreCaret(true);

        // Enabling logs to console
        eyes.setLogHandler(new StdoutLogHandler(true));

        // Enabling logs to File
//        eyes.setLogHandler(new FileLogger("path/to/file.log", true, true));

        // Save Debug Screenshots - available in latest versions
        eyes.setSaveDebugScreenshots(true);

        //eyes.setDebugScreenshotsPath(); // By Default the path is the working directory


        eyes.open(driver,appName,testName.getMethodName(), new RectangleSize(600, 500));
    }

    @Test
    public void Test_CSS_Stitcing() {
      //  eyes.setStitchMode(StitchMode.SCROLL);
        driver.get("https://applitools.com/");
  //      eyes.checkWindow("Home Page - Standard Scroll");
        eyes.setStitchMode(StitchMode.CSS);
        eyes.checkWindow("Home Page - CSS");
    }

    @After
    public void tearDown(){
        try{
            eyes.close();
        }
        finally {
            eyes.abortIfNotClosed();
            driver.quit();
        }

    }

    @Test
    public void TestCheckWindow() {
        eyes.checkWindow("Window");
//        eyes.checkWindow(3000,"Window"); // set Different matchTimeOut per step
    }

    @Test
    public void TestCheckRegion() {
        // TODO change Selector to fit the new URL
        eyes.checkRegion(By.id("overflowing-div"), "Region", true);
//        eyes.checkRegion(By.id("overflowing-div"),3000, "Region", true);  // set Different matchTimeOut per step
    }

    @Test
    public void TestCheckFrame() {
        // TODO change Selector to fit the new URL
        //first property id the frame name or ID
        eyes.checkFrame("frame1", "frame1");
//        eyes.checkFrame("frame1",3000,"frame1");          // set Different matchTimeOut per step

    }

    @Test
    public void TestCheckRegionInFrame() {
        // TODO change Selector to fit the new URL
        eyes.checkRegionInFrame("frame1", By.id("inner-frame-div"), "Inner frame div", true);
//        eyes.checkRegionInFrame("frame1", By.id("inner-frame-div"),3000, "Inner frame div", true);       // set Different matchTimeOut per step
    }






}