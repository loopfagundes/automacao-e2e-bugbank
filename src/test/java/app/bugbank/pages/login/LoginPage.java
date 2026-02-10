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
}