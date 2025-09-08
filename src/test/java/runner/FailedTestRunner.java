package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "@test-output/reports/failed_scenarios.txt",
    glue = {"com.nopcommerce.tests.cucumber.stepdefinitions", "com.nopcommerce.tests.base"},
    plugin = {
        "pretty",
        "html:test-output/reports/cucumber-failed.html",
        "json:test-output/reports/cucumber-failed.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true
)
public class FailedTestRunner extends AbstractTestNGCucumberTests {
}

