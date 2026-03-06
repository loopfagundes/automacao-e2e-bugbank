package app.bugbank.pages.login;

import app.bugbank.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    public WebElement inputEmail() {
        return toBeClickable(By.xpath("//*[@id='__next'] //div[@class='card__login'] //input[@name='email']"));
    }

    public WebElement inputSenha() {
        return toBeClickable(By.xpath("//*[@id='__next'] //div[@class='card__login'] //input[@name='password']"));
    }

    public WebElement acessarButton() {
        return toBeClickable(By.xpath("//*[@id='__next'] //div[@class='card__login'] //button[@type='submit']"));
    }

    public WebElement bemVindoText() {
        return visibilityOf(By.cssSelector("[class='home__ContainerText-sc-1auj767-7 iDA-Ddb']"));
    }

    public WebElement nomeDoUsuarioLogadoText() {
        return visibilityOf(By.id("textName"));
    }

    public WebElement contaEDigitoDoUsuarioText() {
        return visibilityOf(By.id("textAccountNumber"));
    }

    public WebElement saldoDoUsuarioText() {
        return visibilityOf(By.id("textBalance"));
    }

    public WebElement sairButton() {
        return toBeClickable(By.id("btnExit"));
    }

    public WebElement telaDeLoginText() {
        return visibilityOf(By.cssSelector("[class='pages__Title-sc-1ee1f2s-4 cFmqIK']"));
    }
}