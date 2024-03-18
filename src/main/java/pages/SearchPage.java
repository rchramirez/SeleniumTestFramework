package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static utils.Constants.*;

@Component
public class SearchPage {

    private static final Logger logger = LoggerFactory.getLogger(SearchPage.class);

    @Autowired
    public BasePage basePage;

    private By breadcrumbText = By.xpath(".//span[@class='next-breadcrumb-text activated']");
    private By couponDismiss = By.xpath(".//a[@class='next-dialog-close']");
    private By galleryListContainer = By.xpath(".//div[@class='gallery-wrap product-list']");
    private By goToPageBtn = By.xpath(".//span[@class='jump-btn']");
    private By listItems = By.xpath(".//li[contains(@class, 'list-item')]");
    private By mainRelatedCategory = By.xpath(".//div[@class='refine-block category']");
    private By maxPrice = By.xpath(".//input[@placeholder='max']");
    private By minPrice = By.xpath(".//input[@placeholder='min']");
    private By pageNumberInput = By.xpath(".//span[@class='next-input next-large']");
    private By pagination = By.xpath(".//div[@class='product-pagination-wrap']");
    private By priceBox = By.xpath(".//span[@class='next-input next-small min-price']");
    private By productPrice = By.xpath(".//ul//div[@class='item-price-wrap']");
    private By subCategory = By.xpath(".//div[@class='content']//ul[@class='child-menu']//a[@target='_self']");
    private By submitPriceFilter = By.xpath(".//a[@class='ui-button narrow-go']");
    private By totalPage = By.xpath(".//span[@class='total-page']");

    @Step("Getting main related category")
    public String getMainRelatedCategoryTtile() {
        return basePage.getElementText(mainRelatedCategory);
    }

    @Step("Getting subcategories for the searched item")
    public List<String> getSubCategoriesList() {
        List<String> subCategoriesList = new ArrayList<>();
        basePage.waitVisibilityOFElementLocated(mainRelatedCategory);
        if (basePage.isElementPresent(subCategory)) {
            List<WebElement> webElements = basePage.findElements(subCategory);
            if (webElements.size() > 0) {
                for (WebElement element : webElements) {
                    subCategoriesList.add(element.getText());
                }
            }
        }
        return subCategoriesList;
    }

    @Step("Getting subcategories for the searched item")
    public List<String> getProductsPriceOnCurrentPage() {
        List<String> productsPrice = new ArrayList<>();
        //basePage.waitVisibilityOFElementLocated(sideBar);
        scrollToPagination();
        List<WebElement> webElements = basePage.findElements(productPrice);
        if (webElements.size() > 0) {
            for (WebElement element : webElements) {
                productsPrice.add(element.getText());
            }
        }
        return productsPrice;
    }


    @Step("Selecting subcategory by visible text: {0}")
    public void selectSubCategory(String text) {
        if (!text.isEmpty()) {
            logger.info("Clicking on text link" + text);
            basePage.clickElement(By.linkText(text));
            closeSearchCoupon();
        } else {
            logger.info("There is no subcategory to be selected");
        }
    }

    @Step("Setting minimum price value filter: {0}")
    public void setMinPriceFilter(String value) {
        basePage.setText(minPrice, value);
    }

    @Step("Setting maximum price value filter: {0}")
    public void setMaxPriceFilter(String value) {
        basePage.setText(maxPrice, value);
    }

    @Step("Submitting price interval with minimum price: {0} and maximum price: {1}")
    public void setPriceInterval(String minValue, String maxValue) {
        basePage.clickElement(priceBox);
        setMinPriceFilter(minValue);
        setMaxPriceFilter(maxValue);
        basePage.clickElement(submitPriceFilter);
    }

    @Step("Getting searched item text from breadcrumb")
    public String getSearchedTextFromBreadCrumb() {
        String breadcrumb = basePage.getElementText(breadcrumbText);
        int index = breadcrumb.indexOf("(");
        String text = breadcrumb.substring(0, index).trim();
        return text.replaceAll("[^A-Za-z]", "");
    }

    @Step("Getting searched results number")
    public int getSearchResultsNumberFromBreadCrumb() {
        String breadCrumb = basePage.getElementText(breadcrumbText);
        String searchResultsNumber = breadCrumb.replaceAll("[^\\d.]", "").trim();
        return Integer.parseInt(searchResultsNumber);
    }

    @Step("Closing coupon")
    public void closeSearchCoupon() {
        try {
            basePage.waitVisibilityOFElementLocated(couponDismiss);
            if (basePage.isElementPresent(couponDismiss)) {
                basePage.clickElement(couponDismiss);
            }
        }catch (TimeoutException t){
            System.out.println("Coupon not visible");
        }

    }

    @Step("Getting products number from current page")
    public int getProductsNumberFromCurrentPage() {
        List<WebElement> elements = basePage.driver.getDriver().findElements(listItems);
        return elements.size();
    }

    @Step("Scrolling to element")
    public void scrollToPagination() {
        JavascriptExecutor js = (JavascriptExecutor) basePage.driver.getDriver();
        while (!basePage.isElementPresent(pagination)) {
            if (basePage.isElementPresent(galleryListContainer)) {
                js.executeScript("window.scrollBy(0,2000)");
            } else {
                js.executeScript("window.scrollBy(0,4000)");
            }
        }
    }

    @Step()
    public void scrollToBottom(){
        JavascriptExecutor js = (JavascriptExecutor) basePage.driver.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    @Step("Navigating to search results page number: {0}")
    public void navigateToPageNumber(String pageNumber) {
        basePage.waitVisibilityOFElementLocated(pagination);
        basePage.setText(pageNumberInput, pageNumber);
        basePage.clickElement(goToPageBtn);
    }

    @Step("Getting  total number of search results pages")
    public int getTotalPagesNumber() {
        String text = basePage.getElementText(totalPage);
        String numberAsString = text.replaceAll("[^\\d.]", "").trim();
        return Integer.parseInt(numberAsString);
    }
}
