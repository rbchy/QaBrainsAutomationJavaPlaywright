package pages;

import com.microsoft.playwright.Page;

public class LogoutPage {

    private final Page page;

    public LogoutPage(Page page) {
        this.page = page;
    }

    public void clickLogout() {
        page.waitForURL("**/*logged=true*");
        page.locator("button:has-text('Logout')").first().click();
    }

    public boolean isLogoutSuccessful() {
        try {
            page.locator("form button[type='submit']:has-text('Login')").first().waitFor();
            return page.locator("#email").isVisible();
        } catch (Exception e) {
            return false;
        }
    }
}
