# 🛒 ECommerce_Testing_Assignment

Automation Testing Suite for [https://automationteststore.com](https://automationteststore.com), built using **Selenium WebDriver**, **Java**, and **TestNG**.  
This project is submitted as part of the Automation Testing Internship at **Ziegler Aerospace**.

---

## 📁 Project Structure

ECommerce_Testing_Assignment/
│
├── src/ → Source code (organized by POM)
│ ├── pages/
│ ├── tests/
│ └── utils/
│
├── testdata.csv → Test input data (email, names, etc.)
├── screenshots/ → Store only failure screenshots
├── report.txt → Execution log and validations summary
├── README.md → Setup instructions, explanation, known issues
├── pom.xml / build.gradle → If using Maven/Gradle
├── testng.xml → TestNG runner file (if applicable)
Assignment Instructions:
1. Homepage & Category Verification
- Navigate to the homepage: https://automationteststore.com/
- Dynamically detect and print all main category names (e.g., Men, Skincare, Fragrance).
- Navigate into any random category using dynamic logic (not hardcoded names).
- Assert that the category has at least 3 visible products.
2. Product Selection & Cart Addition
- Randomly select and add 2 products to the cart.
- Capture and log each product's:
 - Name
 - Price
 - Quantity
 - Product URL
- Validate if the cart counter updates correctly.
- If a product is out of stock or button is missing, log it in report.txt.
3. Cart & Checkout Workflow
- Navigate to the shopping cart.
- Assert that both added items are listed with correct price and total.
- Proceed to checkout and simulate user registration (use dummy data from testdata.csv).
4. Negative Scenario (Validation Testing)
- On the registration page:
 - Leave one required field empty (e.g., password or last name).
 - Attempt to submit and assert the error message is displayed.
 - Take a screenshot upon validation failure and store it in /screenshots/.
5. Reporting
- After test completion:
 - Log total product cost, failed validations, and skipped elements in a report.txt.
 - Ensure any failures automatically trigger a screenshot capture.
Technical Guidelines
Parameter Requirement
Language Java
Framework Selenium WebDriver
Test Framework TestNG (preferred) or JUnit
Design Pattern Page Object Model (POM)
Wait Strategy WebDriverWait (no Thread.sleep)
Test Data Externalized via .csv
Error Capture Screenshot on failure

