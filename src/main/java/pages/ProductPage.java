package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class ProductPage extends BasePage {
    
    @FindBy(className = "product-title")
    private List<WebElement> productTitles;
    
    @FindBy(css = ".button-2.product-box-add-to-cart-button")
    private List<WebElement> addToCartButtons;
    
    @FindBy(css = ".add-to-cart-button")
    private WebElement addToCartButton;
    
    @FindBy(className = "content")
    private WebElement notificationBar;
    
    @FindBy(className = "ico-cart")
    private WebElement shoppingCartLink;
    
    @FindBy(css = ".close")
    private WebElement closeNotificationButton;
    
    @FindBy(className = "price-value-1")
    private WebElement productPrice;
    
    @FindBy(id = "product_enteredQuantity_1")
    private WebElement quantityInput;
    
    @FindBy(id = "product_attribute_2")
    private WebElement ramDropdown;
    
    @FindBy(id = "product_attribute_3_6")
    private WebElement hdd320gbRadio;
    
    @FindBy(id = "product_attribute_3_7")
    private WebElement hdd400gbRadio;
    
    @FindBy(id = "product_attribute_4_8")
    private WebElement osHomeRadio;
    
    @FindBy(id = "product_attribute_4_9")
    private WebElement osPremiumRadio;
    
    @FindBy(id = "product_attribute_5_10")
    private WebElement softwareMicrosoft;
    
    @FindBy(id = "product_attribute_5_11")
    private WebElement softwareAcrobat;
    
    @FindBy(id = "product_attribute_5_12")
    private WebElement softwareTotal;

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public void navigateToProductCategory(String category) {
        String url = "https://demo.nopcommerce.com/" + category.toLowerCase();
        driver.get(url);
    }
    
    public void navigateToProduct(String productName) {
        String url = "https://demo.nopcommerce.com/" + productName.toLowerCase().replace(" ", "-");
        driver.get(url);
    }
    
    public void clickProductByName(String productName) {
        for (int i = 0; i < productTitles.size(); i++) {
            if (productTitles.get(i).getText().equalsIgnoreCase(productName)) {
                productTitles.get(i).click();
                break;
            }
        }
    }
    
    public void addProductToCart(String productName) {
        for (int i = 0; i < productTitles.size(); i++) {
            if (productTitles.get(i).getText().equalsIgnoreCase(productName)) {
                addToCartButtons.get(i).click();
                break;
            }
        }
    }
    
    public void configureAndAddComputerToCart(String ram, String hdd, String os, List<String> softwareOptions) {
        // Select RAM
        Select ramSelect = new Select(ramDropdown);
        ramSelect.selectByVisibleText(ram);
        
        // Select HDD
        if (hdd.equals("320 GB")) {
            clickElement(hdd320gbRadio);
        } else if (hdd.equals("400 GB")) {
            clickElement(hdd400gbRadio);
        }
        
        // Select OS
        if (os.equals("Home")) {
            clickElement(osHomeRadio);
        } else if (os.equals("Premium")) {
            clickElement(osPremiumRadio);
        }
        
        // Select software options
        if (softwareOptions.contains("Microsoft Office")) {
            clickElement(softwareMicrosoft);
        }
        if (softwareOptions.contains("Acrobat Reader")) {
            clickElement(softwareAcrobat);
        }
        if (softwareOptions.contains("Total Commander")) {
            clickElement(softwareTotal);
        }
        
        // Add to cart
        clickAddToCart();
    }
    
    public void clickAddToCart() {
        clickElement(addToCartButton);
    }
    
    public String getNotificationMessage() {
        return getElementText(notificationBar);
    }
    
    public void closeNotification() {
        clickElement(closeNotificationButton);
    }
    
    public void clickShoppingCart() {
        clickElement(shoppingCartLink);
    }
    
    public String getProductPrice() {
        return getElementText(productPrice);
    }
    
    public void setQuantity(String quantity) {
        sendKeysToElement(quantityInput, quantity);
    }
    
    public void waitForNotification() {
        waitForElementToBeVisible(notificationBar);
    }
}