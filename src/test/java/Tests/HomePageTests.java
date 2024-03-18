package Tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.TestListener;

import static org.testng.Assert.assertTrue;

@Listeners(TestListener.class)
public class HomePageTests extends BaseTest {

    @Test
    public void checkUrl() {
        String currentUrl = driver.getCurrentUrl();
        String baseUrl = driver.getBaseUrl();
        assertTrue(currentUrl.equalsIgnoreCase(baseUrl));
    }
}
