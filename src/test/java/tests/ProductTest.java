package tests;

import pages.ProductPage;
import tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Arrays;

public class ProductTest extends BaseTest {
    
    @Test(priority = 1, description = "Verify user can view product details")
    public void testViewProductDetails() {
        test = extent.createTest("View Product Details");
        extentTest.set(test);
        
        ProductPage productPage = new ProductPage(getDriver());
        
        // Navigate to a specific product
        productPage.navigateToProduct("build-your-own-computer");
        
        // Verify product details page is displayed
        Assert.assertTrue(getDriver().getTitle().contains("Build your own computer"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("build-your-own-computer"));
    }
    
    @Test(priority = 2, description = "Verify user can configure and add computer to cart")
    public void testAddConfigurableProductToCart() {
        test = extent.createTest("Add Configurable Product to Cart");
        extentTest.set(test);
        
        ProductPage productPage = new ProductPage(getDriver());
        
        // Navigate directly to the product detail page
        getDriver().get("https://demo.nopcommerce.com/build-your-own-computer");
        
        // Configure and add the computer to cart
        productPage.configureAndAddComputerToCart(
            "8GB [+$60.00]", 
            "400 GB", 
            "Premium", 
            Arrays.asList("Microsoft Office", "Total Commander")
        );
        
        // Wait for and verify notification
        productPage.waitForNotification();
        Assert.assertTrue(productPage.getNotificationMessage().contains("The product has been added to your shopping cart"),
            "Product was not successfully added to cart.");
        
        // Close notification
        productPage.closeNotification();
    }
    
    @Test(priority = 3, description = "Verify user can add simple product to cart from category page")
    public void testAddSimpleProductToCartFromCategory() {
        test = extent.createTest("Add Simple Product to Cart from Category Page");
        extentTest.set(test);
        
        ProductPage productPage = new ProductPage(getDriver());
        
        // Navigate to a product category with simple products
        productPage.navigateToProductCategory("books");
        
        // Add a simple product to cart (no configuration needed)
        productPage.addProductToCart("First Prize Pies");
        
        // Verify product was added to cart
        productPage.waitForNotification();
        Assert.assertTrue(productPage.getNotificationMessage().contains("The product has been added to your shopping cart"));
        
        // Close notification
        productPage.closeNotification();
    }
    
   
}