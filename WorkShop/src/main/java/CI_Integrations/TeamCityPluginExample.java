package CI_Integrations;

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


@RunWith(JUnit4.class)

public class TeamCityPluginExample {
    private Eyes eyes;
    private static BatchInfo batch;
    private WebDriver driver;
    private static String testedPageUrl = "http://the-internet.herokuapp.com/login";
    private static String appName="Applitools_Demo";

    @Rule
    public final TestName testName = new TestName();

    @BeforeClass
    public static void OneTimeSetUp(){
        String batchName = ((System.getenv("APPLITOOLS_BATCH_NAME")!=null) ? System.getenv("APPLITOOLS_BATCH_NAME") : "MyBatchName");

        batch = new BatchInfo(batchName);
        if (System.getenv("APPLITOOLS_BATCH_ID")!=null){
            batch.setId(System.getenv("APPLITOOLS_BATCH_ID"));
        }

    }

    @Before
    public void setup(){
        driver = new ChromeDriver();
        driver.get(testedPageUrl);

        eyes = new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        eyes.setBatch(batch);

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

    @Test
    public void TestCheckWindow() {
        eyes.check("Window", Target.window().timeout(3000).fully().layout()); // Full page screenshot
    }


    @Test
    public void TestCheckWindowIgnoreAndFloatingRegion() {
        WebElement elementToIgnore = driver.findElement(By.cssSelector("#login > div:nth-child(1) > div")); // First input element
        WebElement floatingElement = driver.findElement(By.cssSelector("#login > button > i")); // text on button

        eyes.check("Window", Target.window().ignore(elementToIgnore).floating(floatingElement,10,20,30,40).fully());
    }

}






