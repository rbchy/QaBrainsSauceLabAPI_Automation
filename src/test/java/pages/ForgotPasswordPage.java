package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ForgotPasswordPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // Fixed: Initialized emailField and fixed syntax
    private By emailField = By.id("email"); 
    private By submitBtn = By.xpath("//*[@id=\"inner-body\"]/div/div[1]/form/button");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public void enterEmail(String email) {
        WebElement emailEl = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        emailEl.clear();
        emailEl.sendKeys(email);
    }

    public void clickSubmit() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(submitBtn));
        js.executeScript("arguments[0].click();", btn);
        // Note: Thread.sleep is generally discouraged; consider a wait for a specific element instead.
        try { Thread.sleep(3000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public String getSuccessMessage() {
        String[] successSelectors = {
            "//*[@id=\"success-msg\"]/div[1]/div/span[1]",
            "//*[@id=\"success-msg\"]/div[2]/h2"
        };
        
        for (String selector : successSelectors) {
            try {
                WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selector)));
                String text = msg.getText().trim();
                if (!text.isEmpty()) return text;
            } catch (Exception ignored) {}
        }
        return "";
    }

    public String getErrorMessage() {
        String[] preciseSelectors = {
            "//*[@id='email']/following-sibling::div[1]",
            "//*[@id='email']/following-sibling::span[1]", 
            "//*[@id='email']/following-sibling::*[1][contains(text(), '@')]",
            "//input[@id='email']/following-sibling::*[contains(text(), '@')]",
            "//*[@id='email']/ancestor::*//div[contains(text(), '@')][1]"
        };

        for (String selector : preciseSelectors) {
            try {
                // Use findElements to avoid NoSuchElementException
                List<WebElement> elements = driver.findElements(By.xpath(selector));
                if (!elements.isEmpty()) {
                    String text = elements.get(0).getText().trim();
                    if (!text.isEmpty() && !text.equalsIgnoreCase("RESET PASSWORD")) {
                        System.out.println("✅ ERROR: '" + text + "' [" + selector + "]");
                        return text;
                    }
                }
            } catch (Exception ignored) {}
        }

        // HTML5 validation fallback
        try {
            // Fixed: Locate the element properly before passing to JS
            WebElement emailEl = driver.findElement(emailField);
            String validationMsg = (String) js.executeScript("return arguments[0].validationMessage;", emailEl);
            if (validationMsg != null && !validationMsg.isEmpty()) {
                System.out.println("✅ HTML5 ERROR: '" + validationMsg + "'");
                return validationMsg;
            }
        } catch (Exception ignored) {}

        return "";
    }
}