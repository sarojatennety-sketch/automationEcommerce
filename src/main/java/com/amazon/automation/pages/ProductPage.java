package com.amazon.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage {
    WebDriver driver;
    WebDriverWait wait;

    // Product page locators
    @FindBy(id = "add-to-cart-button")
    WebElement addToCartButton;  // Add to cart button on product page

    @FindBy(id = "nav-cart")
    WebElement cartLink;  // Cart icon/link in navigation

    // Alternative locators if primary doesn't work
    @FindBy(css = "input[value='Add to cart']")
    WebElement addToCartAlt;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Switches to the product detail page tab
     * Since clicking on a product opens it in a new tab,
     * this method handles switching to the new tab
     */
    private void switchToProductTab() {
        try {
            // Get all window handles
            Set<String> windowHandles = driver.getWindowHandles();
            
            if (windowHandles.size() > 1) {
                // Switch to the last opened tab (new product tab)
                String newTab = null;
                for (String handle : windowHandles) {
                    newTab = handle;
                }
                driver.switchTo().window(newTab);
                System.out.println("Switched to product detail tab");
                
                // Wait for the page to load
                Thread.sleep(2000);
                wait.until(ExpectedConditions.visibilityOf(addToCartButton));
                System.out.println("Product detail page loaded");
            }
        } catch (Exception e) {
            System.out.println("Error switching to product tab: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds the product to cart
     * This method:
     * 1. Switches to the product detail tab (if opened in new tab)
     * 2. Finds and clicks the "Add to cart" button
     * 3. Waits for confirmation
     *
     * @return this for method chaining
     */
    public ProductPage addToCart() {
        try {
            // Switch to product tab if a new tab was opened
            switchToProductTab();
            
            // Wait for Add to Cart button to be visible and clickable
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
            System.out.println("Add to Cart button is visible and clickable");

            // Click the Add to Cart button
            addToCartButton.click();
            System.out.println("Clicked Add to Cart button");

            // Wait for cart count to update (item added confirmation)
            Thread.sleep(2000);  // Wait for the add to cart animation
            System.out.println("Item added to cart successfully");

            // Return this for method chaining
            return this;
        } catch (Exception e) {
            System.out.println("Error adding to cart: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Navigates to the cart page from product detail page
     * This method:
     * 1. Clicks on the cart icon in the navigation
     * 2. Waits for cart page to load
     *
     * @return CartPage object
     */
    public CartPage goToCart() {
        try {
            // Wait for cart link to be clickable
            wait.until(ExpectedConditions.elementToBeClickable(cartLink));
            System.out.println("Cart icon is visible and clickable");

            // Click on cart
            cartLink.click();
            System.out.println("Clicked on cart, navigating to cart page...");

            // Wait for cart page to load
            Thread.sleep(2000);
            System.out.println("Cart page loaded");

            // Return CartPage object
            return new CartPage(driver);
        } catch (Exception e) {
            System.out.println("Error navigating to cart: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}