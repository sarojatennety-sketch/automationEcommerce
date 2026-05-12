package com.amazon.automation.tests;

import com.amazon.automation.base.BaseTest;
import com.amazon.automation.pages.HomePage;
import com.amazon.automation.pages.LoginPage;
import com.amazon.automation.pages.ProductPage;
import com.amazon.automation.pages.CartPage;
import org.testng.annotations.Test;
import org.testng.Assert;


public class AmazonTest extends BaseTest {

    @Test
    public void testAmazonAutomation() {
        // ==================== LOGIN FLOW ====================
        System.out.println("\n========== LOGIN FLOW ==========");
        
        // Step 1: Open Amazon.in home page
        driver.get("https://www.amazon.in");
        System.out.println("Step 1: Opened Amazon.in home page");
        
        // Step 2: Navigate to sign-in
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToSignIn();
        loginPage.fillEmailAndContinue("rojasmily1234@gmail.com");
        System.out.println("Step 2: Completed email entry and clicked continue");
        
        // Step 3: Fill password and sign in
        HomePage homePage = loginPage.fillPasswordAndSignIn("Shannupapa");
        System.out.println("Step 3: Completed password entry and signed in\n");
        
        // ==================== SEARCH FLOW ====================
        System.out.println("========== SEARCH FLOW ==========");
        
        // Step 4: Search for product
        homePage.searchProduct("storage box and organiser");
        System.out.println("Step 4: Searched for 'storage box and organiser'");
        
        // ==================== PRODUCT SELECTION FLOW ====================
        System.out.println("========== PRODUCT SELECTION FLOW ==========");
        
        // Step 5: Select first product
        ProductPage productPage = homePage.selectFirstProduct();
        System.out.println("Step 5: Selected first product from search results");
        
        // Step 6: Add to cart
        productPage = productPage.addToCart();
        System.out.println("Step 6: Added product to cart");
        
        // Step 7: Go to cart
        CartPage cartPage = productPage.goToCart();
        System.out.println("Step 7: Navigated to cart\n");
        
        // ==================== CHECKOUT FLOW ====================
        System.out.println("========== CHECKOUT FLOW ==========");
        
        // Step 8: Proceed to checkout
        cartPage.proceedToCheckout();
        System.out.println("Step 8: Clicked Proceed to Buy button\n");
        
        System.out.println("========== TEST COMPLETED ==========\n");
    }

    @Test
    public void testAmazonAutomation_FailedSearchDemo() {
        System.out.println("\n========== FAILED SEARCH FLOW ==========");
        
        driver.get("https://www.amazon.in");
        System.out.println("Step 1: Opened Amazon.in home page");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToSignIn();
        loginPage.fillEmailAndContinue("rojasmily1234@gmail.com");
        System.out.println("Step 2: Completed email entry and clicked continue");
        
        HomePage homePage = loginPage.fillPasswordAndSignIn("Shannupapa");
        System.out.println("Step 3: Completed password entry and signed in\n");
        
        // ==================== SEARCH FLOW (FAILED CASE) ====================
        homePage.searchProduct("Flipkart");
        System.out.println("Step 4b: Searched for 'Flipkart'");
        
        // Intentionally wrong expectation: expecting results to contain "Flipkart"
        String firstProductTitle = homePage.getFirstProductTitle();
        Assert.assertTrue(firstProductTitle.contains("Flipkart"),
        "Expected first product title to contain 'Flipkart', but got: " + firstProductTitle);
        
        System.out.println("========== FAILED SEARCH FLOW COMPLETED ==========\n");
    }
}