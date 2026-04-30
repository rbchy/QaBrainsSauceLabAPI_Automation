package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions"},
        plugin = {"pretty", 
        		"html:target/cucumber-reports.html",
        		"json:target/cucumber-reports/signup.json"
        		},
        		tags = "@smoke or @regression",
       
        		monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}