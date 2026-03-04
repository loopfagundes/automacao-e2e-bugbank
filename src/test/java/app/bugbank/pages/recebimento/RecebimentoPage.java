package app.bugbank.pages.recebimento;

import app.bugbank.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class RecebimentoPage extends BasePage {

    public WebElement saldoDisponivelExtratoText() {
        return toBeClickable(By.id("textBalanceAvailable"));
    }

    public WebElement transferenciaRecebidaText() {
        return toBeClickable(By.xpath("//*[@class='bank-statement__Transaction-sc-7n8vh8-13 fUCxBP'][2]"));
    }
}