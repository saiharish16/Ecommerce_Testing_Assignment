package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds wait
    }

    public void addToCart() {
        // Capture product details before adding to cart
        String productName = getProductName();
        String productPrice = getProductPrice();
        String productURL = driver.getCurrentUrl();
        int productQuantity = getProductQuantity();

        // Check if the add to cart button is available
        try {
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".add-to-cart")));
            addToCartButton.click();

            // Validate if the cart counter updates correctly
            validateCartCounter(productQuantity);
        } catch (Exception e) {
            logOutOfStockProduct(productName, productPrice, productURL);
        }
    }

    private String getProductName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-name"))).getText();
    }

    private String getProductPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-price"))).getText();
    }

    private int getProductQuantity() {
        // Assuming there's an input field for quantity
        String quantityText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-quantity"))).getAttribute("value");
        return Integer.parseInt(quantityText);
    }

    private void validateCartCounter(int expectedQuantity) {
        // Wait for the cart counter to be visible and get its value
        WebElement cartCounter = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart-counter")));
        int actualQuantity = Integer.parseInt(cartCounter.getText());

        if (actualQuantity != expectedQuantity) {
            logCartCounterMismatch(expectedQuantity, actualQuantity);
        }
    }

    private void logCartCounterMismatch(int expected, int actual) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt", true))) {
            writer.write("Cart counter mismatch: Expected " + expected + ", but got " + actual + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logOutOfStockProduct(String productName, String productPrice, String productURL) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt", true))) {
            writer.write("Out of stock or button missing for product: " + productName + "\n");
            writer.write("Price: " + productPrice + "\n");
            writer.write("URL: " + productURL + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProductDetails() {
        String productName = getProductName();
        String productPrice = getProductPrice();
        return productName + " - " + productPrice;
    }
}
