package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = "src/test/java/features",
    glue = {"stepdefinitions"},
    plugin = {
        "pretty",
        "html:test-output/reports/cucumber.html",
        "json:test-output/reports/cucumber.json",
        "junit:test-output/reports/cucumber.xml",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        "rerun:test-output/reports/failed_scenarios.txt"
    },
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
