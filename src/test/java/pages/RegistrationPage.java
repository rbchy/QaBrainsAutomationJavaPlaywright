package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

/** Registration form. The menu's element ids are generated per page load, so the page is opened by URL. */
public class RegistrationPage {

    private final Page page;

    public RegistrationPage(Page page) {
        this.page = page;
    }

    public void open(String baseUrl) {
        if (!page.url().contains("/registration")) {
            page.navigate(baseUrl.replaceAll("/+$", "") + "/registration");
        }
        page.locator("#name").waitFor();
    }

    public void fill(String name, String country, String account, String email, String password) {
        page.locator("#name").fill(name);
        page.locator("#country").selectOption(new SelectOption().setLabel(country));
        page.locator("#account").selectOption(new SelectOption().setLabel(account));
        page.locator("#email").fill(email);
        page.locator("#password").fill(password);
        page.locator("#confirm_password").fill(password);
    }

    public void submit() {
        page.locator("form button[type='submit']").first().click();
    }

    public boolean isRegistrationSuccessful() {
        try {
            page.getByText("successfully", new Page.GetByTextOptions().setExact(false)).first().waitFor();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** The browser's own HTML5 validation message for the e-mail field. */
    public String emailValidationMessage() {
        return (String) page.locator("#email").evaluate("el => el.validationMessage");
    }
}
