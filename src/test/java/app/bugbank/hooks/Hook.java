package app.bugbank.hooks;

import app.bugbank.core.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hook {
    @Before
    public void setup() {
        DriverFactory.getDriver();
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
