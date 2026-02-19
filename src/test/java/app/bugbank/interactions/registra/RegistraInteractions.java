package app.bugbank.interactions.registra;

import app.bugbank.pages.registra.RegistraPage;
import app.bugbank.tools.ConfigReader;
import app.bugbank.tools.ElementDataUtils;
import org.testng.Assert;

import static app.bugbank.widgets.Element.*;

public class RegistraInteractions extends RegistraPage {

    public void validaOUrlBase() {
        String urlBase = ConfigReader.get("BASE_URL");
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
        jsClick(driver, saldoContaToggle());
    }

    public void realizarCadastrar() {
        click(cadastrarButton());
    }

    public void armazenaDetalhesDaConta(String userProp) {
        switch (userProp.toLowerCase()) {
            case "as", "ow" -> ElementDataUtils.extractAccountDetails(
                    sucessoModalText(),
                    userProp.toLowerCase(),
                    "conta",
                    "digito"
            );
            default -> throw new RuntimeException("Usuário inválido: " + userProp);
        }
    }

    public void fecharModal() {
        click(fechaModalButton());
    }
}