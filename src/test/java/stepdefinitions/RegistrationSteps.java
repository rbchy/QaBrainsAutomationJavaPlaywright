package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.RegistrationPage;
import utils.ConfigReader;
import utils.DriverFactory;

public class RegistrationSteps {

    private static final String NAME = "RB Chowdhury";
    private static final String COUNTRY = "United States";
    private static final String ACCOUNT_TYPE = "Engineer";
    private static final String PASSWORD = "Password123";

    private RegistrationPage page() {
        return new RegistrationPage(DriverFactory.getPage());
    }

    @When("User navigates to registration page")
    public void user_navigates_to_registration_page() {
        page().open(ConfigReader.get("url"));
    }

    @When("User enters valid registration details with email {string}")
    public void user_enters_valid_registration_details_with_email(String email) {
        page().fill(NAME, COUNTRY, ACCOUNT_TYPE, email, PASSWORD);
    }

    @When("User enters registration details with email {string}")
    public void user_enters_registration_details_with_email(String email) {
        page().fill(NAME, COUNTRY, ACCOUNT_TYPE, email, PASSWORD);
    }

    @When("User clicks on register button")
    public void user_clicks_on_register_button() {
        page().submit();
    }

    @Then("User should be registered successfully")
    public void user_should_be_registered_successfully() {
        Assert.assertTrue(page().isRegistrationSuccessful(), "No success message after registration");
    }

    @Then("Email validation error message should be displayed")
    public void email_validation_error_message_should_be_displayed() {
        String message = page().emailValidationMessage();
        Assert.assertTrue(message != null && !message.isBlank(), "The browser did not reject an e-mail without '@'");
    }
}
