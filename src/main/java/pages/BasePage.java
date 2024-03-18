package pages;

import component.AliExpressDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static utils.Constants.*;

@Component
public class BasePage {


    @Autowired
    AliExpressDriver driver;

    @Step("Clicking on element {0}")
    public void clickElement(By locator) {
        findElement(locator).click();
    }

    @Step("Finding element {0}")
    public WebElement findElement(By locator) {
        waitVisibilityOFElementLocated(locator);
        return driver.getDriver().findElement(locator);
    }

    @Step("Finding elements {0}")
    public List<WebElement> findElements(By locator) {
        waitVisibilityOFElementLocated(locator);
        return driver.getDriver().findElements(locator);
    }

    @Step("Getting all drop down values {0}")
    public List<String> getDropDownTextValues(By locator) {
        List<String> elements = new ArrayList<>();

        Select select = new Select(driver.getDriver().findElement(locator));
        List<WebElement> options = select.getOptions();

        for (WebElement opt : options) {
            elements.add(opt.getText());
        }
        return elements;
    }

    @Step("Getting element attribute value {0}")
    public String getElementAttribute(By locator, String attribute) {
        return findElement(locator).getAttribute(attribute);
    }

    @Step("Getting element text {0}")
    public String getElementText(By locator) {
        return findElement(locator).getText();
    }

    @Step("Getting selected drop down text value  {0}")
    public String getSelectedDropDownValue(By locator) {
        WebElement webElement = findElement(locator);
        Select select = new Select(webElement);
        return select.getFirstSelectedOption().getText();
    }

    @Step("Checking if element is present {0}")
    public boolean isElementEnabled(By locator) {
        if (findElement(locator).isEnabled()) {
            return true;
        }
        return false;
    }

    @Step("Checking if element is present {0}")
    public boolean isElementPresent(By locator) {
        try {
            List<WebElement> elements = driver.getDriver().findElements(locator);
            if (elements != null && elements.size() > 0)
                return true;
        } catch (NoSuchElementException n) {
            return false;
        }
        return false;
    }

    @Step("Checking if element is selected: {0}")
    public boolean isElementSelected(By locator) {
        return findElement(locator).isSelected();
    }

    @Step("Moving mouse over element {0}")
    public void moveMouseToElement(By locator) {
        Actions actions = new Actions(driver.getDriver());
        actions.moveToElement(driver.getDriver().findElement(locator)).perform();
    }

    @Step("Selecting drop down value by visible text {0}")
    public void selectDropDownValueByVisibleText(By locator, String text) {
        Select select = new Select(driver.getDriver().findElement(locator));
        select.selectByVisibleText(text);
    }

    @Step("Setting text for element {0}")
    public void setText(By locator, String text) {
        findElement(locator).sendKeys(text);
    }

    @Step("Waiting for frame and switching to it {0}")
    public void switchToDefaultFrame() {
        driver.getDriver().switchTo().defaultContent();
    }

    @Step("Waiting for frame and switching to it {0}")
    public void waitFrameToBeAvailableAndSwitch(By locator) {
        WebDriverWait wait = new WebDriverWait(driver.getDriver(), MEDIUM_TIMEOUT);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    @Step("Waiting for element {0}")
    public void waitVisibilityOFElementLocated(By locator) {
        WebDriverWait wait = new WebDriverWait(driver.getDriver(), MEDIUM_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
