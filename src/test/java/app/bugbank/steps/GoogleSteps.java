package app.bugbank.steps;

import app.bugbank.core.DriverFactory;
import io.cucumber.java.pt.Entao;

public class GoogleSteps {

    @Entao("que acesso o site do Google")
    public void queAcessoOSiteDoGoole() {
        DriverFactory.getDriver().get("https://www.google.com");
    }
}