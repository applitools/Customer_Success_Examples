package TestNG.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class LoginPage {
	
	private WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		
		this.driver = driver;
		
		if(!driver.getTitle().equals("ParaBank | Welcome | Online Banking")) {
			driver.get("http://parabank.parasoft.com");
		}		
	}
	
	public ErrorPage incorrectLogin(String username, String password) {
		
		driver.findElement(By.name("username")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.xpath("//input[@value='Log In']")).click();
		return new ErrorPage(driver);
	}
	
	public HomePage correctLogin(String username, String password) {
		
		driver.findElement(By.name("username")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.xpath("//input[@value='Log In']")).click();
		return new HomePage(driver);
	}
}