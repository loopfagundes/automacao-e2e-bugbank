package app.bugbank.interactions.recebimento;

import app.bugbank.pages.recebimento.RecebimentoPage;
import app.bugbank.tools.PropertiesManager;
import org.testng.Assert;

public class RecebimentoInteractions extends RecebimentoPage {

    public void validarOSaldoDisponivelAtualizado() {
        String saldoAtualizado = saldoDisponivelExtratoText().getText();
        PropertiesManager.setProperty("ow", "saldoAtualizado", saldoAtualizado);
        String mensagem = "O saldo atualizado no extrato não está visível";
        Assert.assertTrue(saldoDisponivelExtratoText().isDisplayed(), mensagem);
    }

    public void validarTransferenciaRecebida() {
        String saldoTransferenciaRecebida = transferenciaRecebidaText().getText();
        PropertiesManager.setProperty("ow", "saldoTransferenciaRecebida", saldoTransferenciaRecebida);
        String mensagem = "O saldo do extrato da transferência recebida não está visível";
        Assert.assertTrue(transferenciaRecebidaText().isDisplayed(), mensagem);
    }
}