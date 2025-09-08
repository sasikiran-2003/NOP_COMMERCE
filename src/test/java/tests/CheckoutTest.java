package tests;

import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductPage;
import tests.BaseTest;
import utils.PropertyReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Arrays;

public class CheckoutTest extends BaseTest {
    
    @Test(priority = 1, description = "Verify user can complete checkout process with configured product")
    public void testCompleteCheckoutProcessWithConfiguredProduct() {
        test = extent.createTest("Complete Checkout Process with Configured Product");
        extentTest.set(test);
        
        // First login
        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/login");
        loginPage.enterEmail(PropertyReader.getProperty("valid.email"));
        loginPage.enterPassword(PropertyReader.getProperty("valid.password"));
        loginPage.clickLoginButton();
        
        // Configure and add a computer to cart
        ProductPage productPage = new ProductPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/build-your-own-computer");
        
        // Configure the computer
        productPage.configureAndAddComputerToCart(
            "8GB [+$60.00]", 
            "400 GB", 
            "Premium", 
            Arrays.asList("Microsoft Office", "Total Commander")
        );
        
        productPage.closeNotification();
        
        // Navigate to shopping cart and proceed to checkout
        productPage.clickShoppingCart();
        CartPage cartPage = new CartPage(getDriver());
        cartPage.clickCheckout();
        
        // Complete checkout process
        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        
        // Verify billing address form is displayed
        Assert.assertTrue(checkoutPage.isBillingAddressFormDisplayed(), 
                         "Billing address form should be displayed");
        
        // Fill billing address
        checkoutPage.enterBillingAddress("John", "Doe", "johndoe@example.com", 
                                       "United States", "California", "Los Angeles", 
                                       "123 Main St", "90001", "1234567890");
        checkoutPage.clickBillingContinue();
        
        // Select shipping method
        checkoutPage.selectShippingMethod("Next Day Air");
        
        // Select payment method
        checkoutPage.selectPaymentMethod("Cash On Delivery");
        
        // Continue to payment info
        checkoutPage.clickPaymentInfoContinue();
        
        // Confirm order
        checkoutPage.clickConfirmOrder();
        
        // Verify order confirmation
        Assert.assertTrue(checkoutPage.isOrderConfirmationDisplayed(), 
                         "Order confirmation should be displayed");
        Assert.assertTrue(checkoutPage.getOrderConfirmationTitle().contains("Thank you"),
                         "Order confirmation title should contain 'Thank you'");
        Assert.assertTrue(checkoutPage.getOrderNumber().contains("Order number:"),
                         "Order number should be displayed");
    }
    
    @Test(priority = 2, description = "Verify configured product details in checkout")
    public void testConfiguredProductDetailsInCheckout() {
        test = extent.createTest("Configured Product Details in Checkout");
        extentTest.set(test);
        
        // First login
        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/login");
        loginPage.enterEmail(PropertyReader.getProperty("valid.email"));
        loginPage.enterPassword(PropertyReader.getProperty("valid.password"));
        loginPage.clickLoginButton();
        
        // Configure and add a computer to cart
        ProductPage productPage = new ProductPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/build-your-own-computer");
        
        // Configure the computer
        productPage.configureAndAddComputerToCart(
            "8GB [+$60.00]", 
            "400 GB", 
            "Premium", 
            Arrays.asList("Microsoft Office", "Total Commander")
        );
        
        productPage.closeNotification();
        
        // Navigate to shopping cart and proceed to checkout
        productPage.clickShoppingCart();
        CartPage cartPage = new CartPage(getDriver());
        cartPage.clickCheckout();
        
        // Verify product configuration in checkout
        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        
        // Fill billing address and continue to shipping
        checkoutPage.enterBillingAddress("John", "Doe", "johndoe@example.com", 
                                       "United States", "California", "Los Angeles", 
                                       "123 Main St", "90001", "1234567890");
        checkoutPage.clickBillingContinue();
        
        // Select shipping method and continue to payment
        checkoutPage.selectShippingMethod("Next Day Air");
        
        // Select payment method and continue to payment info
        checkoutPage.selectPaymentMethod("Cash On Delivery");
        
        // Continue to confirm order page
        checkoutPage.clickPaymentInfoContinue();
        
        // Verify product configuration
        Assert.assertTrue(checkoutPage.verifyCheckoutProductConfiguration(0, "8GB"),
                         "Checkout should show the selected RAM configuration");
        Assert.assertTrue(checkoutPage.verifyCheckoutProductConfiguration(0, "400 GB"),
                         "Checkout should show the selected HDD configuration");
        Assert.assertTrue(checkoutPage.verifyCheckoutProductConfiguration(0, "Premium"),
                         "Checkout should show the selected OS configuration");
        Assert.assertTrue(checkoutPage.verifyCheckoutProductConfiguration(0, "Microsoft Office"),
                         "Checkout should show the selected software configuration");
        Assert.assertTrue(checkoutPage.verifyCheckoutProductConfiguration(0, "Total Commander"),
                         "Checkout should show the selected software configuration");
    }
    
    @Test(priority = 3, description = "Verify checkout requires terms of service acceptance")
    public void testCheckoutTermsOfServiceValidation() {
        test = extent.createTest("Checkout Terms of Service Validation");
        extentTest.set(test);
        
        // First login
        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/login");
        loginPage.enterEmail(PropertyReader.getProperty("valid.email"));
        loginPage.enterPassword(PropertyReader.getProperty("valid.password"));
        loginPage.clickLoginButton();
        
        // Add a simple product to cart
        ProductPage productPage = new ProductPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/books");
        productPage.addProductToCart("First Prize Pies");
        productPage.closeNotification();
        
        // Navigate to shopping cart and try to checkout without accepting terms
        productPage.clickShoppingCart();
        CartPage cartPage = new CartPage(getDriver());
        
        // Try to checkout without accepting terms
        cartPage.clickCheckout();
        
        // Verify error message
        Assert.assertTrue(getDriver().getPageSource().contains("Please accept the terms of service"),
                         "Should show terms of service validation error");
    }
    
    @Test(priority = 4, description = "Verify checkout with different payment methods")
    public void testCheckoutWithDifferentPaymentMethods() {
        test = extent.createTest("Checkout with Different Payment Methods");
        extentTest.set(test);
        
        // First login
        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/login");
        loginPage.enterEmail(PropertyReader.getProperty("valid.email"));
        loginPage.enterPassword(PropertyReader.getProperty("valid.password"));
        loginPage.clickLoginButton();
        
        // Add a simple product to cart
        ProductPage productPage = new ProductPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/books");
        productPage.addProductToCart("First Prize Pies");
        productPage.closeNotification();
        
        // Navigate to shopping cart and proceed to checkout
        productPage.clickShoppingCart();
        CartPage cartPage = new CartPage(getDriver());
        cartPage.clickCheckout();
        
        // Complete checkout process with different payment methods
        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        
        // Fill billing address
        checkoutPage.enterBillingAddress("John", "Doe", "johndoe@example.com", 
                                       "United States", "California", "Los Angeles", 
                                       "123 Main St", "90001", "1234567890");
        checkoutPage.clickBillingContinue();
        
        // Select shipping method
        checkoutPage.selectShippingMethod("Ground");
        
        // Test Credit Card payment method
        checkoutPage.selectPaymentMethod("Credit Card");
        checkoutPage.clickPaymentInfoContinue();
        
        // Verify payment information form is displayed for credit card
        Assert.assertTrue(getDriver().getCurrentUrl().contains("checkout"),
                         "Should be on checkout page for payment information");
        
        // Go back and test Check payment method
        getDriver().navigate().back();
        checkoutPage.selectPaymentMethod("Check");
        checkoutPage.clickPaymentInfoContinue();
        
        // Verify payment information form is displayed for check
        Assert.assertTrue(getDriver().getCurrentUrl().contains("checkout"),
                         "Should be on checkout page for payment information");
    }
    
    @Test(priority = 5, description = "Verify order total calculation in checkout")
    public void testOrderTotalCalculation() {
        test = extent.createTest("Order Total Calculation in Checkout");
        extentTest.set(test);
        
        // First login
        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/login");
        loginPage.enterEmail(PropertyReader.getProperty("valid.email"));
        loginPage.enterPassword(PropertyReader.getProperty("valid.password"));
        loginPage.clickLoginButton();
        
        // Add a simple product to cart
        ProductPage productPage = new ProductPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/books");
        productPage.addProductToCart("First Prize Pies");
        productPage.closeNotification();
        
        // Navigate to shopping cart and proceed to checkout
        productPage.clickShoppingCart();
        CartPage cartPage = new CartPage(getDriver());
        cartPage.clickCheckout();
        
        // Complete checkout process
        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        
        // Fill billing address
        checkoutPage.enterBillingAddress("John", "Doe", "johndoe@example.com", 
                                       "United States", "California", "Los Angeles", 
                                       "123 Main St", "90001", "1234567890");
        checkoutPage.clickBillingContinue();
        
        // Select shipping method
        checkoutPage.selectShippingMethod("Next Day Air");
        
        // Select payment method
        checkoutPage.selectPaymentMethod("Cash On Delivery");
        
        // Continue to payment info
        checkoutPage.clickPaymentInfoContinue();
        
        // Get order total
        String orderTotal = checkoutPage.getOrderTotal();
        
        // Verify order total is displayed and contains currency symbol
        Assert.assertTrue(orderTotal.contains("$"), "Order total should contain currency symbol");
        Assert.assertFalse(orderTotal.isEmpty(), "Order total should not be empty");
    }
}