package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {
    
    @FindBy(id = "Email")
    private WebElement emailInput;
    
    @FindBy(id = "Password")
    private WebElement passwordInput;
    
    @FindBy(css = ".button-1.login-button")
    private WebElement loginButton;
    
    @FindBy(css = ".message-error")
    private WebElement errorMessage;
    
    @FindBy(className = "ico-logout")
    private WebElement logoutLink;
    
    @FindBy(className = "ico-account")
    private WebElement myAccountLink;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public void enterEmail(String email) {
        sendKeysToElement(emailInput, email);
    }
    
    public void enterPassword(String password) {
        sendKeysToElement(passwordInput, password);
    }
    
    public void clickLoginButton() {
        clickElement(loginButton);
    }
    
    public String getErrorMessage() {
        return getElementText(errorMessage);
    }
    
    public boolean isLogoutDisplayed() {
        try {
            return logoutLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isMyAccountDisplayed() {
        try {
            return myAccountLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public WebElement getLogoutLink() {
        return logoutLink;
    }

}

