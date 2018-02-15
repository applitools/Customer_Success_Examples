package TestNG.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ErrorPage {
	
	private WebDriver driver;
	
	public ErrorPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getErrorText() {
		
		return driver.findElement(By.className("error")).getText();
	}
}