package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.BaseTest;
import utils.DriverFactory;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A fresh browser per scenario: local Chrome by default, Sauce Labs when -Dsauce=true and the
 * SAUCE_* environment variables are set. On failure the report gets a screenshot and the page HTML;
 * on Sauce Labs the job is also marked passed / failed in the dashboard.
 */
public class Hooks {

    private boolean onSauce;

    @Before
    public void openBrowser(Scenario scenario) throws Exception {
        onSauce = DriverFactory.sauceEnabled();
        WebDriver driver = onSauce
                ? useSauce(scenario.getName())
                : BaseTest.initDriver();
        driver.get(BaseTest.config.getProperty("url"));
    }

    private WebDriver useSauce(String name) throws Exception {
        WebDriver remote = DriverFactory.createSauceDriver(name);
        BaseTest.useDriver(remote);
        return remote;
    }

    @After
    public void closeBrowser(Scenario scenario) {
        WebDriver driver = BaseTest.currentDriver();
        try {
            if (scenario.isFailed() && driver != null) {
                byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(png, "image/png", scenario.getName());

                String html = driver.getPageSource();
                scenario.attach(html, "text/html", "Page source at failure (" + driver.getCurrentUrl() + ")");
                Path dir = Paths.get("target", "failures");
                Files.createDirectories(dir);
                Files.writeString(dir.resolve(scenario.getName().replaceAll("[^A-Za-z0-9-]+", "_") + ".html"),
                        "<!-- " + driver.getCurrentUrl() + " -->\n" + html, StandardCharsets.UTF_8);
            }
            if (onSauce && driver != null) {
                ((JavascriptExecutor) driver).executeScript(
                        "sauce:job-result=" + (scenario.isFailed() ? "failed" : "passed"));
            }
        } catch (Exception e) {
            System.err.println("Could not capture failure evidence: " + e.getMessage());
        } finally {
            BaseTest.quitDriver();
        }
    }
}
