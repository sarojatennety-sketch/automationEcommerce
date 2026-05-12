package com.amazon.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    // Search page locators
    @FindBy(id = "twotabsearchtextbox")
    WebElement searchBox;  // Search input field

    @FindBy(id = "nav-search-submit-button")
    WebElement searchButton;  // Search submit button (Go)

    // Product result locators
    @FindBy(css = "div[data-cy='asin-faceout-container'] a h2 span")
    WebElement firstProductTitle;  // First product title in search results

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Searches for a product on Amazon
     * This method:
     * 1. Finds the search input field
     * 2. Enters the search query
     * 3. Clicks the search button (Go)
     *
     * @param product The product name to search for (e.g., "storage box and organiser")
     */
    public void searchProduct(String product) {
        try {
            // Wait for search box to be visible
            wait.until(ExpectedConditions.visibilityOf(searchBox));
            System.out.println("Search box is visible");

            // Clear any existing text and enter search query
            searchBox.clear();
            searchBox.sendKeys(product);
            System.out.println("Search query entered: " + product);

            // Wait for search button to be clickable and click it
            wait.until(ExpectedConditions.elementToBeClickable(searchButton));
            searchButton.click();
            System.out.println("Search button clicked, waiting for results...");

            // Wait for search results to load
            wait.until(ExpectedConditions.visibilityOf(firstProductTitle));
            System.out.println("Search results loaded");
        } catch (Exception e) {
            System.out.println("Error during search: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Selects the first product from search results
     * This method:
     * 1. Finds the first product in the search results
     * 2. Clicks on it to open the product page
     *
     * @return ProductPage object for method chaining
     */
    public ProductPage selectFirstProduct() {
        try {
            // Wait for first product to be visible and clickable
            wait.until(ExpectedConditions.elementToBeClickable(firstProductTitle));
            System.out.println("First product is visible: " + firstProductTitle.getText());

            // Click on the first product to open product page
            firstProductTitle.click();
            System.out.println("Clicked on first product, loading product page...");

            // Wait for product page to load
            Thread.sleep(2000);  // Small delay for page to load
            
            // Return ProductPage object for method chaining
            return new ProductPage(driver);
        } catch (Exception e) {
            System.out.println("Error selecting first product: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

   /**
     * Gets the first product title from search results
     * Used for assertions in tests
     *
     * @return String containing the first product title
     */
    public String getFirstProductTitle() {
        try {
            wait.until(ExpectedConditions.visibilityOf(firstProductTitle));
            String productTitle = firstProductTitle.getText();
            System.out.println("First product title: " + productTitle);
            return productTitle;
        } catch (Exception e) {
            System.out.println("Error fetching first product title: " + e.getMessage());
            return "";
        }
    }
}