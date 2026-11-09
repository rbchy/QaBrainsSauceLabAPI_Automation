package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.InputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BaseTest {

    protected static WebDriver driver;
    public static Properties config = new Properties();

    static {
        try (InputStream is = BaseTest.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) {
                throw new RuntimeException("❌ config.properties not found in src/test/resources!");
            }
            config.load(is);
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to load config.properties", e);
        }
    }

    public static WebDriver initDriver() {
        // ✅ Session check - যদি driver invalid হয় নতুন create করুন
        if (driver == null || isDriverInvalid()) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            
            // ✅ Password popup disable (main fix)
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("autofill.profile_enabled", false);
            options.setExperimentalOption("prefs", prefs);
            
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-notifications");
            options.addArguments("--incognito"); // Cache clear

            // ✅ Headless for CI: mvn test -Dheadless=true (or headless=true in config.properties)
            boolean headless = Boolean.parseBoolean(
                    System.getProperty("headless", config.getProperty("headless", "false")));
            if (headless) {
                options.addArguments("--headless=new", "--window-size=1920,1080",
                        "--no-sandbox", "--disable-dev-shm-usage");
            }

            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        }
        return driver;
    }

    // ✅ Driver validity check
    private static boolean isDriverInvalid() {
        try {
            if (driver == null) return true;
            driver.getCurrentUrl(); // Simple health check
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static WebDriver getDriver() {
        return initDriver();
    }

    /** Use a driver created elsewhere (e.g. a Sauce Labs RemoteWebDriver from DriverFactory). */
    public static void useDriver(WebDriver remote) {
        driver = remote;
    }

    /** The open driver, or null — never starts a new browser. */
    public static WebDriver currentDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                // Ignore quit errors
            } finally {
                driver = null;
            }
        }
    }
}