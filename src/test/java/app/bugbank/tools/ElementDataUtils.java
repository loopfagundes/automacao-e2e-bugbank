package app.bugbank.tools;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebElement;

public class ElementDataUtils {

    public static String toReplaceAll(WebElement element) {
        String ignoreNumbers = element.getText();
        return ignoreNumbers.replaceAll("[0-9-]", "");
    }

    public static void extractAccountDetails(WebElement element, String nameProp, String numberAccount, String digit) {
        String accountNumber = element.getText();
        String[] numberSeparator = accountNumber.split("-");
        String number = numberSeparator[0].replaceAll("[^0-9]", "");
        String numberDigit = numberSeparator[1].replaceAll("[^0-9]", "");
        PropertiesManager.setProperty("data", nameProp, numberAccount, number);
        PropertiesManager.setProperty("data", nameProp, digit, numberDigit);
    }

    public static void fakeValue(WebElement element, String nameFolder, String fileName, String key) {
        if (element == null) {
            throw new IllegalArgumentException("O elemento WebElement não pode ser null.");
        }
        int fakeCash = Faker.instance().number().numberBetween(0, 999);
        int fakeCent = Faker.instance().number().numberBetween(0, 99);
        String fakeValue = fakeCash + "." + fakeCent;
        element.sendKeys(fakeValue);
        PropertiesManager.setProperty(nameFolder, fileName, key, fakeValue);
    }

    public static void extractAndStore(WebElement element, String nameFolder, String fileName, String key) {
        if (element == null) {
            throw new IllegalArgumentException("O elemento WebElement não pode ser null.");
        }
        String extractText = element.getText();
        PropertiesManager.setProperty(nameFolder, fileName, key, extractText);
    }
}