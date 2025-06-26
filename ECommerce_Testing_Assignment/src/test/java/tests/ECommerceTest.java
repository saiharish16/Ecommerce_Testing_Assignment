package tests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.TestDataReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class ECommerceTest {
    private WebDriver driver;
    @SuppressWarnings("unused")
	private WebDriverWait wait;
    private HomePage homePage;
    private CategoryPage categoryPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private RegistrationPage registrationPage;
    private double totalProductCost = 0.0;
    private StringBuilder reportLog = new StringBuilder();

    @BeforeClass
    public void setUp() {
        // Initialize WebDriver and other components
        driver = new ChromeDriver();
        new WebDriverWait(driver, Duration.ofSeconds(30));
        homePage = new HomePage(driver);
        categoryPage = new CategoryPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        registrationPage = new RegistrationPage(driver);
    }

    @DataProvider(name = "registrationData")
    public Object[][] registrationData() {
        // Read data from testdata.csv
        TestDataReader reader = new TestDataReader("testdata.csv");
        List<String[]> data = reader.readData();
        Object[][] dataObject = new Object[data.size()][4];
        for (int i = 0; i < data.size(); i++) {
            dataObject[i] = data.get(i);
        }
        return dataObject;
    }

    @Test
    (dataProvider = "registrationData")
    public void testNegativeRegistration(String firstName, String lastName, String email, String password) {
        // Navigate to the registration page
        driver.get("https://automationteststore.com/register"); // Adjust the URL as needed

        // Fill the registration form
        registrationPage.fillRegistrationForm(firstName, lastName, email, password);
        registrationPage.submitRegistration();

        // Assert that the error message is displayed
        if (!registrationPage.isErrorMessageDisplayed()) {
            logFailedValidation("Error message not displayed for invalid input.");
            takeScreenshot("screenshots/registration_error.png");
        }

        // Log the registration attempt
        reportLog.append("Registration attempt with email: ").append(email).append("\n");
    }

    @Test
    public void testECommerceWorkflow() {
        // Navigate to the home page
        homePage.navigate();
        homePage.randomCategory();

        // Validate that the category has at least 3 visible products
        Assert.assertTrue(categoryPage.visibleProducts(), "Category does not have at least 3 products.");

        // Add products to cart and validate
        productPage.addToCart();
        String productDetails = productPage.getProductDetails();
        totalProductCost += extractProductPrice(productDetails); // Assuming productDetails contains price info

        cartPage.navigateToCart();
        if (!cartPage.validateCartItems()) {
            logFailedValidation("Cart items validation failed.");
            takeScreenshot("screenshots/cart_validation_error.png");
        }
    }

    @AfterClass
    public void tearDown() {
        // Log total product cost and any failed validations
        logReport();

        // Close the browser
        driver.quit();
    }

    private double extractProductPrice(String productDetails) {
        // Assuming productDetails is in the format "Product Name - $Price"
        String priceString = productDetails.split("-")[1].trim().replace("$", "");
        return Double.parseDouble(priceString);
    }

    private void logFailedValidation(String message) {
        reportLog.append("Validation Failed: ").append(message).append("\n");
    }

    private void takeScreenshot(String filePath) {
    	File screenshots = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            File destinationFile = new File(filePath);
            FileUtils.copyFile(screenshots, destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logReport() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt", true))) {
            writer.write("Total Product Cost: $" + totalProductCost + "\n");
            writer.write(reportLog.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
