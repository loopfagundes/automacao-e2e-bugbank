package app.bugbank.steps.registra;

import app.bugbank.interactions.registra.RegistraInteractions;
import io.cucumber.java.pt.Entao;

public class RegistraSteps {

    RegistraInteractions registraInteractions = new RegistraInteractions();

    @Entao("que acesso o site do BugBank")
    public void queAcessoOSiteDoBugBank() {
        registraInteractions.validaOUrlBase();
    }

    @Entao("que clico o botao registrar")
    public void queClicoOBotaoRegistar() {
        registraInteractions.fazerRegistrar();
    }

    @Entao("preencho o campo do email {string}")
    public void preenchoOCampoDoEmail(String usuario) {
        registraInteractions.preencherOEmail(usuario);
    }

    @Entao("preencho o campo do nome {string}")
    public void preenchoOCampoDoNome(String usuario) {
        registraInteractions.preencherONome(usuario);
    }

    @Entao("preencho o campo da senha {string}")
    public void preenchoOCampoDaSenha(String usuario) {
        registraInteractions.preencherASenha(usuario);
    }

    @Entao("preencho novamente a senha no campo de confirmação de senha {string}")
    public void preenchoNovamenteASenha(String usuario) {
        registraInteractions.preencherConfirmacaoDaSenha(usuario);
    }

    @Entao("clico o toggle para criar a conta com saldo")
    public void clicoOtoggleParaCriarAContaComSaldo() {
        registraInteractions.clicarToggleOSaldoDaConta();
    }

    @Entao("clico no botao cadastrar")
    public void clicoNoBotaoCadastrar() {
        registraInteractions.realizarCadastrar();
    }

    @Entao("armazeno a conta {string} do numero da conta e do digito")
    public void armazenoONumeroDaContaEDigito(String userProp) {
        registraInteractions.armazenaDetalhesDaConta(userProp);
    }

    @Entao("fecho o modal")
    public void fechoOModal() {
        registraInteractions.fecharModal();
    }
}