package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ForgotPasswordPage {

    private final Page page;

    public ForgotPasswordPage(Page page) {
        this.page = page;
    }

    public void open(String baseUrl) {
        page.navigate(baseUrl.replaceAll("/+$", "") + "/forgot-password");
        page.locator("#email").waitFor();
    }

    public void enterEmail(String email) {
        page.locator("#email").fill(email);
    }

    public void submit() {
        page.locator("#email").locator("xpath=ancestor::form").locator("button[type='submit'], button")
                .first().click();
    }

    public String successMessage() {
        try {
            Locator msg = page.locator("#success-msg");
            msg.waitFor();
            return msg.innerText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /** Inline error next to the field if there is one, otherwise the browser's HTML5 validation message. */
    public String errorMessage() {
        String html5 = (String) page.locator("#email").evaluate("el => el.validationMessage");
        if (html5 != null && !html5.isBlank()) {
            return html5;
        }
        Locator inline = page.locator("#email ~ *").first();
        return inline.count() > 0 ? inline.innerText().trim() : "";
    }
}
