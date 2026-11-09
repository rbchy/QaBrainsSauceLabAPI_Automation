package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * The QA Brains e-commerce demo (practice.qabrains.com/ecommerce).
 * Product cards link to /ecommerce/product-details?id=N and carry one button whose text
 * toggles between "Add to cart" and "Remove from cart".
 */
public class EcommercePage {

    private static final String ADD = "Add to cart";
    private static final String REMOVE = "Remove from cart";

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By email = By.id("email");
    private final By password = By.id("password");
    private final By submit = By.cssSelector("button[type='submit']");
    private final By addButtons = By.xpath("//button[normalize-space()='" + ADD + "']");
    private final By removeButtons = By.xpath("//button[normalize-space()='" + REMOVE + "']");
    private final By productLinks = By.cssSelector("a[href*='/ecommerce/product-details']");

    public EcommercePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void open(String baseUrl) {
        driver.get(baseUrl.replaceAll("/+$", "") + "/ecommerce/login");
    }

    public void login(String user, String pass) {
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(email));
        e.clear();
        e.sendKeys(user);
        WebElement p = driver.findElement(password);
        p.clear();
        p.sendKeys(pass);
        click(driver.findElement(submit));
        wait.until(ExpectedConditions.visibilityOfElementLocated(addButtons));
    }

    public boolean isOnProductList() {
        return !driver.findElements(addButtons).isEmpty() || !driver.findElements(removeButtons).isEmpty();
    }

    public int productCount() {
        return (int) driver.findElements(productLinks).stream()
                .map(a -> a.getAttribute("href")).distinct().count();
    }

    public int inCartCount() {
        return driver.findElements(removeButtons).size();
    }

    public void addProducts(int howMany) {
        int target = inCartCount() + howMany;
        for (int i = 0; i < howMany; i++) {
            int before = inCartCount();
            click(wait.until(ExpectedConditions.elementToBeClickable(addButtons)));
            wait.until(d -> inCartCount() == before + 1);
        }
        wait.until(d -> inCartCount() == target);
    }

    public void removeFirstProduct() {
        int before = inCartCount();
        click(wait.until(ExpectedConditions.elementToBeClickable(removeButtons)));
        wait.until(d -> inCartCount() == before - 1);
    }

    public void openFirstProduct() {
        String href = driver.findElements(productLinks).get(0).getAttribute("href");
        driver.get(href);
        wait.until(ExpectedConditions.urlContains("product-details"));
    }

    /** Text of every product card: the closest ancestor of each cart button that also holds a product link. */
    public List<String> productCardTexts() {
        List<WebElement> cards = driver.findElements(By.xpath(
                "//button[normalize-space()='" + ADD + "' or normalize-space()='" + REMOVE + "']"
                        + "/ancestor::*[.//a[contains(@href,'product-details')]][1]"));
        return cards.stream().map(WebElement::getText).toList();
    }

    public String pageText() {
        return driver.findElement(By.tagName("body")).getText();
    }

    private void click(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }
}
