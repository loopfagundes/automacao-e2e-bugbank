package app.bugbank.interactions.registra;

import app.bugbank.pages.registra.RegistraPage;
import app.bugbank.tools.ConfigReader;
import org.testng.Assert;

import static app.bugbank.widgets.Element.*;

public class RegistraInteractions extends RegistraPage {

    public void validaOUrlBase() {
        String urlBase = ConfigReader.get("URL");
        Assert.assertEquals(driver.getCurrentUrl(), urlBase, "URL base em desacordo");
    }

    public void fazerRegistrar() {
        click(registrarButton());
    }

    public void preencherOEmail(String usuario) {
        sendKeysJson(emailInput(), usuario, "Email");
    }

    public void preencherONome(String usuario) {
        sendKeysJson(nomeInput(), usuario, "Nome");
    }

    public void preencherASenha(String usuario) {
        sendKeysJson(senhaInput(), usuario, "Senha");
    }

    public void preencherConfirmacaoDaSenha(String usuario) {
        sendKeysJson(confirmacaoSenhaInput(), usuario, "ConfirmacaoSenha");

    }

    public void clicarToggleOSaldoDaConta() {
        actionsClick(driver, saldoContaToggle());
    }

    public void realizarCadastrar() {
        click(cadastrarButton());
    }

    public void fecharModal() {
        click(fechaModalButton());
    }
}