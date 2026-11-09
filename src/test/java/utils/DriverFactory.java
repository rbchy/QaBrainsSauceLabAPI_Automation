package utils;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

/**
 * Creates a Sauce Labs RemoteWebDriver. Credentials come ONLY from the environment
 * (SAUCE_USERNAME / SAUCE_ACCESS_KEY) — never from code or config files.
 * Browser and platform can be chosen with -Dsauce.browser / -Dsauce.platform.
 */
public final class DriverFactory {

    private static final String SAUCE_HUB = "https://ondemand.us-west-1.saucelabs.com:443/wd/hub";

    private DriverFactory() {
    }

    /** True when -Dsauce=true (or sauce=true in config.properties) and both credentials are present. */
    public static boolean sauceEnabled() {
        boolean wanted = Boolean.parseBoolean(System.getProperty("sauce", BaseTest.config.getProperty("sauce", "false")));
        return wanted && notBlank(System.getenv("SAUCE_USERNAME")) && notBlank(System.getenv("SAUCE_ACCESS_KEY"));
    }

    public static WebDriver createSauceDriver(String testName) throws Exception {
        String username = System.getenv("SAUCE_USERNAME");
        String accessKey = System.getenv("SAUCE_ACCESS_KEY");
        if (!notBlank(username) || !notBlank(accessKey)) {
            throw new IllegalStateException("Set SAUCE_USERNAME and SAUCE_ACCESS_KEY as environment variables");
        }

        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", username);
        sauceOptions.setCapability("accessKey", accessKey);
        sauceOptions.setCapability("build", System.getProperty("sauce.build",
                System.getenv().getOrDefault("GITHUB_RUN_ID", "QAbrains Build")));
        sauceOptions.setCapability("name", testName);

        MutableCapabilities browserOptions = new MutableCapabilities();
        browserOptions.setCapability("browserName", System.getProperty("sauce.browser", "chrome"));
        browserOptions.setCapability("browserVersion", "latest");
        browserOptions.setCapability("platformName", System.getProperty("sauce.platform", "Windows 11"));
        browserOptions.setCapability("sauce:options", sauceOptions);

        System.out.println("✅ Sauce Labs session for: " + testName);
        WebDriver driver = new RemoteWebDriver(new URL(SAUCE_HUB), browserOptions);
        driver.manage().window().maximize();
        return driver;
    }

    private static boolean notBlank(String s) {
        return s != null && !s.isBlank();
    }
}
