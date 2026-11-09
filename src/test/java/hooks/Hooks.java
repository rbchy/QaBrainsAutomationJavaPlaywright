package hooks;

import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.DriverFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** A fresh browser per scenario; on failure a full-page screenshot and the page HTML go into the report. */
public class Hooks {

    @Before
    public void setup() {
        DriverFactory.initDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        Page page = DriverFactory.getPage();
        try {
            if (scenario.isFailed() && page != null) {
                String name = "FAILED_" + scenario.getName().replaceAll("[^A-Za-z0-9-]+", "_");
                Path dir = Paths.get("target", "screenshots");
                Files.createDirectories(dir);

                byte[] png = page.screenshot(new Page.ScreenshotOptions()
                        .setFullPage(true).setPath(dir.resolve(name + ".png")));
                scenario.attach(png, "image/png", name);

                String html = page.content();
                scenario.attach(html, "text/html", "Page source at failure (" + page.url() + ")");
                Files.writeString(dir.resolve(name + ".html"), "<!-- " + page.url() + " -->\n" + html);
            }
        } catch (Exception e) {
            System.err.println("Could not capture failure evidence: " + e.getMessage());
        } finally {
            DriverFactory.quitDriver();
        }
    }
}
