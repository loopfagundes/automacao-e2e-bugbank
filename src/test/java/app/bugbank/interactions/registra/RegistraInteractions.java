package app.bugbank.interactions.registra;

import app.bugbank.pages.registra.RegistraPage;
import app.bugbank.tools.ConfigReader;
import app.bugbank.tools.ElementDataTool;
import app.bugbank.tools.JsonReader;
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

    public void preencherASenha() {
        String senha = ElementDataTool.passwordGenerator();
        senhaInput().clear();
        senhaInput().sendKeys(senha);
        confirmacaoSenhaInput().clear();
        confirmacaoSenhaInput().sendKeys(senha);
    }

    public void clicarToggleOSaldoDaConta() {
        jsClick(driver, saldoContaToggle());
    }

    public void realizarCadastrar() {
        click(cadastrarButton());
    }

    public void armazenaDetalhesDaConta(String userProp) {
        switch (userProp.toLowerCase()) {
            case "as", "ow" -> ElementDataTool.extractAccountDetails(
                    sucessoModalText(),
                    userProp.toLowerCase(),
                    "conta",
                    "digito"
            );
            default -> throw new RuntimeException("Usuário inválido: " + userProp);
        }
        validaCriarAContaComSucesso();
    }

    private void validaCriarAContaComSucesso() {
        String modalTexto = ElementDataTool.toReplaceAll(sucessoModalText()).trim();
        String contaCriada = JsonReader.getDataJson("Mensagem", "ContaCriadaSucesso");
        String mensagem = "Falha ao criar a conta";
        Assert.assertEquals(modalTexto, contaCriada, mensagem);
    }

    public void fecharModal() {
        click(fechaModalButton());
    }
}