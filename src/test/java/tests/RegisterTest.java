package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import pages.RegisterPage;
import utils.PropertyReader;

public class RegisterTest extends BaseTest {
    
    @Test(priority = 1, description = "Verify user can register with valid credentials")
    public void testUserRegistrationWithValidCredentials() {
        test = extent.createTest("User Registration with Valid Credentials");
        extentTest.set(test);
        
        RegisterPage registerPage = new RegisterPage(getDriver());
        
        // Navigate to register page (URL might need adjustment based on actual site)
        getDriver().get(PropertyReader.getProperty("url") + "/register");
        
        // Fill registration form
        registerPage.selectGender("Male");
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("johndoe12355@example.com");
        registerPage.enterCompanyName("ABC Company");
        registerPage.enterPassword("password123");
        registerPage.enterConfirmPassword("password123");
        registerPage.clickRegisterButton();
        
        // Verify registration success
        Assert.assertTrue(registerPage.isLogoutDisplayed(), "Registration was not successful");
        Assert.assertEquals(registerPage.getRegistrationResult(), "Your registration completed");
    }
    
    @Test(priority = 2, description = "Verify registration fails with existing email")
    public void testUserRegistrationWithExistingEmail() {
        test = extent.createTest("User Registration with Existing Email");
        extentTest.set(test);
        
        RegisterPage registerPage = new RegisterPage(getDriver());
        
        // Navigate to register page
        getDriver().get(PropertyReader.getProperty("url") + "/register");
        
        // Fill registration form with existing email
        registerPage.selectGender("Female");
        registerPage.enterFirstName("Jane");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("johndoe12355@example.com"); // Assuming this email already exists
        registerPage.enterCompanyName("XYZ Company");
        registerPage.enterPassword("password123");
        registerPage.enterConfirmPassword("password123");
        registerPage.clickRegisterButton();
        
        // Verify error message (adjust based on actual error message)
        Assert.assertTrue(getDriver().getPageSource().contains("The specified email already exists"));
    }
    
    @Test(priority = 3, description = "Verify registration fails with password confirmation mismatch")
    public void testUserRegistrationWithPasswordMismatch() {
        test = extent.createTest("User Registration with Password Mismatch");
        extentTest.set(test);
        
        RegisterPage registerPage = new RegisterPage(getDriver());
        
        // Navigate to register page
        getDriver().get(PropertyReader.getProperty("url") + "/register");
        
        // Fill registration form with mismatched passwords
        registerPage.selectGender("Male");
        registerPage.enterFirstName("Bob");
        registerPage.enterLastName("Smith");
        registerPage.enterEmail("bobsmith" + System.currentTimeMillis() + "@example.com");
        registerPage.enterCompanyName("Test Company");
        registerPage.enterPassword("password123");
        registerPage.enterConfirmPassword("differentpassword");
        registerPage.clickRegisterButton();
        
        // Verify error message (adjust based on actual error message)
        Assert.assertTrue(getDriver().getPageSource().contains("The password and confirmation password do not match"));
    }
}

