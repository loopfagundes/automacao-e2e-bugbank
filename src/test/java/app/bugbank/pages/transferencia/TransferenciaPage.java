package app.bugbank.pages.transferencia;

import app.bugbank.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TransferenciaPage extends BasePage {

    public WebElement transferenciaButton() {
        return toBeClickable(By.id("btn-TRANSFERÊNCIA"));
    }

    public WebElement numeroDaContaInput() {
        return toBeClickable(By.cssSelector("input[type='accountNumber']"));
    }

    public WebElement digitoInput() {
        return toBeClickable(By.cssSelector("input[type='digit']"));
    }

    public WebElement valorDaTransferenciaInput() {
        return toBeClickable(By.cssSelector("input[type='transferValue']"));
    }

    public WebElement descricaoInput() {
        return toBeClickable(By.cssSelector("input[type='description']"));
    }

    public WebElement transferirAgoraButton() {
        return toBeClickable(By.cssSelector("button[type='submit']"));
    }

    public WebElement transferenciaSucessoModalText() {
        return toBeClickable(By.id("modalText"));
    }

    public WebElement fechaModalButton() {
        return toBeClickable(By.id("btnCloseModal"));
    }

    public WebElement voltaMeuPerfilButton() {
        return toBeClickable(By.id("btnBack"));
    }

    public WebElement saldoDaContaText() {
        return toBeClickable(By.id("textBalance"));
    }

    public WebElement extratoButton() {
        return toBeClickable(By.id("btn-EXTRATO"));
    }

    public WebElement saldoDisponivelExtratoText() {
        return toBeClickable(By.id("textBalanceAvailable"));
    }

    public WebElement transferenciaEnviadaText() {
        return toBeClickable(By.xpath("//*[@class='bank-statement__Transaction-sc-7n8vh8-13 fUCxBP'][2]"));
    }

    public WebElement clicaSairParaLogout() {
        return toBeClickable(By.cssSelector("[class='home__ContainerLink-sc-1auj767-2 cCGrzy']"));
    }
}