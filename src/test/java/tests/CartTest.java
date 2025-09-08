package tests;

import pages.CartPage;
import pages.ProductPage;
import tests.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Arrays;

public class CartTest extends BaseTest {
    
    @Test(priority = 1, description = "Verify user can update product quantity using arrows")
    public void testUpdateProductQuantityUsingArrows() {
        test = extent.createTest("Update Product Quantity Using Arrows");
        extentTest.set(test);
        
        ProductPage productPage = new ProductPage(getDriver());
        CartPage cartPage = new CartPage(getDriver());
        
        // Add a simple product to cart (no configuration needed)
        getDriver().get("https://demo.nopcommerce.com/books");
        productPage.addProductToCart("First Prize Pies");
        
        // Wait for notification and close it
        productPage.waitForNotification();
        productPage.closeNotification();
        
        // Navigate to shopping cart
        productPage.clickShoppingCart();
        
        // Wait for cart page to load completely
        wait.until(ExpectedConditions.titleContains("Shopping cart"));
        
        // Get initial quantity
        String initialQuantity = cartPage.getCurrentQuantity(0);
        Assert.assertEquals(initialQuantity, "1", "Initial quantity should be 1");
        
        // Update quantity using arrows
        cartPage.updateQuantityUsingArrows(0, 3);
        
        // Wait for the quantity to update
        wait.until(driver -> {
            String currentQty = cartPage.getCurrentQuantity(0);
            return currentQty.equals("3");
        });
        
        // Verify quantity was updated
        String currentQuantity = cartPage.getCurrentQuantity(0);
        Assert.assertEquals(currentQuantity, "3", "Quantity should be updated to 3");
        
        // Verify subtotal was updated
        String subtotal = cartPage.getProductSubtotal(0);
        Assert.assertTrue(subtotal.contains("$"), "Subtotal should reflect updated quantity");
    }
    
    @Test(priority = 2, description = "Verify user can update product quantity by direct input")
    public void testUpdateProductQuantityByInput() {
        test = extent.createTest("Update Product Quantity By Direct Input");
        extentTest.set(test);
        
        ProductPage productPage = new ProductPage(getDriver());
        CartPage cartPage = new CartPage(getDriver());
        
        // Add a simple product to cart (no configuration needed)
        getDriver().get("https://demo.nopcommerce.com/books");
        productPage.addProductToCart("First Prize Pies");
        
        // Wait for notification and close it
        productPage.waitForNotification();
        productPage.closeNotification();
        
        // Navigate to shopping cart
        productPage.clickShoppingCart();
        
        // Wait for cart page to load completely
        wait.until(ExpectedConditions.titleContains("Shopping cart"));
        
        // Update quantity by direct input
        cartPage.updateQuantityByInput(0, "2");
        
        // Wait for the quantity to update
        wait.until(driver -> {
            String currentQty = cartPage.getCurrentQuantity(0);
            return currentQty.equals("2");
        });
        
        // Verify quantity was updated
        String currentQuantity = cartPage.getCurrentQuantity(0);
        Assert.assertEquals(currentQuantity, "2", "Quantity should be updated to 2");
        
        // Verify subtotal was updated
        String subtotal = cartPage.getProductSubtotal(0);
        Assert.assertTrue(subtotal.contains("$"), "Subtotal should reflect updated quantity");
    }
    
    @Test(priority = 3, description = "Verify user can remove product from cart")
    public void testRemoveProductFromCart() {
        test = extent.createTest("Remove Product from Cart");
        extentTest.set(test);
        
        ProductPage productPage = new ProductPage(getDriver());
        CartPage cartPage = new CartPage(getDriver());
        
        // Add a simple product to cart (no configuration needed)
        getDriver().get("https://demo.nopcommerce.com/books");
        productPage.addProductToCart("First Prize Pies");
        
        // Wait for notification and close it
        productPage.waitForNotification();
        productPage.closeNotification();
        
        // Navigate to shopping cart
        productPage.clickShoppingCart();
        
        // Wait for cart page to load completely
        wait.until(ExpectedConditions.titleContains("Shopping cart"));
        
        // Remove product
        cartPage.removeProduct(0);
        
        // Wait for cart to update
        wait.until(ExpectedConditions.visibilityOf(cartPage.getEmptyCartMessageElement()));
        
        // Verify cart is empty
        Assert.assertTrue(cartPage.getEmptyCartMessage().contains("Your Shopping Cart is empty!"),
                         "Cart should be empty after removing product");
    }
    
    @Test(priority = 4, description = "Verify configured product appears correctly in cart")
    public void testConfiguredProductInCart() {
        test = extent.createTest("Configured Product in Cart");
        extentTest.set(test);
        
        ProductPage productPage = new ProductPage(getDriver());
        CartPage cartPage = new CartPage(getDriver());
        
        // Navigate to and configure a computer
        getDriver().get("https://demo.nopcommerce.com/build-your-own-computer");
        
        // Configure the computer
        productPage.configureAndAddComputerToCart(
            "8GB [+$60.00]", 
            "400 GB", 
            "Premium", 
            Arrays.asList("Microsoft Office", "Total Commander")
        );
        
        // Wait for notification and close it
        productPage.waitForNotification();
        productPage.closeNotification();
        
        // Navigate to shopping cart
        productPage.clickShoppingCart();
        
        // Wait for cart page to load completely
        wait.until(ExpectedConditions.titleContains("Shopping cart"));
        
        // Verify product configuration in cart
        Assert.assertTrue(cartPage.verifyProductConfiguration(0, "8GB"),
                         "Cart should show the selected RAM configuration");
        Assert.assertTrue(cartPage.verifyProductConfiguration(0, "400 GB"),
                         "Cart should show the selected HDD configuration");
        Assert.assertTrue(cartPage.verifyProductConfiguration(0, "Premium"),
                         "Cart should show the selected OS configuration");
    }
    
    @Test(priority = 5, description = "Verify user can proceed to checkout from cart")
    public void testProceedToCheckoutFromCart() {
        test = extent.createTest("Proceed to Checkout from Cart");
        extentTest.set(test);
        
        ProductPage productPage = new ProductPage(getDriver());
        CartPage cartPage = new CartPage(getDriver());
        
        // Add a simple product to cart
        getDriver().get("https://demo.nopcommerce.com/books");
        productPage.addProductToCart("First Prize Pies");
        
        // Wait for notification and close it
        productPage.waitForNotification();
        productPage.closeNotification();
        
        // Navigate to shopping cart
        productPage.clickShoppingCart();
        
        // Wait for cart page to load completely
        wait.until(ExpectedConditions.titleContains("Shopping cart"));
        
        // Proceed to checkout
        cartPage.clickCheckout();
        
        // Wait for checkout page to load
        wait.until(ExpectedConditions.urlContains("checkout"));
        
        // Verify checkout page is displayed
        Assert.assertTrue(getDriver().getCurrentUrl().contains("checkout"),
                         "Should be redirected to checkout page");
    }
}