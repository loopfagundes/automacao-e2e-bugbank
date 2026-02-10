package app.bugbank.steps.login;

import app.bugbank.interactions.login.LoginInteractions;
import io.cucumber.java.pt.Entao;

public class LoginSteps {

    LoginInteractions loginInteractions = new LoginInteractions();

    @Entao("que acesso o site do BugBank")
    public void queAcessoOSiteDoBugBank() {
        loginInteractions.validaOUrlBase();
    }

    @Entao("preencho o campo do email")
    public void preenchoOCampoDoEmail() {
        loginInteractions.preencherInputDoEmail();
    }

    @Entao("preencho o campo da senha")
    public void preenchoOCampoDaSenha() {
        loginInteractions.preencherInputDaSenha();
    }
}