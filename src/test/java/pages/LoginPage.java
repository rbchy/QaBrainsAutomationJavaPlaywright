package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

/** Login form on the practice site's home page. */
public class LoginPage {

    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    private Locator email() { return page.locator("#email"); }
    private Locator password() { return page.locator("#password"); }
    private Locator loginButton() { return page.locator("form button[type='submit']").first(); }
    private Locator successMessage() { return page.locator("#success-msg"); }
    private Locator errorMessage() { return page.getByText("Your email and password both are invalid"); }

    public void login(String user, String pass) {
        email().fill(user);
        password().fill(pass);
        loginButton().click();
        // wait for either outcome
        page.locator("#success-msg, :text('Your email and password both are invalid')")
                .first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    public boolean isLoginSuccessful() {
        try {
            successMessage().waitFor();
            return !successMessage().innerText().isBlank();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorDisplayed() {
        try {
            errorMessage().first().waitFor();
            return errorMessage().first().isVisible();
        } catch (Exception e) {
            return false;
        }
    }
}
