package AdvancedUseCases.ResponsiveWebDesign.pages;

import com.applitools.eyes.selenium.Eyes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultPage extends BasePage {

	private By sortButton = By.cssSelector("#js-pjax-container > div > div.columns > div.column.three-fourths.codesearch-results > div > div.d-flex.flex-justify-between.border-bottom.pb-3 > div > button");
	private By sortOptionLocator = By.cssSelector("#js-pjax-container > div > div.columns > div.column.three-fourths.codesearch-results > div > div.d-flex.flex-justify-between.border-bottom.pb-3 > div > div");

	public SearchResultPage(WebDriver driver, Eyes eyes) {
		super(driver,eyes);
		URL=driver.getCurrentUrl();

	}

	public void sortByCriteria(String criteria){
		WebElement sortElement = driver.findElement(sortOptionLocator);
		List<WebElement> sortItems= sortElement.findElements(By.cssSelector("a"));
		eyes.checkWindow("Entire search before change");
		driver.findElement(sortButton).click();
		eyes.checkRegion(sortOptionLocator,"Before sort");
		switch (criteria.toLowerCase()) {
			case "best match":
				sortItems.get(0).click();

				break;
			case "most stars":
				sortItems.get(1).click();
				break;
			case "fewest stars":
				sortItems.get(2).click();
				break;
			case "most forks":
				sortItems.get(3).click();
				break;
			case "fewest forks":
				sortItems.get(4).click();
				break;
			case "recently updated":
				sortItems.get(5).click();
				break;
			case "least recently updated":
				sortItems.get(6).click();
				break;
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		eyes.checkRegion(sortButton,"AfterChange");


	}
	
}