package app.bugbank.interactions.login;

import app.bugbank.pages.login.LoginPage;
import app.bugbank.tools.GlobalTool;
import app.bugbank.tools.JsonReader;
import app.bugbank.tools.PropertiesManager;
import org.testng.Assert;

import static app.bugbank.tools.GlobalTool.*;
import static app.bugbank.widgets.Element.*;

public class LoginInteractions extends LoginPage {

    public void preencherInputDoEmail(String usuario) {
        sendKeysJson(inputEmail(), "Login", usuario);
    }

    public void preencherInputDaSenha() {
        String senha = PropertiesManager.getProperty("data", "senha");
        inputSenha().clear();
        inputSenha().sendKeys(senha);
    }

    public void clicaParaAcessar() {
        click(acessarButton());
    }

    public void validaTelaLogadoBemVindo() {
        String bemVindoAtual = bemVindoText().getText();
        String bemVindoEsperado = JsonReader.getDataJson("Mensagem", "BemVindo");
        String mensagem = "O Texto não esta correto";
        Assert.assertTrue(bemVindoAtual.contains(bemVindoEsperado), mensagem);
    }

    public void validaNomeDoUsuarioLogado(String usuario) {
        String usuarioKey = usuario.toLowerCase();
        GlobalTool.setText(nomeDoUsuarioLogadoText(), usuarioKey, "nome");
        String nomeEsperado = PropertiesManager.getProperty(usuarioKey, "nome");
        String nomeNaTela = nomeDoUsuarioLogadoText().getText();
        Assert.assertEquals(nomeNaTela, nomeEsperado, "O nome do usuário não está correto");
    }

    public void validaAContaEDigitoDoUsuario() {
        String mensagem = "Conta e o digito não estão visíveis na tela";
        Assert.assertTrue(contaEDigitoDoUsuarioText().isDisplayed(), mensagem);
    }

    public void validaSaldoDoUsuario(String usuario) {
        String user = usuario.toLowerCase();
        String saldoDaConta = saldoDoUsuarioText().getText();
        String mensagem = "O saldo não esta visível na tela";
        Assert.assertTrue(saldoDoUsuarioText().isDisplayed(), mensagem);
        PropertiesManager.setProperty(user, "saldoDaConta", saldoDaConta);
    }

    public void validaSaldoAnterior() {
        String saldoDaConta = saldoDoUsuarioText().getText();
        String mensagem = "[Usuario - OW] >> O saldo anterior não esta visível na tela";
        Assert.assertTrue(saldoDoUsuarioText().isDisplayed(), mensagem);
        PropertiesManager.setProperty("ow", "SaldoAnterior", saldoDaConta);
    }

    public void clicaSairDaTelaDeLogin() {
        click(sairButton());
    }

    public void validaATelaDeLogin() {
        String telaDeLoginTexto = telaDeLoginText().getText();
        String telaDeLoginEsperado = JsonReader.getDataJson("Mensagem", "TelaDeLogin");
        String mensagem = "A pagina de login não esta correta";
        Assert.assertEquals(telaDeLoginTexto, telaDeLoginEsperado, mensagem);
    }
}