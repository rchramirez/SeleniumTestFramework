package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static utils.Constants.sideBar;

@Component
public class HomePage {

    @Autowired
    BasePage basePage;

    @Autowired
    LoginPage loginPage;

    private By categoriesDropDown = By.id("search-cate");
    private By categoriesSelector = By.id("search-dropdown-box");
    private By couponDismiss = By.xpath(".//a[@class='close-layer']");
    private By loginFrame = By.id("alibaba-login-box");
    private By mainUserAccountSignIn = By.xpath(".//a[@class='sign-btn']");
    private By searchBar = By.id("search-key");
    private By searchBtn = By.xpath(".//input[@class='search-button']");
    private By signIn = By.xpath(".//span[@class='register-btn']");
    private By userAccount = By.id("nav-user-account");
    private By usernameMessage = By.xpath(".//span[@data-role='username']");

    @Step("Closing coupon pop-up")
    public void closeCoupon() {
        try {
            basePage.waitVisibilityOFElementLocated(couponDismiss);
            if (basePage.isElementPresent(couponDismiss)) {
                basePage.clickElement(couponDismiss);
            }
        }catch (TimeoutException t){
            System.out.println("Pop-up not present");
        }
    }

    @Step("Enter search text: {0}")
    public void enterSearchText(String text) {
        basePage.setText(searchBar, text);
    }

    @Step("Getting categories values")
    public List<String> getCategoriesValues() {
        basePage.clickElement(categoriesDropDown);
        return basePage.getDropDownTextValues(categoriesSelector);
    }

    @Step("Get salutation message after sign in")
    public String getUserNameMessage() {
        basePage.waitVisibilityOFElementLocated(usernameMessage);
        return basePage.getElementText(usernameMessage);
    }

    @Step("Sign in")
    public void loginFromHomePage() {
        basePage.clickElement(signIn);
        if (!basePage.driver.isHeadlessSet()) {
            basePage.waitVisibilityOFElementLocated(sideBar);
        }
        basePage.waitFrameToBeAvailableAndSwitch(loginFrame);
        loginPage.signIn();
        basePage.switchToDefaultFrame();
        closeCoupon();
    }

    @Step("Moving over user account element and clicking Sign In")
    public void moveToUserAccountAndClickSignInBtn() {
        basePage.moveMouseToElement(userAccount);
        basePage.clickElement(mainUserAccountSignIn);
    }

    @Step("Selecting category: {0}")
    public void selectCategoryByText(String text) {
        basePage.moveMouseToElement(categoriesDropDown);
        basePage.selectDropDownValueByVisibleText(categoriesSelector, text);
    }

    @Step("Submit search")
    public void submitSearchBtn() {
        basePage.clickElement(searchBtn);
    }
}
