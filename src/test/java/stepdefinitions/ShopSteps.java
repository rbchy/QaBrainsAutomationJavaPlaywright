package stepdefinitions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.EcommercePage;
import utils.ConfigReader;
import utils.DriverFactory;

import java.util.ArrayList;
import java.util.List;

/** E-commerce demo (cart, product details) and menu navigation smoke checks. */
public class ShopSteps {

    private final List<String> problems = new ArrayList<>();
    private int modulesOpened;

    private Page page() {
        return DriverFactory.getPage();
    }

    private EcommercePage shop() {
        return new EcommercePage(page());
    }

    // ---------- e-commerce ----------

    @Given("User is logged in to the e-commerce site")
    public void user_is_logged_in_to_the_e_commerce_site() {
        shop().open(ConfigReader.get("url"));
        shop().login(ConfigReader.get("shop_email"), ConfigReader.get("shop_password"));
        Assert.assertTrue(shop().isOnProductList(), "Product list did not load after the shop login");
    }

    @When("User adds products to cart")
    public void user_adds_products_to_cart() {
        shop().addProducts(1);
    }

    @When("User adds {int} products to cart")
    public void user_adds_n_products_to_cart(int count) {
        shop().addProducts(count);
    }

    @Then("Product should appear in cart")
    public void product_should_appear_in_cart() {
        Assert.assertEquals(shop().inCartCount(), 1, "Products marked 'Remove from cart'");
    }

    @Then("{int} products should be in the cart")
    public void n_products_should_be_in_the_cart(int count) {
        Assert.assertEquals(shop().inCartCount(), count, "Products marked 'Remove from cart'");
    }

    @When("User removes product from cart")
    public void user_removes_product_from_cart() {
        shop().removeFirstProduct();
    }

    @Then("Cart should be empty")
    public void cart_should_be_empty() {
        Assert.assertEquals(shop().inCartCount(), 0, "Products still marked 'Remove from cart'");
    }

    @When("User opens the details of the first product")
    public void user_opens_the_details_of_the_first_product() {
        shop().openFirstProduct();
    }

    @Then("The product details page should show its name and price")
    public void the_product_details_page_should_show_its_name_and_price() {
        Assert.assertTrue(page().url().contains("product-details?id="), "Not on a details page: " + page().url());
        page().waitForCondition(() -> shop().pageText().matches("(?s).*\\$\\d+\\.\\d{2}.*"));
        Assert.assertTrue(shop().pageText().contains("Sample"), "Product name not shown on the details page");
    }

    @Then("Every product card should show a name, a price and an {string} button")
    public void every_product_card_should_show_a_name_a_price_and_button(String buttonText) {
        List<String> cards = shop().productCardTexts();
        Assert.assertTrue(cards.size() >= 1, "No product cards found");
        Assert.assertEquals(cards.size(), shop().productCount(), "One card per product link");
        for (String card : cards) {
            Assert.assertTrue(card.matches("(?s).*\\$\\d+\\.\\d{2}.*"), "No price on card: " + card);
            Assert.assertTrue(card.contains("Name"), "No product name on card: " + card);
            Assert.assertTrue(card.contains(buttonText), "No '" + buttonText + "' button on card: " + card);
        }
    }

    // ---------- menu navigation ----------

    @When("User opens each demo module from the menu")
    public void user_opens_each_demo_module_from_the_menu(DataTable modules) {
        for (List<String> row : modules.asLists()) {
            String label = row.get(0);
            String expectedInUrl = row.get(1);
            page().navigate(ConfigReader.get("url"));
            String url = clickMenuItem(label);
            if (url == null || !url.contains(expectedInUrl)) {
                problems.add("'" + label + "' opened " + url + " (expected '" + expectedInUrl + "' in the URL)");
            } else {
                modulesOpened++;
            }
        }
    }

    @Then("Each module page should open")
    public void each_module_page_should_open() {
        Assert.assertTrue(problems.isEmpty(), "Menu problems: " + problems);
        Assert.assertTrue(modulesOpened > 0, "No module was opened");
    }

    /** Clicks a sidebar item by its visible label; returns the URL it opened (same or new tab). */
    private String clickMenuItem(String label) {
        Locator item = page().locator("[role='menuitem']", new Page.LocatorOptions().setHasText(label))
                .or(page().locator("a", new Page.LocatorOptions().setHasText(label))).first();
        try {
            // the sidebar is rendered client-side: wait for it instead of checking count() immediately
            item.waitFor(new Locator.WaitForOptions().setTimeout(15000));
        } catch (Exception notRendered) {
            return null;
        }
        String start = page().url();
        int tabs = DriverFactory.getContext().pages().size();
        item.click();
        try {
            page().waitForCondition(() -> DriverFactory.getContext().pages().size() > tabs
                    || !page().url().equals(start), new Page.WaitForConditionOptions().setTimeout(15000));
        } catch (Exception timeout) {
            return page().url();
        }
        List<Page> pages = DriverFactory.getContext().pages();
        if (pages.size() > tabs) {
            Page opened = pages.get(pages.size() - 1);
            opened.waitForLoadState();
            String url = opened.url();
            opened.close();
            return url;
        }
        return page().url();
    }
}
