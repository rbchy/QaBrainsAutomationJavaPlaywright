package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.LoginPage;
import pages.LogoutPage;
import utils.ConfigReader;
import utils.DriverFactory;

/** Login and logout on practice.qabrains.com. */
public class AuthSteps {

    private LoginPage login() {
        return new LoginPage(DriverFactory.getPage());
    }

    private void loginWithConfiguredUser() {
        login().login(ConfigReader.get("valid_email"), ConfigReader.get("valid_password"));
    }

    @When("User enters valid username and password")
    public void user_enters_valid_username_and_password() {
        loginWithConfiguredUser();
    }

    @When("User enters invalid username and password")
    public void user_enters_invalid_username_and_password() {
        login().login("wrong.user@example.com", "WrongPass1");
    }

    @Then("User should be logged in successfully")
    public void user_should_be_logged_in_successfully() {
        Assert.assertTrue(login().isLoginSuccessful(), "Login was not successful");
    }

    @Then("Error message should be displayed")
    public void error_message_should_be_displayed() {
        Assert.assertTrue(login().isErrorDisplayed(), "No error message after an invalid login");
    }

    @Given("User is logged in")
    public void user_is_logged_in() {
        loginWithConfiguredUser();
        Assert.assertTrue(login().isLoginSuccessful(), "Could not log in before the logout test");
    }

    @When("User clicks on logout button")
    public void user_clicks_on_logout_button() {
        new LogoutPage(DriverFactory.getPage()).clickLogout();
    }

    @Then("User should be logged out successfully")
    public void user_should_be_logged_out_successfully() {
        Assert.assertTrue(new LogoutPage(DriverFactory.getPage()).isLogoutSuccessful(),
                "Login form did not come back after logout");
    }
}
