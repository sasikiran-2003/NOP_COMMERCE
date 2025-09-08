package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    protected WebDriver getDriver() {
        return this.driver;
    }

    public void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void clickElement(WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
    }

    public void sendKeysToElement(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    public String getElementText(WebElement element) {
        waitForElementToBeVisible(element);
        return element.getText();
    }
}

