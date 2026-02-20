package app.bugbank.widgets;

import app.bugbank.tools.AppLogger;
import app.bugbank.tools.JsonReader;
import org.openqa.selenium.*;

public class Element {

    public static void click(WebElement locator) {
        try {
            if (locator.isDisplayed() && locator.isEnabled()) {
                locator.click();
            } else {
                AppLogger.logWarning("O botão " + locator + " não recebeu um clique.");
            }
        } catch (InvalidElementStateException | NoSuchElementException | StaleElementReferenceException |
                 TimeoutException e) {
            throw new RuntimeException("[click] Erro na validação do elemento.", e);
        }
    }

    public static void jsClick(WebDriver driver, WebElement locator) {
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].click();", locator);
        } catch (InvalidElementStateException | NoSuchElementException | StaleElementReferenceException |
                 TimeoutException e) {
            throw new RuntimeException("[jse] Erro na validação do elemento.", e);
        }
    }

    public static void sendKeysJson(WebElement locator, String parentNode, String key) {
        try {
            if (locator.isDisplayed()) {
                locator.clear();
                locator.sendKeys(JsonReader.getDataJson(parentNode, key));
            }
        } catch (InvalidElementStateException | NoSuchElementException | StaleElementReferenceException |
                 TimeoutException e) {
            throw new RuntimeException("[Assert] Erro na validação do elemento.", e);
        }
    }
}