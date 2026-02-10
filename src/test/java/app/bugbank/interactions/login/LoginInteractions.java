package app.bugbank.interactions.login;

import app.bugbank.drivers.DriverManager;
import app.bugbank.pages.login.LoginPage;
import app.bugbank.tools.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginInteractions extends LoginPage {

    private final WebDriver driver;

    public LoginInteractions() {
        this.driver = DriverManager.getDriver();
    }

    public void validaOUrlBase() {
        String urlBase = ConfigReader.get("URL");
        Assert.assertEquals(driver.getCurrentUrl(), urlBase, "URL base em desacordo");
    }

    public void preencherInputDoEmail() {
        inputEmail().sendKeys("GOL!");
    }

    public void preencherInputDaSenha() {
        inputSenha().sendKeys("SENHAAAAAAAAA");
    }
}