package tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.TestDataReader;

public class AssignmentTestScripts {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		
		WebDriverManager.chromiumdriver().setup();
		WebDriver driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
		driver.get("https://automationteststore.com/");
		
		//1.Home page & Category Verification
		
		WebDriverWait wait  = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='nav-pills categorymenu']/li")));
	    List<WebElement> categories = driver.findElements(By.xpath("//ul[@class='nav-pills categorymenu']/li"));
	    System.out.println("Main Categories: ");
	    for (WebElement category : categories) {
            String text = category.getText().trim();
            if (!text.isEmpty()) {
                System.out.println("- " + text);
            }
        }
	    
	   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='  Skincare']")));
	   WebElement element = driver.findElement(By.xpath("//a[text()='  Skincare']"));
	   Actions act = new Actions(driver);
	   act.moveToElement(element).perform();
	   element.click();
	   
	   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='col-md-2 col-sm-2 col-xs-6 align_center']")));
	   List<WebElement> products = driver.findElements(By.xpath("//li[@class='col-md-2 col-sm-2 col-xs-6 align_center']"));
	   long visibleCount = products.stream().filter(WebElement::isDisplayed).count();

       System.out.println("Visible products found: " + visibleCount);
       if (visibleCount >= 3) {
           System.out.println("Category contains at least 3 visible products.");
       } else {
           System.out.println("Less than 3 visible products found.");
       }
	   
	   //2.Product Selection & Cart Addition
       
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Eyes']")));
       driver.findElement(By.xpath("//a[text()='Eyes']")).click();
       
       driver.findElement(By.xpath("//a[text()='Eye master']")).click();
       driver.findElement(By.xpath("//a[@class='cart']")).click();
       
       driver.navigate().back();
       driver.navigate().back();
       
       driver.findElement(By.xpath("//a[text()='Absolue Eye Precious Cells']")).click();
       driver.findElement(By.xpath("//a[@class='cart']")).click();
       
  
		/*String title = driver.getTitle();
    	assert title.equals("Shopping Cart") : "Title doesn't match" ;
    	
    	WebElement cartTable = driver.findElement(By.xpath("//table[@class='table table-striped table-bordered'][1]"));
    	
    	List<WebElement> rows = cartTable.findElements(By.cssSelector("tbody tr"));
    	 for (WebElement row : rows) {
             String name = row.findElement(By.cssSelector("td:a")).getText();
             String unitPrice = row.findElement(By.cssSelector("td:nth-child(4)")).getText();
             String quantity = row.findElement(By.cssSelector("td:nth-child(5) input")).getAttribute("value");
           
              
             System.out.println("Item Name: " + name);
             System.out.println("Unit Price: " + unitPrice);
             System.out.println("Quantity: " + quantity);
             System.out.println("------------------------------");
         
    	 }*/
    	 
       
       driver.navigate().back();
       driver.navigate().back();
       driver.navigate().back();
       driver.navigate().back();
       
       driver.findElement(By.xpath("//span[text()='Cart'][1]")).click();
       driver.navigate().back();
       
     
	   //3.Cart & Checkout Workflow
       
       driver.findElement(By.xpath("//span[text()='Cart'][1]")).click();
       
       driver.findElement(By.id("cart_checkout1")).click();
       
       driver.findElement(By.xpath("//input[@value='register']")).isSelected();
       driver.findElement(By.xpath("//button[@title='Continue']")).click();
       
       @SuppressWarnings("unused")
	   Map<String, String> data = TestDataReader.readTestData("testdata.csv");
       driver.findElement(By.name("firstname")).sendKeys("John");
       driver.findElement(By.name("lastname")).sendKeys("Doe");
       driver.findElement(By.name("email")).sendKeys("john.doe@example.com");
       driver.findElement(By.name("address_1")).sendKeys("13th Street. 47 W 13th St");
       driver.findElement(By.name("city")).sendKeys("Washington");
       driver.findElement(By.name("zone_id")).click();
       driver.findElement(By.xpath("//option[text()='Edinburgh']")).click();
       driver.findElement(By.name("postcode")).sendKeys("NY 10011");
       
       driver.findElement(By.name("country_id")).click();
       driver.findElement(By.xpath("//option[text()='United States']")).click();
       
       driver.findElement(By.name("loginname")).sendKeys("JohnDoe");
       driver.findElement(By.name("password")).sendKeys("Password123");
       driver.findElement(By.name("confirm")).sendKeys("Password123");
       
       
      WebElement act1 = driver.findElement(By.xpath("//input[@type='checkbox']"));
      act1.isEnabled();
      driver.findElement(By.xpath("//button[@title='Continue']")).click();
    
      
       //4.Negative Scenario (Validation Testing)
	   
	   driver.findElement(By.xpath("//a[text()='Login or register']")).click();
	   driver.findElement(By.name("loginname")).sendKeys("John");
	   
	   driver.findElement(By.xpath("//button[@title='Login']")).click();
	   
	   TakesScreenshot ts = (TakesScreenshot)driver;
	   File src = ts.getScreenshotAs(OutputType.FILE);
	   File screenshots = new File("FailedScript.png");
	   FileUtils.copyFile(src, screenshots);
	          
	}
}

