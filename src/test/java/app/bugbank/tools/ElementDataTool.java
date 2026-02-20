package app.bugbank.tools;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebElement;

public class ElementDataTool {

    public static String toReplaceAll(WebElement element) {
        String ignoreNumbers = element.getText();
        return ignoreNumbers.replaceAll("[0-9-]", "");
    }

    public static void extractAccountDetails(WebElement element, String nameProp, String numberAccount, String digit) {
        String accountNumber = element.getText();
        String[] numberSeparator = accountNumber.split("-");
        String number = numberSeparator[0].replaceAll("[^0-9]", "");
        String numberDigit = numberSeparator[1].replaceAll("[^0-9]", "");
        PropertiesManager.setProperty(nameProp, numberAccount, number);
        PropertiesManager.setProperty(nameProp, digit, numberDigit);
    }

    public static void fakeValue(WebElement element, String fileName, String key) {
        if (element == null) {
            throw new IllegalArgumentException("O elemento WebElement não pode ser null.");
        }
        int fakeCash = Faker.instance().number().numberBetween(0, 999);
        int fakeCent = Faker.instance().number().numberBetween(0, 99);
        String fakeValue = fakeCash + "." + fakeCent;
        element.sendKeys(fakeValue);
        PropertiesManager.setProperty(fileName, key, fakeValue);
    }

    public static void setText(WebElement element, String fileName, String key) {
        if (element == null) {
            throw new IllegalArgumentException("O elemento WebElement não pode ser null.");
        }
        String extractText = element.getText();
        PropertiesManager.setProperty(fileName, key, extractText);
    }

    public static String passwordGenerator() {
        String senha = PropertiesManager.getProperty("data", "senha");
        if (senha == null || senha.isBlank()) {
            senha = Faker.instance().number().digits(5) + "@";
            PropertiesManager.setProperty("data", "senha", senha);
        }
        return senha;
    }
}