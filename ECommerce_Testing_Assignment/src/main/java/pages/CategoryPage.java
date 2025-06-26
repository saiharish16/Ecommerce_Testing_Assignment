package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CategoryPage {
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	public CategoryPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}
	
	public boolean visibleProducts() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='col-md-2 col-sm-2 col-xs-6 align_center']")));
		List<WebElement> products = driver.findElements(By.xpath("//li[@class='col-md-2 col-sm-2 col-xs-6 align_center']"));
		return products.size() >= 3;
	}

}
