package pages;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static utils.Constants.*;

@Component
public class LoginPage {

    @Autowired
    public BasePage basePage;

    private By email = By.xpath(".//input[@name='fm-login-id']");
    private By password = By.xpath(".//input[@name='fm-login-password']");
    private By submitBtn = By.xpath(".//button[@class='fm-button fm-submit password-login']");

    public void signIn() {
        if (basePage.isElementPresent(submitBtn)) {
            basePage.setText(email, LOGIN_EMAIL);
            basePage.setText(password, LOGIN_PWD);
            basePage.clickElement(submitBtn);
        }
    }
}
