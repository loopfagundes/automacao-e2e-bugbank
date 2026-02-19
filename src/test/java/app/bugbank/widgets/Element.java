package app.bugbank.widgets;

import app.bugbank.tools.AppLogger;
import app.bugbank.tools.JsonReader;
import app.bugbank.tools.PropertiesManager;
import org.openqa.selenium.*;

public class Element {

    private static final String FOLDER_PATH = "data";

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

    public static void extractAccountDetails(WebElement element, String nameProp, String numberAccount, String digit) {
        String accountNumber = element.getText();
        String[] numberSeparator = accountNumber.split("-");
        String number = numberSeparator[0].replaceAll("[^0-9]", "");
        String numberDigit = numberSeparator[1].replaceAll("[^0-9]", "");
        PropertiesManager.setProperty(FOLDER_PATH, nameProp, numberAccount, number);
        PropertiesManager.setProperty(FOLDER_PATH, nameProp, digit, numberDigit);
    }
}