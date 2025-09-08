package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage extends BasePage {

    @FindBy(id = "gender-male")
    private WebElement genderMaleRadio;

    @FindBy(id = "gender-female")
    private WebElement genderFemaleRadio;

    @FindBy(id = "FirstName")
    private WebElement firstNameInput;

    @FindBy(id = "LastName")
    private WebElement lastNameInput;

    @FindBy(id = "Email")
    private WebElement emailInput;

    @FindBy(id = "Company")
    private WebElement companyInput;

    @FindBy(id = "Password")
    private WebElement passwordInput;

    @FindBy(id = "ConfirmPassword")
    private WebElement confirmPasswordInput;

    @FindBy(id = "register-button")
    private WebElement registerButton;

    @FindBy(className = "result")
    private WebElement registrationResult;

    @FindBy(className = "ico-logout")
    private WebElement logoutLink;

    public RegisterPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void selectGender(String gender) {
        if ("male".equalsIgnoreCase(gender)) {
            clickElement(genderMaleRadio);
        } else if ("female".equalsIgnoreCase(gender)) {
            clickElement(genderFemaleRadio);
        }
    }

    public void enterFirstName(String firstName) {
        sendKeysToElement(firstNameInput, firstName);
    }

    public void enterLastName(String lastName) {
        sendKeysToElement(lastNameInput, lastName);
    }

    public void enterEmail(String email) {
        sendKeysToElement(emailInput, email);
    }

    public void enterCompanyName(String company) {
        sendKeysToElement(companyInput, company);
    }

    public void enterPassword(String password) {
        sendKeysToElement(passwordInput, password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        sendKeysToElement(confirmPasswordInput, confirmPassword);
    }

    public void clickRegisterButton() {
        clickElement(registerButton);
    }

    public String getRegistrationResult() {
        return getElementText(registrationResult);
    }

    public boolean isLogoutDisplayed() {
        try {
            return logoutLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }

    }
}
