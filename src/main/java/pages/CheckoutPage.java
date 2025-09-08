package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class CheckoutPage extends BasePage {
    
    // Billing address elements
    @FindBy(id = "BillingNewAddress_FirstName")
    private WebElement firstNameInput;
    
    @FindBy(id = "BillingNewAddress_LastName")
    private WebElement lastNameInput;
    
    @FindBy(id = "BillingNewAddress_Email")
    private WebElement emailInput;
    
    @FindBy(id = "BillingNewAddress_CountryId")
    private WebElement countryDropdown;
    
    @FindBy(id = "BillingNewAddress_StateProvinceId")
    private WebElement stateDropdown;
    
    @FindBy(id = "BillingNewAddress_City")
    private WebElement cityInput;
    
    @FindBy(id = "BillingNewAddress_Address1")
    private WebElement address1Input;
    
    @FindBy(id = "BillingNewAddress_ZipPostalCode")
    private WebElement zipCodeInput;
    
    @FindBy(id = "BillingNewAddress_PhoneNumber")
    private WebElement phoneNumberInput;
    
    @FindBy(css = ".button-1.new-address-next-step-button")
    private WebElement billingContinueButton;
    
    // Shipping method elements
    @FindBy(id = "shippingoption_1")
    private WebElement shippingMethodNextDayAir;
    
    @FindBy(id = "shippingoption_2")
    private WebElement shippingMethod2ndDayAir;
    
    @FindBy(id = "shippingoption_0")
    private WebElement shippingMethodGround;
    
    @FindBy(css = ".button-1.shipping-method-next-step-button")
    private WebElement shippingMethodContinueButton;
    
    // Payment method elements
    @FindBy(id = "paymentmethod_0")
    private WebElement paymentMethodCashOnDelivery;
    
    @FindBy(id = "paymentmethod_1")
    private WebElement paymentMethodCheck;
    
    @FindBy(id = "paymentmethod_2")
    private WebElement paymentMethodCreditCard;
    
    @FindBy(id = "paymentmethod_3")
    private WebElement paymentMethodPurchaseOrder;
    
    @FindBy(css = ".button-1.payment-method-next-step-button")
    private WebElement paymentMethodContinueButton;
    
    // Payment information elements
    @FindBy(css = ".button-1.payment-info-next-step-button")
    private WebElement paymentInfoContinueButton;
    
    // Confirm order elements
    @FindBy(css = ".button-1.confirm-order-next-step-button")
    private WebElement confirmOrderButton;
    
    @FindBy(className = "title")
    private WebElement orderConfirmationTitle;
    
    @FindBy(className = "order-number")
    private WebElement orderNumber;
    
    @FindBy(css = ".order-completed-continue-button")
    private WebElement orderCompletedContinueButton;
    
    // Order summary elements
    @FindBy(className = "product-name")
    private List<WebElement> checkoutProductNames;
    
    @FindBy(css = ".cart-options .value")
    private List<WebElement> checkoutProductAttributes;
    
    @FindBy(className = "product-quantity")
    private List<WebElement> checkoutProductQuantities;
    
    @FindBy(className = "product-unit-price")
    private List<WebElement> checkoutProductUnitPrices;
    
    @FindBy(className = "product-subtotal")
    private List<WebElement> checkoutProductSubtotals;
    
    @FindBy(css = ".cart-total-right")
    private List<WebElement> checkoutOrderTotals;
    
    @FindBy(css = ".section.shipping-address")
    private WebElement shippingAddressSection;
    
    @FindBy(css = ".section.shipping-method")
    private WebElement shippingMethodSection;
    
    @FindBy(css = ".section.payment-method")
    private WebElement paymentMethodSection;
    
    @FindBy(css = ".section.payment-info")
    private WebElement paymentInfoSection;
    
    @FindBy(css = ".section.order-summary")
    private WebElement orderSummarySection;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public void enterBillingAddress(String firstName, String lastName, String email, 
                                  String country, String state, String city, String address, 
                                  String zipCode, String phone) {
        waitForElementToBeVisible(firstNameInput);
        
        sendKeysToElement(firstNameInput, firstName);
        sendKeysToElement(lastNameInput, lastName);
        sendKeysToElement(emailInput, email);
        
        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByVisibleText(country);
        
        if (state != null && !state.isEmpty()) {
            Select stateSelect = new Select(stateDropdown);
            stateSelect.selectByVisibleText(state);
        }
        
        sendKeysToElement(cityInput, city);
        sendKeysToElement(address1Input, address);
        sendKeysToElement(zipCodeInput, zipCode);
        sendKeysToElement(phoneNumberInput, phone);
    }
    
    public void clickBillingContinue() {
        clickElement(billingContinueButton);
        waitForElementToBeVisible(shippingAddressSection);
    }
    
    public void selectShippingMethod(String method) {
        switch (method.toLowerCase()) {
            case "ground":
                clickElement(shippingMethodGround);
                break;
            case "next day air":
                clickElement(shippingMethodNextDayAir);
                break;
            case "2nd day air":
                clickElement(shippingMethod2ndDayAir);
                break;
            default:
                clickElement(shippingMethodGround);
        }
        clickElement(shippingMethodContinueButton);
        waitForElementToBeVisible(paymentMethodSection);
    }
    
    public void selectPaymentMethod(String method) {
        switch (method.toLowerCase()) {
            case "cash on delivery":
                clickElement(paymentMethodCashOnDelivery);
                break;
            case "check":
                clickElement(paymentMethodCheck);
                break;
            case "credit card":
                clickElement(paymentMethodCreditCard);
                break;
            case "purchase order":
                clickElement(paymentMethodPurchaseOrder);
                break;
            default:
                clickElement(paymentMethodCashOnDelivery);
        }
        clickElement(paymentMethodContinueButton);
        waitForElementToBeVisible(paymentInfoSection);
    }
    
    public void clickPaymentInfoContinue() {
        clickElement(paymentInfoContinueButton);
        waitForElementToBeVisible(orderSummarySection);
    }
    
    public void clickConfirmOrder() {
        clickElement(confirmOrderButton);
        waitForElementToBeVisible(orderConfirmationTitle);
    }
    
    public String getOrderConfirmationTitle() {
        return getElementText(orderConfirmationTitle);
    }
    
    public String getOrderNumber() {
        return getElementText(orderNumber);
    }
    
    public void clickOrderCompletedContinue() {
        clickElement(orderCompletedContinueButton);
    }
    
    public String getCheckoutProductName(int index) {
        waitForElementToBeVisible(checkoutProductNames.get(index));
        return checkoutProductNames.get(index).getText();
    }
    
    public String getCheckoutProductAttributes(int index) {
        waitForElementToBeVisible(checkoutProductAttributes.get(index));
        return checkoutProductAttributes.get(index).getText();
    }
    
    public String getCheckoutProductQuantity(int index) {
        waitForElementToBeVisible(checkoutProductQuantities.get(index));
        return checkoutProductQuantities.get(index).getText();
    }
    
    public String getCheckoutProductUnitPrice(int index) {
        waitForElementToBeVisible(checkoutProductUnitPrices.get(index));
        return checkoutProductUnitPrices.get(index).getText();
    }
    
    public String getCheckoutProductSubtotal(int index) {
        waitForElementToBeVisible(checkoutProductSubtotals.get(index));
        return checkoutProductSubtotals.get(index).getText();
    }
    
    public String getOrderTotal() {
        waitForElementToBeVisible(checkoutOrderTotals.get(checkoutOrderTotals.size() - 1));
        return checkoutOrderTotals.get(checkoutOrderTotals.size() - 1).getText();
    }
    
    public boolean verifyCheckoutProductConfiguration(int index, String expectedConfiguration) {
        String actualConfiguration = getCheckoutProductAttributes(index);
        return actualConfiguration.contains(expectedConfiguration);
    }
    
    public boolean isBillingAddressFormDisplayed() {
        try {
            return firstNameInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isOrderConfirmationDisplayed() {
        try {
            return orderConfirmationTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}