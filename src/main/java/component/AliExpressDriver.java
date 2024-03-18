package component;

import config.ApplicationContextConfig;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static utils.Constants.TIMEOUT;

@Component
public class AliExpressDriver extends ApplicationContextConfig {

    public static WebDriver driver;

    @Value("${headless}")
    private boolean headless;

    @Value("${baseUrl}")
    private String baseUrl;

    public AliExpressDriver(@Value("${browser}") String browser, @Value("${headless}") boolean headless) {
        if (System.getProperty("os.name").toLowerCase().contains("win")){
            if(browser.equalsIgnoreCase("firefox")){
                System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/win/geckodriver.exe");
            }else if(browser.equalsIgnoreCase("chrome")){
                System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/win/chromedriver.exe");
            }
        }
        if (System.getProperty("os.name").toLowerCase().contains("linux")){
            if(browser.equalsIgnoreCase("firefox")){
                System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/linux/geckodriver");
            }else if(browser.equalsIgnoreCase("chrome")){
                System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/linux/chromedriver");
            }
        }
        switch (browser) {

            case "firefox":

                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(headless);
                firefoxOptions.addArguments("--start-maximized");
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
                driver = new FirefoxDriver(firefoxOptions);
                driver.manage().deleteAllCookies();
                driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
                break;
            case "chrome":

            default:

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setHeadless(headless);
                chromeOptions.addArguments("--start-maximized");
//                chromeOptions.addArguments("--incognito");
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
                driver = new ChromeDriver(chromeOptions);
                driver.manage().deleteAllCookies();
                driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
                break;

        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void closeDriver() {
        driver.close();
    }

    public void get(String url) {
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public boolean isHeadlessSet() {
        return headless;
    }
}
