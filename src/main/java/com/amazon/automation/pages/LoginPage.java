package com.amazon.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    // Locators from Amazon.in home page
    @FindBy(id = "nav-link-accountList")
    WebElement accountListMenu;  // "Hello, sign in" menu button

    @FindBy(css = "a[data-nav-role='signin'][class*='nav-action-signin-button']")
    WebElement signInLinkInDropdown;  // "Sign in" link in the dropdown menu

    // Locators from Sign-in page
    @FindBy(id = "ap_email_login")
    WebElement emailField;  // Email/Mobile input field

    @FindBy(id = "continue")
    WebElement continueButton;  // Continue button

    @FindBy(id = "ap_password")
    WebElement passwordField;  // Password input field

    @FindBy(id = "signInSubmit")
    WebElement signInButton;  // Sign In button

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Navigates to the sign-in page by clicking on Account & Lists menu and then Sign in button
     * This method handles the flow:
     * 1. Hover over or click "Hello, sign in" menu
     * 2. Click on "Sign in" from the dropdown
     */
    public void navigateToSignIn() {
        try {
            // Wait for the Account & Lists menu to be visible
            wait.until(ExpectedConditions.visibilityOf(accountListMenu));
            System.out.println("Account & Lists menu is visible");

            // Hover over the Account & Lists menu to show the dropdown
            actions.moveToElement(accountListMenu).perform();
            System.out.println("Hovered over Account & Lists menu");

            // Wait for the Sign in link to appear in the dropdown
            wait.until(ExpectedConditions.visibilityOf(signInLinkInDropdown));
            System.out.println("Sign in link is visible in dropdown");

            // Click on the Sign in link
            signInLinkInDropdown.click();
            System.out.println("Clicked on Sign in link");

            // Wait for the email input field to appear on the sign-in page
            wait.until(ExpectedConditions.visibilityOf(emailField));
            System.out.println("Navigated to sign-in page");
        } catch (Exception e) {
            System.out.println("Error during navigation to sign-in: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Fills the email field and clicks the Continue button
     * This method handles the first step of login:
     * 1. Enter email or mobile number
     * 2. Click Continue button
     *
     * @param email The email or mobile number to enter
     */
    public void fillEmailAndContinue(String email) {
        try {
            // Wait for email field to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(emailField));
            System.out.println("Email field is visible");

            // Clear any existing text and enter the email
            emailField.clear();
            emailField.sendKeys(email);
            System.out.println("Email entered: " + email);

            // Wait for Continue button to be clickable and click it
            wait.until(ExpectedConditions.elementToBeClickable(continueButton));
            continueButton.click();
            System.out.println("Continue button clicked");
        } catch (Exception e) {
            System.out.println("Error while filling email: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Fills the password field and clicks the Sign In button
     * This method handles the second step of login:
     * 1. Enter password
     * 2. Click Sign In button
     *
     * @param password The password to enter
     * @return HomePage object for method chaining
     */
    public HomePage fillPasswordAndSignIn(String password) {
        try {
            // Wait for password field to appear after clicking Continue
            wait.until(ExpectedConditions.visibilityOf(passwordField));
            System.out.println("Password field is visible");

            // Enter the password
            passwordField.clear();
            passwordField.sendKeys(password);
            System.out.println("Password entered");

            // Wait for Sign In button to be clickable and click it
            wait.until(ExpectedConditions.elementToBeClickable(signInButton));
            signInButton.click();
            System.out.println("Sign In button clicked");

            // Return HomePage object for method chaining
            return new HomePage(driver);
        } catch (Exception e) {
            System.out.println("Error while filling password: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Complete login flow in one method
     * @param email The email or mobile number
     * @param password The password
     * @return HomePage object
     */
    public HomePage login(String email, String password) {
        navigateToSignIn();
        fillEmailAndContinue(email);
        return fillPasswordAndSignIn(password);
    }
}