# Amazon Automation Project

This project automates the login, product search, add to cart, and checkout process on Amazon.in using Selenium WebDriver with Page Object Model architecture.

## Architecture

- **Base**: Contains `BaseTest` for WebDriver setup and teardown.
- **Pages**: Page Object Model classes for each page (LoginPage, HomePage, ProductPage, CartPage, CheckoutPage).
- **Tests**: Test classes that orchestrate the automation flow.
- **Utils**: Placeholder for utility classes (e.g., waits, common actions).

## Prerequisites

- Java 11 or higher (ensure JAVA_HOME is set, e.g., C:\Program Files\Java\jdk-21.0.10)
- Maven 3.6+ (ensure Maven bin is in PATH or use full path, e.g., C:\Program Files\apache-maven-3.9.15\bin)
- Google Chrome browser
- ChromeDriver matching the installed Chrome version when application control policies block automatic downloads

## Recreating This Project from Scratch

This README.md contains all the information needed to recreate this project on any system. Follow these steps:

### 1. Create Maven Project Structure

Create a new directory for the project (e.g., `automationEcommerce`) and set up the standard Maven directory structure:

```
automationEcommerce/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── amazon/
│   │   │           └── automation/
│   │   │               ├── pages/
│   │   │               └── utils/
│   │   └── resources/
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── amazon/
│       │           └── automation/
│       │               ├── base/
│       │               └── tests/
│       └── resources/
├── pom.xml
└── README.md
```

### 2. Create pom.xml

Create `pom.xml` in the project root with the following content:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/POM/4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.amazon</groupId>
    <artifactId>automationEcommerce</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>4.15.0</version>
        </dependency>
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>7.8.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.github.bonigarcia</groupId>
            <artifactId>webdrivermanager</artifactId>
            <version>5.5.3</version>
        </dependency>
    </dependencies>
</project>
```

### 3. Implement Base Classes

Create `BaseTest.java` in `src/test/java/com/amazon/automation/base/`:

```java
package com.amazon.automation.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
```

### 4. Implement Page Classes

Create the page classes as described in the Page Object Model Architecture section below. Use the code from the existing files or implement based on the method descriptions.

### 5. Implement Test Classes

Create `AmazonTest.java` in `src/test/java/com/amazon/automation/tests/` with the test methods described in the Complete Test Flow section.

### 6. Update Credentials and Run

Update the email and password in `AmazonTest.java` with valid Amazon credentials, then run the tests as described in the Running the Tests section.

## Setup

1. Clone or download the project.
2. Update the email and password in `AmazonTest.java` with valid Amazon credentials.
3. If the office environment blocks automatic ChromeDriver execution, set `CHROME_DRIVER_PATH` or `webdriver.chrome.driver` to a locally approved driver executable.

## Running the Tests

Run the following commands in the project root terminal:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21.0.10'
$env:CHROME_DRIVER_PATH = 'C:\allowed\path\chromedriver.exe'
& 'C:\Program Files\apache-maven-3.9.15\bin\mvn.cmd' test
```

This sets JAVA_HOME and, if needed, an explicit ChromeDriver path before running the Maven test.

### Complete Test Flow

The automation test performs the following end-to-end flow:

#### **Phase 1: LOGIN**
1. Open Amazon.in home page
2. Click on "Hello, sign in" menu (Account & Lists)
3. Click on "Sign in" from the dropdown
4. Fill in email address ("test@example.com")
5. Click Continue button

#### **Phase 2: SEARCH**
1. On home page, locate the search box
2. Enter search query: "storage box and organiser"
3. Click the search button (Go)
4. Wait for search results to load

#### **Phase 3: PRODUCT SELECTION**
1. Identify the first product in search results
2. Click on the product to open product details page
3. Product page loads with item details, price, ratings, etc.

#### **Phase 4: ADD TO CART**
1. Locate the "Add to Cart" button on product page
2. Click "Add to Cart" button
3. Item is added to cart with visual confirmation
4. Navigate to cart by clicking the Cart icon

#### **Phase 5: CHECKOUT**
1. Cart page displays with item details and subtotal
2. Click "Proceed to Buy" button
3. Checkout page loads with order summary

### Test Console Output

The test prints detailed messages at each step, making it easy to follow and debug. Example output:

```
========== LOGIN FLOW ==========
Step 1: Opened Amazon.in home page
Account & Lists menu is visible
Sign in link is visible in dropdown
Email entered: test@example.com
Step 2: Completed email entry and clicked continue

========== SEARCH FLOW ==========
Step 3: Searched for 'storage box and organiser'
Search results loaded

========== PRODUCT SELECTION FLOW ==========
Step 4: Selected first product from search results
Clicked on first product, loading product page...

========== CART FLOW ==========
Step 5: Added product to cart
Step 6: Navigated to cart
Step 7: Clicked Proceed to Buy button

========== TEST COMPLETED ==========
```

### Negative Test Cases

The project includes negative test cases to validate error handling and failure scenarios:

- **testAmazonAutomation_FailedSearchDemo**: This test demonstrates a failed search scenario where the test searches for "Flipkart" on Amazon.in and intentionally asserts that the first product title contains "Flipkart". Since Amazon.in search results for "Flipkart" will not contain products with "Flipkart" in the title (as Flipkart is a competitor), this assertion will fail, showcasing how the framework handles test failures. The test follows the same login flow as the positive test but diverges in the search phase to test invalid or unexpected search results.

### Page Object Model Architecture

The project uses Page Object Model (POM) architecture for better code organization. For detailed information on setting up a similar architecture from scratch, refer to [architecture.md](architecture.md).

- **[LoginPage](src/main/java/com/amazon/automation/pages/LoginPage.java)** - Handles login flow
  - `navigateToSignIn()` - Opens sign-in page
  - `fillEmailAndContinue()` - Enters email and continues

- **[HomePage](src/main/java/com/amazon/automation/pages/HomePage.java)** - Handles home page and search
  - `searchProduct()` - Searches for a product
  - `selectFirstProduct()` - Selects first product from results

- **[ProductPage](src/main/java/com/amazon/automation/pages/ProductPage.java)** - Handles product details page
  - `addToCart()` - Adds product to cart
  - `goToCart()` - Navigates to cart

- **[CartPage](src/main/java/com/amazon/automation/pages/CartPage.java)** - Handles shopping cart page
  - `proceedToCheckout()` - Proceeds to checkout

- **[CheckoutPage](src/main/java/com/amazon/automation/pages/CheckoutPage.java)** - Handles checkout page
  - Placeholder for future checkout logic

### Extending the Automation

To add new functionalities:

1. **Create a new Page class** in `src/main/java/com/amazon/automation/pages/`
2. **Define locators** using `@FindBy` annotations
3. **Add action methods** that interact with elements
4. **Use method chaining** to return Page objects for flow continuation
5. **Update the test class** in `AmazonTest.java` to use the new methods

### Notes

- Locators may change over time; update them as needed in respective page classes
- Use valid Amazon credentials to test login functionality
- This is for educational purposes; respect Amazon's terms of service
- Each page class has detailed comments explaining what each method does

## Future Extensions

- Add more page classes for additional functionalities.
- Implement explicit waits in utils.
- Add data-driven testing with TestNG.
- Integrate with CI/CD pipelines.
- Add reporting with ExtentReports or Allure.

## Notes

- Locators may change over time; update them as needed.
- Use valid Amazon credentials to avoid CAPTCHA or blocks.
- This is for educational purposes; respect Amazon's terms of service.