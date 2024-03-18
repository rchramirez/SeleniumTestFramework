package Tests;

import component.AliExpressDriver;
import config.ApplicationContextConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.HomePage;
import pages.SearchPage;

@ContextConfiguration(classes = ApplicationContextConfig.class)
public class BaseTest extends AbstractTestNGSpringContextTests {

    @Autowired
    public AliExpressDriver driver;

    @Autowired
    public HomePage homePage;

    @Autowired
    public SearchPage searchPage;

    @BeforeMethod(alwaysRun = true)
    public void createTestContext(ITestContext context) {
        context.setAttribute("driver", driver.getDriver());
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        driver.get(driver.getBaseUrl());
        homePage.closeCoupon();
    }

    @AfterSuite(alwaysRun = true)
    public void closeBrowser() {
        driver.closeDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void goToHomePage() {
        driver.get(driver.getBaseUrl());
        homePage.closeCoupon();
    }
}
