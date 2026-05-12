package com.amazon.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage {
    WebDriver driver;
    WebDriverWait wait;

    // Cart page locators
    @FindBy(name = "proceedToRetailCheckout")
    WebElement proceedToCheckoutButton;  // Proceed to Buy button on cart page

    // Alternative locators if primary doesn't work
    @FindBy(css = "input[value='Proceed to checkout']")
    WebElement proceedToCheckoutAlt;

    @FindBy(id = "sc-buy-box-ptc-button")
    WebElement proceedToBuyButton;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Proceeds to the checkout page
     * This method:
     * 1. Finds and clicks the "Proceed to Buy" button
     * 2. Waits for the checkout page to load
     *
     * @return CheckoutPage object for method chaining
     */
    public CheckoutPage proceedToCheckout() {
        try {
            // Wait for Proceed to Checkout button to be visible and clickable
            wait.until(ExpectedConditions.elementToBeClickable(proceedToBuyButton));
            System.out.println("Proceed to Buy button is visible and clickable");

            // Click the Proceed to Checkout button
            proceedToBuyButton.click();
            System.out.println("Clicked Proceed to Buy button");

            // Wait for checkout page to load
            Thread.sleep(2000);
            System.out.println("Checkout page loaded");

            // Return CheckoutPage object for method chaining
            return new CheckoutPage(driver);
        } catch (Exception e) {
            System.out.println("Error proceeding to checkout: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}