package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By registerLink = By.xpath("//*[@id='registration']/span");

    private By nameField = By.id("name");
    private By countryDropdown = By.id("country");
    private By accountTypeDropdown = By.id("account");
    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By confirmPasswordField = By.id("confirm_password");
    private By registerBtn = By.xpath("//button[@type='submit']");

    private By successMsg = By.xpath("//*[contains(translate(., 'SUCCESSFULLY', 'successfully'),'successfully')]");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private void safeClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public void openRegistrationPage() {
        if (driver.getCurrentUrl().contains("registration")) {
            return;
        }

        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(registerLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", link);
        safeClick(link);
        wait.until(ExpectedConditions.urlContains("registration"));
    }

    public void enterRegistrationDetails(String fullName, String country, String accountType,
                                         String email, String password, String confirmPassword) {

        WebElement nameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        nameEl.clear();
        nameEl.sendKeys(fullName);

        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(countryDropdown)))
                .selectByVisibleText(country);

        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(accountTypeDropdown)))
                .selectByVisibleText(accountType);

        WebElement emailEl = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        emailEl.clear();
        emailEl.sendKeys(email);

        WebElement pwdEl = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        pwdEl.clear();
        pwdEl.sendKeys(password);

        WebElement confirmPwdEl = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordField));
        confirmPwdEl.clear();
        confirmPwdEl.sendKeys(confirmPassword);
    }

    public void clickRegister() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(registerBtn));
        safeClick(btn);
    }

    public boolean isRegistrationSuccessful() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMsg)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailValidationMessage() {
        try {
            WebElement emailEl = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
            return emailEl.getAttribute("validationMessage");
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isEmailValidationMessageDisplayed() {
        String msg = getEmailValidationMessage();
        return msg != null && !msg.trim().isEmpty();
    }
}