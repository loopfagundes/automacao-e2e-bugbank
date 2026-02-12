package app.bugbank.drivers;

import app.bugbank.tools.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static final String URL_GRID = System.getProperty("grid.url", ConfigReader.get("URL_GRID"));

    public static WebDriver createDriver() {
        String navegador = ConfigReader.get("NAVEGADOR").toLowerCase();
        boolean headless = Boolean.parseBoolean(ConfigReader.get("HEADLESS").toLowerCase());
        boolean useGrid = Boolean.parseBoolean(ConfigReader.get("USE_GRID").toLowerCase());

        return switch (navegador) {
            case "chrome" -> createChrome(headless, useGrid);
            case "firefox" -> createFirefox(headless, useGrid);
            case "edge" -> createEdge(headless, useGrid);
            default -> throw new RuntimeException(
                    "Navegador inválido. Use: chrome | firefox | edge"
            );
        };
    }

    private static WebDriver createChrome(boolean headless, boolean useGrid) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        configureOptions(options, headless);
        return createDriverWithOptionalGrid(options, useGrid);
    }

    private static WebDriver createFirefox(boolean headless, boolean useGrid) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        configureOptions(options, headless);
        return createDriverWithOptionalGrid(options, useGrid);
    }

    private static WebDriver createEdge(boolean headless, boolean useGrid) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        configureOptions(options, headless);
        return createDriverWithOptionalGrid(options, useGrid);
    }

    private static void configureOptions(Object options, boolean headless) {
        if (options instanceof ChromeOptions chrome) {
            if (headless) chrome.addArguments(
                    "--headless=new",
                    "--window-size=1920,1080");
            chrome.addArguments(
                    "--start-maximized",
                    "--disable-notifications",
                    "--no-sandbox",
                    "--disable-dev-shm-usage");
        } else if (options instanceof FirefoxOptions firefox) {
            if (headless) firefox.addArguments(
                    "-headless",
                    "--window-size=1920,1080");
            firefox.addArguments("--start-maximized");
        } else if (options instanceof EdgeOptions edge) {
            if (headless) edge.addArguments(
                    "--headless=new",
                    "--window-size=1920,1080");
            edge.addArguments("--start-maximized");
        }
    }

    private static WebDriver createDriverWithOptionalGrid(Object options, boolean useGrid) {
        if (useGrid && URL_GRID != null && !URL_GRID.isEmpty()) {
            try {
                return new RemoteWebDriver(new URL(URL_GRID), (org.openqa.selenium.Capabilities) options);
            } catch (MalformedURLException e) {
                throw new RuntimeException("URL do Grid inválida: " + URL_GRID, e);
            }
        } else {
            if (options instanceof ChromeOptions chrome) return new ChromeDriver(chrome);
            if (options instanceof FirefoxOptions firefox) return new FirefoxDriver(firefox);
            if (options instanceof EdgeOptions edge) return new EdgeDriver(edge);
            throw new RuntimeException("Opções de navegador não suportadas para execução local.");
        }
    }
}