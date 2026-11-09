package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LogoutPage {

    WebDriver driver;
    WebDriverWait wait;

    By logoutBtn = By.xpath("//button[contains(text(),'Logout')]");
    By loginBtn = By.xpath("//form//button[contains(text(),'Login')]"); // ✅ updated

    public LogoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.urlContains("logged=true"));
        wait.until(ExpectedConditions.presenceOfElementLocated(logoutBtn));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(logoutBtn));
        js.executeScript("arguments[0].click();", driver.findElement(logoutBtn));
    }

    public boolean isLogoutSuccessful() {
        // ✅ Wait for page URL to change to login page
        wait.until(ExpectedConditions.urlContains("practice.qabrains.com"));
        // ✅ Wait for login button to appear
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginBtn)).isDisplayed();
    }
}