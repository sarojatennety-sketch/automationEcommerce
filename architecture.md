# Selenium Page Object Model Architecture Guide

This guide provides a comprehensive framework for setting up Selenium automation projects using the Page Object Model (POM) architecture. This architecture promotes maintainable, reusable, and scalable test automation code.

## Introduction

Page Object Model is a design pattern that creates an object repository for web UI elements. Each web page is represented as a class, and the various elements on the page are defined as variables within that class. The class also contains methods that perform operations on those elements.

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Web browser (Chrome, Firefox, etc.)
- IDE (VS Code, IntelliJ IDEA, Eclipse)

## Setting up a Maven Project

1. Create a new directory for your project.
2. Open a terminal in that directory and run:
   ```bash
   mvn archetype:generate -DgroupId=com.example -DartifactId=my-automation-project -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
   ```
3. Navigate into the project directory.

## Adding Dependencies

Update the `pom.xml` file with the following dependencies:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/POM/4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>my-automation-project</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- Selenium WebDriver -->
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>4.15.0</version>
        </dependency>
        
        <!-- TestNG for testing framework -->
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>7.8.0</version>
            <scope>test</scope>
        </dependency>
        
        <!-- WebDriverManager for automatic driver management -->
        <dependency>
            <groupId>io.github.bonigarcia</groupId>
            <artifactId>webdrivermanager</artifactId>
            <version>5.5.3</version>
        </dependency>
    </dependencies>
</project>
```

## Project Structure

Organize your project with the following structure:

```
my-automation-project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── automation/
│   │   │               ├── pages/          # Page Object classes
│   │   │               └── utils/          # Utility classes
│   │   └── resources/
│   └── test/
│       ├── java/
│       │   └── com/
│       │           └── example/
│       │               └── automation/
│       │                   ├── base/       # Base test classes
│       │                   └── tests/      # Test classes
│       └── resources/
├── pom.xml
├── README.md
└── architecture.md
```

## BaseTest Class

Create a base test class that handles WebDriver setup and teardown. This class should be extended by all test classes.

### Corporate / Managed Environment Support

In restricted environments where automatic ChromeDriver downloads are blocked by application control policies, the project supports a manual ChromeDriver override. Set either:

- `CHROME_DRIVER_PATH` environment variable
- `-Dwebdriver.chrome.driver=C:\allowed\path\chromedriver.exe`

This allows the test to use a locally approved `chromedriver.exe` instead of the default WebDriverManager cache path.

```java
package com.example.automation.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--start-maximized");

        // Support explicit driver path for restricted environments
        String chromeDriverPath = System.getProperty("webdriver.chrome.driver");
        if (chromeDriverPath == null || chromeDriverPath.isEmpty()) {
            chromeDriverPath = System.getenv("CHROME_DRIVER_PATH");
        }

        if (chromeDriverPath != null && !chromeDriverPath.isEmpty()) {
            File driverExecutable = new File(chromeDriverPath);
            if (!driverExecutable.exists() || !driverExecutable.isFile()) {
                throw new RuntimeException("ChromeDriver executable not found at: " + chromeDriverPath);
            }
            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(driverExecutable)
                    .usingAnyFreePort()
                    .build();
            driver = new ChromeDriver(service, options);
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        }

        // Set timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
```

## Page Object Classes

Each page in your application should have a corresponding Page Object class. These classes encapsulate the elements and actions for that page.

### Example Page Class Structure

```java
package com.example.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Page Elements
    @FindBy(id = "email")
    private WebElement emailField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(id = "login-button")
    private WebElement loginButton;

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Action Methods
    public LoginPage enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    public HomePage clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
        return new HomePage(driver);
    }

    // Fluent method for complete login flow
    public HomePage login(String email, String password) {
        return enterEmail(email)
                .enterPassword(password)
                .clickLogin();
    }
}
```

### Key Principles for Page Classes

1. **Encapsulation**: Keep element locators and actions within the page class.
2. **Method Chaining**: Return `this` or next page objects for fluent interfaces.
3. **Explicit Waits**: Use WebDriverWait for reliable element interactions.
4. **PageFactory**: Initialize elements using @FindBy annotations.
5. **Single Responsibility**: Each method should perform one clear action.

## Test Classes

Test classes extend BaseTest and orchestrate the automation flows using page objects.

```java
package com.example.automation.tests;

import com.example.automation.base.BaseTest;
import com.example.automation.pages.LoginPage;
import com.example.automation.pages.HomePage;
import org.testng.annotations.Test;
import org.testng.Assert;

public class ExampleTest extends BaseTest {

    @Test
    public void testLoginFlow() {
        // Navigate to application
        driver.get("https://example.com");
        
        // Perform login
        HomePage homePage = new LoginPage(driver)
            .enterEmail("user@example.com")
            .enterPassword("password")
            .clickLogin();
        
        // Verify login success
        Assert.assertTrue(homePage.isUserLoggedIn(), "Login should be successful");
    }

    @Test
    public void testFailedLogin() {
        driver.get("https://example.com");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("invalid@example.com")
                .enterPassword("wrongpassword")
                .clickLogin();
        
        // Verify error message
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
    }
}
```

## Running Tests

### Using Maven

```bash
mvn clean test
```

### Using TestNG

```bash
mvn test -Dtest=ExampleTest
```

### Using IDE

Right-click on test class and run as TestNG test.

## Best Practices

1. **Locator Strategy**: Prefer ID > Name > CSS Selector > XPath. Avoid brittle locators.
2. **Wait Strategies**: Use explicit waits over implicit waits. Avoid Thread.sleep().
3. **Test Data**: Externalize test data from code. Use properties files or data providers.
4. **Assertions**: Use meaningful assertions with descriptive messages.
5. **Test Isolation**: Each test should be independent and clean up after itself.
6. **Page Object Updates**: Update page objects when UI changes, not test code.
7. **Naming Conventions**: Use descriptive names for methods and variables.
8. **Error Handling**: Implement proper exception handling and logging.
9. **CI/CD Integration**: Ensure tests run in headless mode for CI pipelines.
10. **Maintenance**: Regularly review and update locators as the application evolves.

## Extending the Framework

- **Utils Package**: Add utility classes for common operations (screenshot capture, data reading, etc.)
- **Custom Waits**: Create custom expected conditions for complex scenarios
- **Data Providers**: Implement TestNG data providers for data-driven testing
- **Reporting**: Integrate with ExtentReports or Allure for detailed test reports
- **Parallel Execution**: Configure TestNG for parallel test execution
- **Cross-Browser Testing**: Extend BaseTest to support multiple browsers

This architecture provides a solid foundation for scalable and maintainable Selenium automation projects.