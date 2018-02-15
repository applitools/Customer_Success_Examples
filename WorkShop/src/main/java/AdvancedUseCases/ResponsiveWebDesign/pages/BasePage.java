package AdvancedUseCases.ResponsiveWebDesign.pages;

import com.applitools.eyes.selenium.Eyes;
import org.openqa.selenium.WebDriver;

public class BasePage {
	public String URL = null;
	public WebDriver driver;
	public Eyes eyes;

	public BasePage(WebDriver driver, Eyes eyes) {
		this.driver = driver;
		this.eyes = eyes;

	}

}