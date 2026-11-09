package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The QA Brains e-commerce demo (/ecommerce). Each product card links to
 * /ecommerce/product-details?id=N and has one button that toggles between
 * "Add to cart" and "Remove from cart".
 */
public class EcommercePage {

    private static final String ADD = "Add to cart";
    private static final String REMOVE = "Remove from cart";

    private final Page page;

    public EcommercePage(Page page) {
        this.page = page;
    }

    private Locator addButtons() { return page.locator("button", new Page.LocatorOptions().setHasText(ADD)); }
    private Locator removeButtons() { return page.locator("button", new Page.LocatorOptions().setHasText(REMOVE)); }
    private Locator productLinks() { return page.locator("a[href*='/ecommerce/product-details']"); }

    public void open(String baseUrl) {
        page.navigate(baseUrl.replaceAll("/+$", "") + "/ecommerce/login");
    }

    public void login(String email, String password) {
        page.locator("#email").fill(email);
        page.locator("#password").fill(password);
        page.locator("button[type='submit']").first().click();
        addButtons().first().waitFor();
    }

    public boolean isOnProductList() {
        return addButtons().count() + removeButtons().count() > 0;
    }

    public int inCartCount() {
        return removeButtons().count();
    }

    public void addProducts(int howMany) {
        for (int i = 0; i < howMany; i++) {
            int before = inCartCount();
            addButtons().first().click();
            removeButtons().nth(before).waitFor();
        }
    }

    public void removeFirstProduct() {
        int before = inCartCount();
        removeButtons().first().click();
        page.waitForCondition(() -> inCartCount() == before - 1);
    }

    public int productCount() {
        return (int) productLinks().all().stream()
                .map(a -> a.getAttribute("href")).distinct().count();
    }

    public void openFirstProduct() {
        page.navigate(productLinks().first().evaluate("a => a.href").toString());
        page.waitForURL("**/product-details**");
    }

    /** Text of every product card: the closest ancestor of a cart button that also holds a product link. */
    public List<String> productCardTexts() {
        return page.locator("xpath=//button[normalize-space()='" + ADD + "' or normalize-space()='" + REMOVE + "']"
                        + "/ancestor::*[.//a[contains(@href,'product-details')]][1]")
                .all().stream().map(Locator::innerText).collect(Collectors.toList());
    }

    public String pageText() {
        return page.locator("body").innerText();
    }
}
