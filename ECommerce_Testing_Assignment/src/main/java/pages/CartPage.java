package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	public CartPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	
	public void navigateToCart() {
		WebElement cartLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.cart")));
        cartLink.click();
    }

	public boolean validateCartItems() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart-item")));
		
		List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart-item"));
    
    // Check if there are items in the cart
    if (cartItems.isEmpty()) {
        return false; // No items in the cart
    }

    // Loop through each cart item and validate details
    for (WebElement item : cartItems) {
        // Validate item name
        String itemName = item.findElement(By.cssSelector(".item-name")).getText();
        if (itemName.isEmpty()) {
            return false; // Item name is empty
        }

        // Validate item price
        String itemPrice = item.findElement(By.cssSelector(".item-price")).getText();
        if (itemPrice.isEmpty()) {
            return false; // Item price is empty
        }

        // Validate item quantity
        String itemQuantity = item.findElement(By.cssSelector(".item-quantity")).getText();
        if (itemQuantity.isEmpty() || Integer.parseInt(itemQuantity) <= 0) {
            return false; // Item quantity is invalid
        }
    }

    // If all validations pass
    return true;
}
}