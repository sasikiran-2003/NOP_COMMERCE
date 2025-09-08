package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class CartPage extends BasePage {
    
    @FindBy(className = "product-name")
    private List<WebElement> productNames;
    
    @FindBy(className = "qty-input")
    private List<WebElement> quantityInputs;
    
    @FindBy(className = "product-subtotal")
    private List<WebElement> productSubtotals;
    
    @FindBy(name = "removefromcart")
    private List<WebElement> removeCheckboxes;
    
    @FindBy(id = "checkout")
    private WebElement checkoutButton;
    
    @FindBy(id = "termsofservice")
    private WebElement termsOfServiceCheckbox;
    
    @FindBy(className = "no-data")
    private WebElement emptyCartMessage;
    
    @FindBy(className = "cart-total")
    private WebElement cartTotal;
    
    @FindBy(css = ".input-group-btn .spin-up")
    private List<WebElement> spinUpButtons;
    
    @FindBy(css = ".input-group-btn .spin-down")
    private List<WebElement> spinDownButtons;
    
    @FindBy(className = "attribute")
    private List<WebElement> productAttributes;
    
    @FindBy(css = ".ajax-loading-block-window")
    private WebElement loadingOverlay;

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public String getProductName(int index) {
        waitForElementToBeVisible(productNames.get(index));
        return productNames.get(index).getText();
    }
    
    public String getProductAttributes(int index) {
        waitForElementToBeVisible(productAttributes.get(index));
        return productAttributes.get(index).getText();
    }
    
    public void updateQuantityUsingArrows(int index, int targetQuantity) {
        // Wait for any loading overlay to disappear
        waitForLoadingOverlayToDisappear();
        
        // Get current quantity
        int currentQuantity = Integer.parseInt(quantityInputs.get(index).getAttribute("value"));
        
        // Determine whether to click up or down arrows
        if (targetQuantity > currentQuantity) {
            int clicks = targetQuantity - currentQuantity;
            for (int i = 0; i < clicks; i++) {
                clickElement(spinUpButtons.get(index));
                // Wait for AJAX update
                waitForLoadingOverlayToDisappear();
            }
        } else if (targetQuantity < currentQuantity) {
            int clicks = currentQuantity - targetQuantity;
            for (int i = 0; i < clicks; i++) {
                clickElement(spinDownButtons.get(index));
                // Wait for AJAX update
                waitForLoadingOverlayToDisappear();
            }
        }
        
        // Wait for update to complete
        waitForCartToUpdate();
    }
    
    public void updateQuantityByInput(int index, String quantity) {
        // Wait for any loading overlay to disappear
        waitForLoadingOverlayToDisappear();
        
        // Clear the field and enter new value
        WebElement quantityInput = quantityInputs.get(index);
        quantityInput.clear();
        quantityInput.sendKeys(quantity);
        
        // Trigger update by sending TAB key (moves focus away from the field)
        quantityInput.sendKeys(Keys.TAB);
        
        // Wait for update to complete
        waitForCartToUpdate();
    }
    
    public String getProductSubtotal(int index) {
        waitForElementToBeVisible(productSubtotals.get(index));
        return productSubtotals.get(index).getText();
    }
    
    public void removeProduct(int index) {
        // Wait for any loading overlay to disappear
        waitForLoadingOverlayToDisappear();
        
        clickElement(removeCheckboxes.get(index));
        waitForCartToUpdate();
    }
    
    public void clickCheckout() {
        clickElement(termsOfServiceCheckbox);
        clickElement(checkoutButton);
    }
    
    public String getEmptyCartMessage() {
        waitForElementToBeVisible(emptyCartMessage);
        return emptyCartMessage.getText();
    }
    
    public String getCartTotal() {
        waitForElementToBeVisible(cartTotal);
        return cartTotal.getText();
    }
    
    public int getCartItemsCount() {
        return productNames.size();
    }
    
    public String getCurrentQuantity(int index) {
        return quantityInputs.get(index).getAttribute("value");
    }
    
    public boolean verifyProductConfiguration(int index, String expectedConfiguration) {
        String actualConfiguration = getProductAttributes(index);
        return actualConfiguration.contains(expectedConfiguration);
    }
    
    public void waitForCartToUpdate() {
        // Wait for the loading overlay to appear and then disappear
        waitForLoadingOverlayToDisappear();
        
        // Additional wait for stability
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void waitForLoadingOverlayToDisappear() {
        try {
            // Wait for loading overlay to appear (if it's going to appear)
            wait.until(ExpectedConditions.visibilityOf(loadingOverlay));
            // Then wait for it to disappear
            wait.until(ExpectedConditions.invisibilityOf(loadingOverlay));
        } catch (Exception e) {
            // If loading overlay doesn't appear, continue
        }
    }
    
    // Getter methods for elements that need to be accessed in tests
    public WebElement getUpdateCartButton() {
        return null; // Not used in current implementation
    }
    
    public WebElement getEmptyCartMessageElement() {
        return emptyCartMessage;
    }
}