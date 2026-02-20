package app.bugbank.steps.hooks;

import app.bugbank.drivers.DriverFactory;
import app.bugbank.drivers.DriverManager;
import app.bugbank.tools.ConfigReader;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hook {

    @Before
    public void setup() {
        if (DriverManager.getDriver() == null) {
            DriverManager.setDriver(DriverFactory.createDriver());
            DriverManager.getDriver().get(ConfigReader.get("BASE_URL"));
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Erro");
        }
    }

    @AfterAll
    public static void tearDownAll() {
        DriverManager.quitDriver();
    }
}
