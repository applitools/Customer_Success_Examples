package AnalyseResults;

import AnalyseResults.ApplitoolsTestResultHandler.ApplitoolsTestResultsHandler;
import AnalyseResults.ApplitoolsTestResultHandler.ResultStatus;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.TestResultsStatus;
import com.applitools.eyes.selenium.Eyes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.URL;


public class AnalyzeResultsExample {

    public static void main(String[] args) throws Exception {

        WebDriver driver = new ChromeDriver();
        Eyes eyes = new Eyes();

//         This is your api key, make sure you use it in all your tests.
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        try {
            // Start visual testing with browser viewport set to 800x600.
            // Make sure to use the returned driver from this point on.
            eyes.open(driver, "Hello World!", "My first Selenium Java test!", new RectangleSize(800, 600));

            // Navigate the browser to the "hello world!" web-site.
            driver.get("https://applitools.com/helloworld");

            // Visual checkpoint #1.
            eyes.checkWindow("Hello!");

            // Click the "Click me!" button.
            driver.findElement(By.tagName("button")).click();

            // Visual checkpoint #2.
            eyes.checkWindow("Click!");

            // End visual testing. Validate visual correctness.
            TestResults testResult = eyes.close(false);


//          Result Analysis

            //Link to Applitools dashboard with the results of the test.
            String url  = testResult.getUrl();

            //Check if the test pass
            Boolean isPassed = testResult.isPassed();

            //Check if the test new
            Boolean isNew = testResult.isNew();

            // get number of steps
            int steps = testResult.getSteps();

            // get number of comparison given match level
            int stepsInStrict = testResult.getStrictMatches();
            int stepsInContent = testResult.getContentMatches();
            int stepsInLayout = testResult.getLayoutMatches();

            // get number of match steps
            int stspsMatches = testResult.getMatches();
            int stspsMissmatches = testResult.getMismatches();
            int stspsMissing = testResult.getMissing();



//            Download test details locally

            ApplitoolsTestResultsHandler testResultHandler = new ApplitoolsTestResultsHandler(testResult, System.getenv("APPLITOOLS_VIEW_KEY"));

            // Optional Setting this prefix will determine the structure of the repository for the downloaded images.
            testResultHandler.SetPathPrefixStructure("TestName/AppName/viewport/hostingOS/hostingApp");

            //Link to test/step result
//            System.out.println("This is the url to the first step " +testResultHandler.getLinkToStep(1));

            testResultHandler.downloadImages(System.getenv("PathToDownloadImages"));                // Download both the Baseline and the Current images to the folder specified in Path.
//            testResultHandler.downloadBaselineImages(System.getenv("PathToDownloadImages"));      // Download only the Baseline images to the folder specified in Path.
//            testResultHandler.downloadCurrentImages(System.getenv("PathToDownloadImages"));       // Download only the Current images to the folder specified in Path.
            testResultHandler.downloadDiffs(System.getenv("PathToDownloadImages"));                 // Download Diffs to the folder specified in Path.
            testResultHandler.downloadAnimateGiff(System.getenv("PathToDownloadImages"));           // Download Animated GIf to the folder specified in Path.

//            Get Steps Names
            String[] names = testResultHandler.getStepsNames();

//            Get the status of each step (Pass / Unresolved / New / Missing).
            ResultStatus[] results = testResultHandler.calculateStepResults();
            for (int i=0 ; i< results.length; i++){
                System.out.println("The result of step "+(i+1)+" "+names[i] +"is "+ results[i]);
            }

        }

        finally {
            // Abort Session in case of an unexpected error.
            eyes.abortIfNotClosed();
            driver.close();
        }
    }
}