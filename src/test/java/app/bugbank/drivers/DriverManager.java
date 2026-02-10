package app.bugbank.drivers;

import app.bugbank.tools.AppLogger;
import org.openqa.selenium.WebDriver;

public class DriverManager {

    private static final ThreadLocal<WebDriver> THREAD_LOCAL = new ThreadLocal<>();

    public static void setDriver(WebDriver driver) {
        THREAD_LOCAL.set(driver);
    }

    public static WebDriver getDriver() {
        return THREAD_LOCAL.get();
    }

    public static void quitDriver() {
        WebDriver driver = THREAD_LOCAL.get();
        if (driver != null) {
            try {
                driver.manage().deleteAllCookies();
            } catch (Exception e) {
                AppLogger.logInfo("Erro ao limpar cookies: " + e.getMessage());
            }
            driver.quit();
            THREAD_LOCAL.remove();
        }
    }
}