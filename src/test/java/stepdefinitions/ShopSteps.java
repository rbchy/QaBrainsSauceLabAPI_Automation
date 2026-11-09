package stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.EcommercePage;
import utils.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** E-commerce demo (cart, product details) and menu navigation smoke checks. */
public class ShopSteps {

    private final List<String> problems = new ArrayList<>();
    private int modulesOpened;

    private WebDriver driver() {
        return BaseTest.getDriver();
    }

    private EcommercePage shop() {
        return new EcommercePage(driver());
    }

    // ---------- e-commerce ----------

    @Given("User is logged in to the e-commerce site")
    public void user_is_logged_in_to_the_e_commerce_site() {
        EcommercePage shop = shop();
        shop.open(BaseTest.config.getProperty("url"));
        shop.login(BaseTest.config.getProperty("shop_email"), BaseTest.config.getProperty("shop_password"));
        Assert.assertTrue(shop.isOnProductList(), "Product list did not load after the shop login");
    }

    @When("User adds products to cart")
    public void user_adds_products_to_cart() {
        shop().addProducts(1);
    }

    @When("User adds {int} products to cart")
    public void user_adds_n_products_to_cart(int count) {
        shop().addProducts(count);
    }

    @Then("Product should appear in cart")
    public void product_should_appear_in_cart() {
        Assert.assertEquals(shop().inCartCount(), 1, "Products marked 'Remove from cart'");
    }

    @Then("{int} products should be in the cart")
    public void n_products_should_be_in_the_cart(int count) {
        Assert.assertEquals(shop().inCartCount(), count, "Products marked 'Remove from cart'");
    }

    @When("User removes product from cart")
    public void user_removes_product_from_cart() {
        shop().removeFirstProduct();
    }

    @Then("Cart should be empty")
    public void cart_should_be_empty() {
        Assert.assertEquals(shop().inCartCount(), 0, "Products still marked 'Remove from cart'");
    }

    @When("User opens the details of the first product")
    public void user_opens_the_details_of_the_first_product() {
        shop().openFirstProduct();
    }

    @Then("The product details page should show its name and price")
    public void the_product_details_page_should_show_its_name_and_price() {
        Assert.assertTrue(driver().getCurrentUrl().contains("product-details?id="),
                "Not on a product details page: " + driver().getCurrentUrl());
        String text = new WebDriverWait(driver(), Duration.ofSeconds(15))
                .until(d -> {
                    String t = shop().pageText();
                    return t.matches("(?s).*\\$\\d+\\.\\d{2}.*") ? t : null;
                });
        Assert.assertTrue(text.contains("Sample"), "Product name not shown on the details page");
    }

    @Then("Every product card should show a name, a price and an {string} button")
    public void every_product_card_should_show_a_name_a_price_and_button(String buttonText) {
        List<String> cards = shop().productCardTexts();
        Assert.assertTrue(cards.size() >= 1, "No product cards found");
        Assert.assertEquals(cards.size(), shop().productCount(), "One card per product link");
        for (String card : cards) {
            Assert.assertTrue(card.matches("(?s).*\\$\\d+\\.\\d{2}.*"), "No price on card: " + card);
            Assert.assertTrue(card.contains("Name"), "No product name on card: " + card);
            Assert.assertTrue(card.contains(buttonText), "No '" + buttonText + "' button on card: " + card);
        }
    }

    // ---------- menu navigation ----------

    @When("User opens each demo module from the menu")
    public void user_opens_each_demo_module_from_the_menu(DataTable modules) {
        String home = BaseTest.config.getProperty("url");
        for (List<String> row : modules.asLists()) {
            String label = row.get(0);
            String expectedInUrl = row.get(1);
            driver().get(home);
            String url = clickMenuItem(label);
            if (url == null || !url.contains(expectedInUrl)) {
                problems.add("'" + label + "' opened " + url + " (expected '" + expectedInUrl + "' in the URL)");
            } else {
                modulesOpened++;
            }
        }
    }

    @Then("Each module page should open")
    public void each_module_page_should_open() {
        Assert.assertTrue(problems.isEmpty(), "Menu problems: " + problems);
        Assert.assertTrue(modulesOpened > 0, "No module was opened");
    }

    /** Clicks a sidebar menu item by its visible label and returns the URL it opened (same or new tab). */
    private String clickMenuItem(String label) {
        WebDriver d = driver();
        String start = d.getCurrentUrl();
        Set<String> tabs = d.getWindowHandles();
        List<WebElement> items = d.findElements(By.xpath(
                "//*[@role='menuitem'][normalize-space()='" + label + "'] | //a[normalize-space()='" + label + "']"));
        if (items.isEmpty()) {
            return null;
        }
        WebElement item = items.get(0);
        ((JavascriptExecutor) d).executeScript("arguments[0].scrollIntoView({block:'center'});", item);
        ((JavascriptExecutor) d).executeScript("arguments[0].click();", item);
        try {
            new WebDriverWait(d, Duration.ofSeconds(15)).until(x ->
                    x.getWindowHandles().size() > tabs.size() || !x.getCurrentUrl().equals(start));
        } catch (Exception timeout) {
            return d.getCurrentUrl();
        }
        if (d.getWindowHandles().size() > tabs.size()) {
            String original = d.getWindowHandle();
            for (String handle : d.getWindowHandles()) {
                if (!tabs.contains(handle)) {
                    d.switchTo().window(handle);
                    String url = d.getCurrentUrl();
                    d.close();
                    d.switchTo().window(original);
                    return url;
                }
            }
        }
        return d.getCurrentUrl();
    }
}
