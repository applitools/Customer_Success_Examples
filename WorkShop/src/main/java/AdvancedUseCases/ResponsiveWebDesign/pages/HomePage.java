package AdvancedUseCases.ResponsiveWebDesign.pages;

import com.applitools.eyes.selenium.Eyes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
	private By userLoginLocator = By.id("user[login]");
	private By serachBoxLocator = By.cssSelector("body > div.position-relative.js-header-wrapper > header > div > div.HeaderMenu.HeaderMenu--bright.d-lg-flex.flex-justify-between.flex-auto > div > div > div > form > label > input.form-control.header-search-input.js-site-search-focus");
	private By menuBoxLocator = By.cssSelector("body > div.position-relative.js-header-wrapper > header > div > div.d-flex.flex-justify-between.flex-items-center > button");
	private By serachFormLocator = By.cssSelector("body > div.position-relative.js-header-wrapper > header > div > div.HeaderMenu.HeaderMenu--bright.d-lg-flex.flex-justify-between.flex-auto > div > div > div > form");

	private By signInFormLocator = By.cssSelector("body > div:nth-child(4) > div.jumbotron.jumbotron-codelines > div > div > div.mx-auto.col-sm-8.col-md-5.hide-sm > div > form");

	private By intreductionParagraph = By.cssSelector("body > div:nth-child(4) > div.jumbotron.jumbotron-codelines > div > div > div.col-md-7.text-center.text-md-left");

	private String mode;


	public HomePage(WebDriver driver, Eyes eyes) {
		super(driver,eyes);
		URL = "https://github.com/";
		driver.get(URL);

	}

	public SearchResultPage searchRepository(String search){
		// App is in mode 1,2 or 3
		if(driver.findElement(menuBoxLocator).isDisplayed()){
			eyes.checkWindow("Open Menu");
			driver.findElement(menuBoxLocator).click();
		}
		eyes.checkWindow("Before filling search ares");
		driver.findElement(serachBoxLocator).sendKeys(search);
		eyes.checkWindow("After filling search ares");
		driver.findElement(serachFormLocator).submit();
		return new SearchResultPage(driver,eyes);
	}


	public String getMode(){
		if(!driver.findElement(signInFormLocator).isDisplayed())
			return "Mode 1";
		if(!driver.findElement(menuBoxLocator).isDisplayed())
			return "mode 4 ";
		if(driver.findElement(intreductionParagraph).getLocation().getY()<driver.findElement(signInFormLocator).getLocation().getY())
			return "Mode 2";
		else
			return "Mode 3";
	}

}