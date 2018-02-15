package TestNG;

import com.applitools.eyes.*;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.*;
import org.testng.annotations.*;
import java.lang.reflect.Method;

public class TestNG_Simple_Example {

    WebDriver driver;
    BatchInfo batch;
    Eyes eyes;


    @BeforeSuite
    public void setUp() {
        batch = new BatchInfo("TestNGDemo");
    }

    @BeforeMethod
    public void beforeMethod(Method method){
        // Optional - How to run Chrome in Headless mode
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("disable-infobars");
//        chromeOptions.addArguments("--headless");
//        driver=new ChromeDriver(chromeOptions);

        driver = new ChromeDriver();

        eyes = new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        eyes.setBatch(batch);

        eyes.setForceFullPageScreenshot(true);
        eyes.setStitchMode(StitchMode.CSS);

        eyes.open(driver,"TestNG",method.getName(),new RectangleSize(600,500));
        driver.get("http://the-internet.herokuapp.com/login");
    }

    @Test(description="checkWindow")
    public void Test_Check_Window() {
        eyes.checkWindow("Check the entire page");
    }

    @Test(description="check Region")
    public void Test_Check_Region() {
        eyes.checkRegion(By.id("login"),"check only the form");
    }

    @AfterMethod
    public void afterMethod(ITestResult result){

        driver.close();

        // How To integrate Applitools results within TestNG built in Report
		TestResults testResult = eyes.close(false);
		if (!testResult.isPassed()){
			ITestContext tc = Reporter.getCurrentTestResult().getTestContext();
			tc.getPassedTests().addResult(result, result.getMethod());
			tc.getPassedTests().getAllMethods().remove(result.getMethod());
			result.setStatus(ITestResult.FAILURE);
			tc.getFailedTests().addResult(result, result.getMethod());

		}

        // If not using TestNG built in report, call eyes.close() at each Test Method to mark tests with UI Diffs ad failed.
    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
    }


}