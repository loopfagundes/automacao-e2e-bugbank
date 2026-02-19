package app.bugbank.pages.registra;

import app.bugbank.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class RegistraPage extends BasePage {

    public WebElement registrarButton() {
        return toBeClickable(By.cssSelector("[class='style__ContainerButton-sc-1wsixal-0 ihdmxA button__child']"));
    }

    public WebElement emailInput() {
        return toBeClickable(By.xpath("//*[@id='__next'] //div[@class='card__register'] //input[@name='email']"));
    }

    public WebElement nomeInput() {
        return toBeClickable(By.cssSelector("input[type='name']"));
    }

    public WebElement senhaInput() {
        return toBeClickable(By.xpath("//*[@id='__next'] //div[@class='card__register'] //input[@name='password']"));
    }

    public WebElement confirmacaoSenhaInput() {
        return toBeClickable(By.xpath("//input[@name='passwordConfirmation']"));
    }

    public WebElement saldoContaToggle() {
        return toBeClickable(By.cssSelector("[class='styles__Span-sc-1pngcbh-2 fLTrsw']"));
    }

    public WebElement cadastrarButton() {
        return toBeClickable(By.xpath("//*[@id='__next'] //div[@class='card__register'] //button[@type='submit']"));
    }

    public WebElement sucessoModalText() {
        return visibilityOf(By.id("modalText"));
    }

    public WebElement fechaModalButton() {
        return toBeClickable(By.id("btnCloseModal"));
    }
}