package AdvancedUseCases;

import Utils.EyesFactory;
import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Localization {

    public static WebDriver driver;
    public static Eyes eyes;
    public static BatchInfo batch;

    @BeforeClass
    public static void ClassSetup(){
        batch = new BatchInfo("Localization");

    }

    @Before
    public void setup(){
        eyes = new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        driver = new ChromeDriver();

        eyes.setForceFullPageScreenshot(true);
        eyes.setStitchMode(StitchMode.CSS);
        eyes.setMatchLevel(MatchLevel.LAYOUT);

        eyes.setBatch(batch);
        eyes.open(driver, "Localization", "Olympics", new RectangleSize(600, 500));

    }

    @Test
    public void testEnglishWebSite(){
        eyes.setSaveFailedTests(true); // To ensure the English web-site will be the baseline - only for demonstration purposes
        driver.get("https://www.olympic.org/news/rio-2016");
        eyes.checkWindow("EnglishWebSite");
    }

    @Test
    public void testFrenchWebSite(){
        eyes.setSaveFailedTests(false);  // Comparing the french Web-Site Vs. the English
        driver.get("https://www.olympic.org/fr/news/rio-2016");

        eyes.checkWindow("FrenchWebSite");
    }

    @After
    public void tearDown() {
            eyes.close();
    }

    @AfterClass
    public static void classTearDown(){
        driver.quit();
    }



}

