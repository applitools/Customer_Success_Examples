package AdvancedUseCases;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StagingVsProduction {

    public static WebDriver driver;
    public static Eyes eyes;
    public static BatchInfo batch;
    public String StagingURLPrefix="";


    @Rule public TestName name = new TestName();


    @BeforeClass
    public static void ClassSetup(){
        batch = new BatchInfo("StagingVsProduction");

    }

    @Before
    public void setup(){
        eyes = new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        driver = new ChromeDriver();

        eyes.setForceFullPageScreenshot(true);
        eyes.setStitchMode(StitchMode.CSS);
        eyes.setBatch(batch);

        if (name.getMethodName().contains("staging")){
            eyes.addProperty("State","Staging");
        }
        else if (name.getMethodName().contains("prod")){
            eyes.addProperty("State","Production");
        }
        eyes.open(driver, "StagingVsProduction", "login test", new RectangleSize(800, 500));
    }


    @Test
    public void tests_1_staging(){
        login_test(true , "staging - ");
    }

    @Test
    public void tests_2_production(){

        login_test(false, "production - ");

    }

    public void login_test(boolean isStaging, String stepPrefix){

        eyes.setSaveFailedTests(isStaging); // To ensure staging set as baseline
        driver.get("http://"+StagingURLPrefix+"/the-internet.herokuapp.com/login");
        eyes.checkWindow(stepPrefix+"Form Page");
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        eyes.checkWindow(stepPrefix+"Form Page - After filling fields");
        driver.findElement(By.cssSelector("#login > button")).click();
        eyes.checkWindow(stepPrefix+"Valid login");
    }



    @After
    public void tearDown() {
        driver.quit();
        eyes.close();
        eyes.clearProperties();
    }





}

