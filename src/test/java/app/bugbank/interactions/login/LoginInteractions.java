package app.bugbank.interactions.login;

import app.bugbank.pages.login.LoginPage;

import static app.bugbank.widgets.Element.*;

public class LoginInteractions extends LoginPage {

    public void preencherInputDoEmail(String usuario) {
        sendKeysJson(inputEmail(), "Login", usuario);
    }

    public void preencherInputDaSenha() {
        sendKeysJson(inputSenha(), "Login", "Senha");
    }

    public void clicaParaAcessar() {
        click(acessarButton());
    }

    public void validaTelaLogadobemVindo() {
        System.out.println(">>>>>>> BEM VINDO >" + bemVindoText().getText());
    }

    public void validaNomeDoUsuarioLogado() {
        System.out.println(">>>>>>>> NOME > " + nomeDoUsuarioLogadoText().getText());
    }

    public void validaAContaEDigitalDoUsuario() {
        System.out.println(">>>>>>>> CONTA E DIGITAL > " + contaEDigitalDoUsuarioText().getText());
    }

    public void validaSaldoDoUsuario() {
        System.out.println(">>>>>>>> SALDO > " + saldoDoUsuarioText().getText());
    }

    public void clicaSairDaTelaDeLogin() {
        click(sairButton());
    }

    public void validaATelaDeLogin() {
        System.out.println(">>>>>>>>> LOGIN > " + telaDeLoginText().getText());
    }
}