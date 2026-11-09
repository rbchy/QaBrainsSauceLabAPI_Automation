package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.BaseTest;

import java.time.Duration;

public class CommonSteps {

    private WebDriver driver() {
        return BaseTest.getDriver();
    }

    private String homeUrl() {
        return BaseTest.config.getProperty("url");
    }

    @Given("User launches the application")
    public void user_launches_the_application() {
        driver().get(homeUrl());
    }

    @Given("User is on homepage")
    public void user_is_on_homepage() {
        if (!driver().getCurrentUrl().contains("practice.qabrains.com")) {
            driver().get(homeUrl());
        }
        Assert.assertTrue(driver().getCurrentUrl().contains("practice.qabrains.com"),
                "Expected the QA Brains home page but was on " + driver().getCurrentUrl());
    }

    @Then("Home page should load successfully")
    public void home_page_should_load_successfully() {
        new WebDriverWait(driver(), Duration.ofSeconds(20)).until(
                d -> "complete".equals(((org.openqa.selenium.JavascriptExecutor) d)
                        .executeScript("return document.readyState")));
        Assert.assertFalse(driver().getTitle().isBlank(), "Home page has no title");
        Assert.assertTrue(new WebDriverWait(driver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body"))).isDisplayed());
        Assert.assertTrue(driver().getCurrentUrl().contains("practice.qabrains.com"),
                "Home page URL: " + driver().getCurrentUrl());
    }
}
