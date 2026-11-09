package stepdefinitions;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import utils.ConfigReader;
import utils.DriverFactory;

public class CommonSteps {

    private Page page() {
        return DriverFactory.getPage();
    }

    @Given("User launches the application")
    public void user_launches_the_application() {
        page().navigate(ConfigReader.get("url"));
    }

    @Given("User is on homepage")
    public void user_is_on_homepage() {
        if (!page().url().contains("practice.qabrains.com")) {
            page().navigate(ConfigReader.get("url"));
        }
        Assert.assertTrue(page().url().contains("practice.qabrains.com"), "Not on the home page: " + page().url());
    }

    @Then("Home page should load successfully")
    public void home_page_should_load_successfully() {
        page().waitForLoadState();
        Assert.assertFalse(page().title().isBlank(), "Home page has no title");
        Assert.assertTrue(page().locator("body").isVisible(), "Home page body is not visible");
    }
}
