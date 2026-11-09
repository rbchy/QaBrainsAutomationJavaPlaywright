package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * One Playwright / Browser / Context / Page per thread (ThreadLocal), so scenarios can run in parallel.
 * Headless when config.properties or -Dheadless=true says so.
 */
public final class DriverFactory {

    private static final ThreadLocal<Playwright> PLAYWRIGHT = new ThreadLocal<>();
    private static final ThreadLocal<Browser> BROWSER = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<Page> PAGE = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static Page getPage() {
        return PAGE.get();
    }

    public static BrowserContext getContext() {
        return CONTEXT.get();
    }

    public static void initDriver() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(ConfigReader.getBoolean("headless")));
        BrowserContext context = browser.newContext(
                new Browser.NewContextOptions().setViewportSize(1920, 1080));
        context.setDefaultTimeout(ConfigReader.getInt("timeout"));
        Page page = context.newPage();

        PLAYWRIGHT.set(playwright);
        BROWSER.set(browser);
        CONTEXT.set(context);
        PAGE.set(page);

        page.navigate(ConfigReader.get("url"));
    }

    public static void quitDriver() {
        if (PAGE.get() != null) { PAGE.get().close(); PAGE.remove(); }
        if (CONTEXT.get() != null) { CONTEXT.get().close(); CONTEXT.remove(); }
        if (BROWSER.get() != null) { BROWSER.get().close(); BROWSER.remove(); }
        if (PLAYWRIGHT.get() != null) { PLAYWRIGHT.get().close(); PLAYWRIGHT.remove(); }
    }
}
