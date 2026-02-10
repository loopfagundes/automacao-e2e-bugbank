package app.bugbank.pages;

import app.bugbank.drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final Duration MAX_DURATION = Duration.ofSeconds(15);

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, MAX_DURATION);
    }

    protected WebElement toBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement visibilityOf(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}