package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void initDriver(String testName) throws Exception {
        String username = "oauth-chyranajit-4079f";
        String accessKey = "b02d19e6-afd5-470b-92f6-03e892b5dafe";
        
        System.out.println("✅ Sauce Labs connected: " + username);
        
        String sauceURL = "https://" + username + ":" + accessKey +
                "@ondemand.us-west-1.saucelabs.com:443/wd/hub";

        MutableCapabilities browserOptions = new MutableCapabilities();
        browserOptions.setCapability("browserName", "chrome");
        browserOptions.setCapability("browserVersion", "latest");
        browserOptions.setCapability("platformName", "Windows 11");

        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("build", "QAbrains Build");
        sauceOptions.setCapability("name", testName);

        browserOptions.setCapability("sauce:options", sauceOptions);

        driver.set(new RemoteWebDriver(new URL(sauceURL), browserOptions));
        getDriver().manage().window().maximize();
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}