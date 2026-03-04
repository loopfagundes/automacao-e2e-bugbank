package app.bugbank.runners;

import app.bugbank.drivers.DriverManager;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "app.bugbank.steps",
        tags = "@Regressivo",
        plugin = {
                "pretty",
                "summary",
                "html:reports/report.html",
                "json:reports/report.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class WebRunnerTest extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterSuite
    public static void tearDown() {
        DriverManager.quitDriver();
    }
}