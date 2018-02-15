package TestNG.tests;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.*;
import org.testng.annotations.*;
import TestNG.pages.ErrorPage;
import TestNG.pages.HomePage;
import TestNG.pages.LoginPage;
import org.testng.internal.TestResult;

import java.lang.reflect.Method;

public class TestNGDemo {
	
	WebDriver driver;
	BatchInfo batch;
	Eyes eyes;


	@BeforeSuite
	public void setUp() {
		batch = new BatchInfo("TestNGDemo");
	}


	@Parameters({"width","height"})
	@BeforeMethod
	public void beforeMethod(Method method,int width,int height){
		driver = new ChromeDriver();
		eyes = new Eyes();
		eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
		eyes.setForceFullPageScreenshot(true);
		eyes.setStitchMode(StitchMode.CSS);
		eyes.setBatch(batch);
		eyes.open(driver,"TestNG",method.getName(),new RectangleSize(width,height));

	}


	@Parameters({"username","password"})
	@Test(description="Checks whether a successful login and logout can be performed given the correct credentials")
	public void testLoginOK(String username, String password) {

		LoginPage lp = new LoginPage(driver);
		eyes.check("LoginPage", Target.window());
		HomePage hp = lp.correctLogin(username, password);
		eyes.check("Correct Login Page", Target.window());
		Assert.assertEquals(hp.getWelcomeString(), "Welcome John Smith");
		lp = hp.logOut();
		eyes.check("Log Out Page", Target.window());


	}

	@Parameters({"username","incorrectpassword"})
	@Test(description="Performs an unsuccessful login and checks the resulting error message")
	public void testLoginNotOK(String username, String incorrectpassword) {
		LoginPage lp = new LoginPage(driver);
		eyes.check("LoginPage", Target.window());
		ErrorPage ep = lp.incorrectLogin(username, incorrectpassword);
		eyes.check("ErrorPage", Target.window());
		Assert.assertEquals(ep.getErrorText(), "The username and password could not be verified.");


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