package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	private WebDriver driver;
	private WebDriverWait wait;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
				
	}
	public void navigate() {
		driver.get("https://automationteststore.com/");
	}
	
	public List<WebElement> MainCategories() {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//ul[@class='nav-pills categorymenu']/li")));
		return driver.findElements(By.xpath("//ul[@class='nav-pills categorymenu']/li"));
	}
	
	public void randomCategory() {
		List<WebElement> categories = MainCategories();
		int random = (int) (Math.random() * categories.size());
		categories.get(random).click();
	}
}
