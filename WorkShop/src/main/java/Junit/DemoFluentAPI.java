package Junit;

/**
 * Created by liranbarokas on 04/12/2017.
 */

import Utils.EyesFactory;
import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


@RunWith(JUnit4.class)

public class DemoFluentAPI{
    private Eyes eyes;
    private WebDriver driver;
    private static String testedPageUrl = "http://the-internet.herokuapp.com/login";
    private static String appName="Applitools_Demo";
    private static String batchName="Juint - DemoFluentAPI";

    @Rule
    public final TestName testName = new TestName();

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
        BatchInfo batch = new BatchInfo(batchName);
        eyes.open(driver,appName,testName.getMethodName(), new RectangleSize(1000, 400));
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

//    @Test
//    public void TestCheckWindow() {
//        eyes.check("Window", Target.window().timeout(3000));
//
//        eyes.check("Window", Target.window().timeout(3000).fully().layout()); // Full page screenshot
////        Equivalent to - eyes.checkWindow("Window");
//
//    }

//    @Test
//    public void TestCheckWindowFullPageScreenshot() {
////        eyes.check("Window", Target.window().timeout(3000).fully());
////        Equivalent to - eyes.checkWindow("Window");
//
//    }

    @Test
    public void TestCheckWindowIgnoreAndFloatingRegion() {

        WebElement elementToIgnore = driver.findElement(By.cssSelector("#login > div:nth-child(1) > div")); // First input element
        WebElement floatingElement = driver.findElement(By.cssSelector("#login > button > i")); // text on button

        eyes.check("Window", Target.window().ignore(elementToIgnore).floating(floatingElement,10,20,30,40).fully());
//        Equivalent to - eyes.checkWindow("Window");

    }
//
//    @Test
//    public void TestIgnoreCursor(){
//        driver.findElement(By.id("username")).sendKeys("Applitools User");
//        eyes.check("Ignore cursor", Target.window().fully().ignoreCaret().layout());
//
//        //https://eyes.applitools.com/app/sessions/00000251889919609569/00000251889919609223/steps/1?&parent=WDYYpsj&accountId=ZZFuPvFFXEmhjSdKex8kLQ~~
//
//
//    }
//
    @Test
    public void TestCheckRegion() {


        eyes.check("Region", Target.region(By.cssSelector("#login > button")));
//        Equivalent to - eyes.checkRegion("Window");

    }

    @Test
    public void TestCheckFrame() {
        driver.get("http://applitools.github.io/demo/TestPages/FramesTestPage/");
        eyes.check("frame1", Target.frame("frame1"));
//        eyes.checkFrame("frame1", "frame1");
    }

    @Test
    public void TestCheckRegionInFrame() {
        driver.get("http://applitools.github.io/demo/TestPages/FramesTestPage/");
        eyes.check("Inner frame div", Target.frame("frame1").region(By.id("inner-frame-div")));
//        eyes.checkRegionInFrame("frame1", By.id("inner-frame-div"), "Inner frame div", true);
    }
}






