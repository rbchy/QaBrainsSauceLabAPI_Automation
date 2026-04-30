package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchPage {

    WebDriver driver;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
    }

    By searchBox = By.id("small-searchterms");
    By searchBtn = By.cssSelector("input[value='Search']");

    public void searchItem(String item) {
        driver.findElement(searchBox).sendKeys(item);
        driver.findElement(searchBtn).click();
    }
}
