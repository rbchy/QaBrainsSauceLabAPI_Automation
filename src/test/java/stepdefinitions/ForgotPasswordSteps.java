package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.ForgotPasswordPage;
import utils.BaseTest;

public class ForgotPasswordSteps {

    private ForgotPasswordPage page() {
        return new ForgotPasswordPage(BaseTest.getDriver());
    }

    @Given("User is on Forgot Password page")
    public void user_is_on_forgot_password_page() {
        String home = BaseTest.config.getProperty("url").replaceAll("/+$", "");
        BaseTest.getDriver().get(home + "/forgot-password");
    }

    @When("User enters registered email {string}")
    public void user_enters_registered_email(String email) {
        page().enterEmail(email);
    }

    @When("User enters unregistered email {string}")
    public void user_enters_unregistered_email(String email) {
        page().enterEmail(email);
    }

    @And("User clicks on Submit button")
    public void user_clicks_on_submit_button() {
        page().clickSubmit();
    }

    @Then("User should see success message {string}")
    public void user_should_see_success_message(String expected) {
        String actual = page().getSuccessMessage();
        Assert.assertFalse(actual.isEmpty(), "No success message was shown");
        Assert.assertTrue(actual.toLowerCase().contains(expected.toLowerCase())
                        || actual.toLowerCase().contains("success"),
                "Success message was '" + actual + "', expected it to contain '" + expected + "'");
    }

    @Then("User should see error message {string}")
    public void user_should_see_error_message(String expected) {
        String actual = page().getErrorMessage();
        Assert.assertTrue(actual.contains(expected),
                "Error message was '" + actual + "', expected it to contain '" + expected + "'");
    }
}
