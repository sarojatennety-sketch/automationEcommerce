package com.amazon.automation.base;

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
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");

        // First check system property
        String chromeDriverPath = System.getProperty("webdriver.chrome.driver");
        if (chromeDriverPath == null || chromeDriverPath.isEmpty()) {
            // Then check environment variable
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
            // Fallback: explicit safe path (office setup)
            System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");
            driver = new ChromeDriver(options);
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}