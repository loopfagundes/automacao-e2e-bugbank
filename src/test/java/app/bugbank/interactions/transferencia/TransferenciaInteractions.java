package app.bugbank.interactions.transferencia;

import app.bugbank.pages.transferencia.TransferenciaPage;
import app.bugbank.tools.PropertiesManager;
import org.testng.Assert;

import static app.bugbank.tools.GlobalTool.*;
import static app.bugbank.tools.JsonReader.*;
import static app.bugbank.widgets.Element.*;

public class TransferenciaInteractions extends TransferenciaPage {

    public void clicaTransferencia() {
        click(transferenciaButton());
    }

    public void preenchaOsCamposContaEDigito(String usuario) {
        String user = usuario.toLowerCase();
        sendKeysProperty(numeroDaContaInput(), user, "conta");
        sendKeysProperty(digitoInput(), user, "digito");
    }

    public void inserirOValorDeTransferencia() {
        fakeValue(valorDaTransferenciaInput(), "as", "valorDeTransferencia");
    }

    public void preenchaCampoDeDescricao() {
        messageFake(descricaoInput(), "as");
    }

    public void realizaTransferir() {
        click(transferirAgoraButton());
    }

    public void validaTransferenciaComSucesso() {
        String mensagemAtual = transferenciaSucessoModalText().getText();
        String mensagemEsperado = getDataJson("Mensagem", "TransferenciaRealizadaComSucesso");
        Assert.assertEquals(mensagemAtual, mensagemEsperado, "A transferencia não foi realizada");
    }

    public void fechaOModalSucesso() {
        click(fechaModalButton());
    }

    public void clicaVoltarNaTelaDoMeuPerfil() {
        click(voltaMeuPerfilButton());
    }

    public void validaOSaldoDesconta() {
        String saldoDaContaDesconta = saldoDaContaText().getText();
        PropertiesManager.setProperty("as", "saldoDaContaDesconta", saldoDaContaDesconta);
    }

    public void clicaExtratoParaAcessar() {
        click(extratoButton());
    }

    public void validarOSaldoERegistroDaTransferenciaEnviada() {
        String saldoDisponivelExtrato = saldoDisponivelExtratoText().getText();
        PropertiesManager.setProperty("as", "saldoDisponivelExtrato", saldoDisponivelExtrato);
        String mensagemSaldoDisponivel = "O saldo disponível no extrato não está visível";
        Assert.assertTrue(saldoDisponivelExtratoText().isDisplayed(), mensagemSaldoDisponivel);
        validaTransferenciaEnviada();
    }

    private void validaTransferenciaEnviada() {
        String transferenciaEnviada = transferenciaEnviadaText().getText();
        PropertiesManager.setProperty("as", "transferenciaEnviada", transferenciaEnviada);
        String mensagemTransferenciaEnviada = "O movimento de transferencia de saldo não está visível";
        Assert.assertTrue(transferenciaEnviadaText().isDisplayed(), mensagemTransferenciaEnviada);
    }

    public void realizarLogout() {
        click(clicaSairParaLogout());
    }
}