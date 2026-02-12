package app.bugbank.interactions.login;

import app.bugbank.pages.login.LoginPage;

public class LoginInteractions extends LoginPage {

    public void preencherInputDoEmail() {
        inputEmail().sendKeys("GOL!");
    }

    public void preencherInputDaSenha() {
        inputSenha().sendKeys("SENHAAAAAAAAA");
    }
}