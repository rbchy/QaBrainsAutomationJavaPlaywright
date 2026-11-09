package utils;

import com.microsoft.playwright.Page;

/** Kept for compatibility with existing code; the browser itself is owned by DriverFactory (one per thread). */
public class BaseTest {

    public static void initBrowser() {
        DriverFactory.initDriver();
    }

    public static Page getPage() {
        return DriverFactory.getPage();
    }

    public static void quitBrowser() {
        DriverFactory.quitDriver();
    }
}
