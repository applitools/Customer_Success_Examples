package AdvancedUseCases.ResponsiveWebDesign;

import AdvancedUseCases.ResponsiveWebDesign.pages.HomePage;
import AdvancedUseCases.ResponsiveWebDesign.pages.SearchResultPage;
import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by liranbarokas on 09/01/2018.
 */
@RunWith(Parameterized.class)
public class ResponsiveWebDesign{
    public static BatchInfo batch;
    public static WebDriver driver;
    public Eyes eyes;
    public static String AppName="Github";
    private int width;
    private int height;
    private String mode;


    @Parameterized.Parameters(name = "{index}: Test with width={0}, height={1}, mode={2}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {544, 400, "mode 1 - upper bound"},
                {545, 400,"mode 2 - lower bound"},
                {767, 400, "mode 2 - upper bound"},
                {768, 400, "mode 3 - lower bound"},
                {1011, 400, "mode 3 - upper bound"},
                {1012, 400,"mode 4 - lower bound"}
        });
    }

    public ResponsiveWebDesign(int Width, int Height, String mode ){
        this.width=Width;
        this.height=Height;
        this.mode = mode;
    }

    @BeforeClass
    public static void ClassSetup(){
        batch = new BatchInfo("Responsive Web Design");
    }

    @Before
    public void setup(){

        // Optional - How to run Chrome in Headless mode

//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("disable-infobars");
//        chromeOptions.addArguments("--headless");
//        driver=new ChromeDriver(chromeOptions);


        driver = new ChromeDriver();
        eyes=new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        eyes.setForceFullPageScreenshot(true);
        eyes.setStitchMode(StitchMode.CSS);
        eyes.setBatch(batch);
    }

    @Test
    public void MobileLayoutMode(){
        HomePage homePage = new HomePage(driver,eyes);
        Eyes.setViewportSize(driver,new RectangleSize(this.width, this.height));
        eyes.open(driver, AppName,"Gitub "+ homePage.getMode());
        SearchResultPage searchResultPage = homePage.searchRepository("Applitools");
        searchResultPage.sortByCriteria("Most Stars");
    }

    @After
    public void tearDown() {
        driver.close();
        eyes.close();
    }


}
