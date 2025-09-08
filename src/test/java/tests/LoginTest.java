package tests;

import pages.LoginPage;
import tests.BaseTest;
import utils.PropertyReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    
    @Test(priority = 1, description = "Verify user can login with valid credentials")
    public void testUserLoginWithValidCredentials() {
        test = extent.createTest("User Login with Valid Credentials");
        extentTest.set(test);
        
        LoginPage loginPage = new LoginPage(getDriver());
        
        // Navigate to login page
        getDriver().get(PropertyReader.getProperty("url") + "/login");
        
        // Fill login form
        loginPage.enterEmail(PropertyReader.getProperty("valid.email"));
        loginPage.enterPassword(PropertyReader.getProperty("valid.password"));
        loginPage.clickLoginButton();
        
        // Verify login success
        Assert.assertTrue(loginPage.isLogoutDisplayed(), "Login was not successful");
        Assert.assertTrue(loginPage.isMyAccountDisplayed(), "My Account link is not displayed");
    }
    
    @Test(priority = 2, description = "Verify login fails with invalid credentials")
    public void testUserLoginWithInvalidCredentials() {
        test = extent.createTest("User Login with Invalid Credentials");
        extentTest.set(test);
        
        LoginPage loginPage = new LoginPage(getDriver());
        
        // Navigate to login page
        getDriver().get(PropertyReader.getProperty("url") + "/login");
        
        // Fill login form with invalid credentials
        loginPage.enterEmail("invalid@example.com");
        loginPage.enterPassword("wrongpassword");
        loginPage.clickLoginButton();
        
        // Verify error message
        Assert.assertTrue(loginPage.getErrorMessage().contains("Login was unsuccessful"));
    }
    
    @Test(priority = 3, description = "Verify user can logout successfully")
    public void testUserLogout() {
        test = extent.createTest("User Logout");
        extentTest.set(test);
        
        LoginPage loginPage = new LoginPage(getDriver());
        
        // First login
        getDriver().get(PropertyReader.getProperty("url") + "/login");
        loginPage.enterEmail(PropertyReader.getProperty("valid.email"));
        loginPage.enterPassword(PropertyReader.getProperty("valid.password"));
        loginPage.clickLoginButton();
        
        // Then logout
        loginPage.clickElement(loginPage.getLogoutLink());
        
        // Verify logout success
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://demo.nopcommerce.com/");

    }
}


