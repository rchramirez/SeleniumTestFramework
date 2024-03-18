package Tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.TestListener;

import static org.testng.Assert.assertTrue;
import static utils.Constants.SALUTATION_MESSAGE;

@Listeners(TestListener.class)
public class LoginTests extends BaseTest {

    @Test(priority = 1)
    public void loginFromUserBemefits() {
        homePage.loginFromHomePage();
        String usernameWelcomeMessage = homePage.getUserNameMessage();
        assertTrue(usernameWelcomeMessage.contains(SALUTATION_MESSAGE), usernameWelcomeMessage + "should contain salutation message");
    }
}
