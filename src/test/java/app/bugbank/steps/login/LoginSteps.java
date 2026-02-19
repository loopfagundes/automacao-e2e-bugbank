package app.bugbank.steps.login;

import app.bugbank.interactions.login.LoginInteractions;
import io.cucumber.java.pt.Entao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginSteps {

    private static final Logger log = LoggerFactory.getLogger(LoginSteps.class);
    LoginInteractions loginInteractions = new LoginInteractions();

    @Entao("que preencho o campo de e-mail com o usuário {string}")
    public void quePreenchoCampoDeEmailDoUsuario(String usuario) {
        loginInteractions.preencherInputDoEmail(usuario);
    }

    @Entao("preencho o campo de senha com o usuário")
    public void preenchoOCampoDeSenha() {
        loginInteractions.preencherInputDaSenha();
    }

    @Entao("clico no botão acessar")
    public void clicoNoBotaoAcessar() {
        loginInteractions.clicaParaAcessar();
    }

    @Entao("devo visualizar a tela de bem vindo")
    public void devoVisualizarATelaDeBemVindo() {
        loginInteractions.validaTelaLogadobemVindo();
    }

    @Entao("o nome do usuário deve ser exibido na tela")
    public void nomeDoUsuarioDeveSerExibidoNaTela() {
        loginInteractions.validaNomeDoUsuarioLogado();
    }

    @Entao("a conta e o dígito do usuario devem ser exibidos na tela")
    public void contaEDigitoDoUsuarioExibidosNaTela() {
        loginInteractions.validaAContaEDigitalDoUsuario();
    }

    @Entao("o saldo do usuário deve ser exibido na tela")
    public void saldoDoUsuarioDeveSerExibidoNaTela() {
        loginInteractions.validaSaldoDoUsuario();
    }

    @Entao("clico no botão sair")
    public void clicoNoBotaoSair() {
        loginInteractions.clicaSairDaTelaDeLogin();
    }

    @Entao("valido que estou a tela de login")
    public void validoQueEstouATelaDeLogin() {
        loginInteractions.validaATelaDeLogin();
    }
}