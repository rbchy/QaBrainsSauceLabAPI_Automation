package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ================= LOCATORS =================
    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By loginButton = By.xpath("//button[text()='Login']");

    private By successMessage = By.xpath("//*[@id='success-msg']//p");

    // ✅ Using your exact XPath for invalid login
    private By errorMessage = By.xpath("//*[@id='home-page']/div/div[1]/form/div[1]/div/span[1]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ================= LOGIN ACTION =================
    public void login(String email, String password) {

        WebElement emailElem = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        emailElem.clear();
        emailElem.sendKeys(email);

        WebElement passElem = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passElem.clear();
        passElem.sendKeys(password);

        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        try {
            loginBtn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn);
        }

        // Wait for either success or error message (30s)
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        longWait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(successMessage),
                ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ));
    }

    // ================= SUCCESS VALIDATION =================
    public boolean isLoginSuccessful() {
        try {
            WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            String text = successMsg.getText();
            System.out.println("✅ Success Message: " + text);
            return !text.isEmpty();
        } catch (Exception e) {
            // Backup check for Welcome text
            try {
                return driver.findElement(By.xpath("//*[contains(text(),'Welcome')]")).isDisplayed();
            } catch (Exception ex) {
                System.out.println("❌ Success message NOT found!");
                return false;
            }
        }
    }

    // ================= ERROR VALIDATION =================
    public boolean isErrorDisplayed() {
        try {
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            String text = errorMsg.getText();
            System.out.println("🔴 Error Message: " + text);
            return !text.isEmpty();
        } catch (Exception e) {
            System.out.println("❌ Error message NOT found!");
            return false;
        }
    }
}