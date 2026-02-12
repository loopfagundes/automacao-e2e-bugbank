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

    private static final String GRID_URL = System.getProperty("grid.url", ConfigReader.get("GRID_URL"));
    private static final String[] WINDOW_SIZE = ConfigReader.get("WINDOW_SIZE_ACTIVE").split("x");
    private static final int WINDOW_WIDTH = Integer.parseInt(WINDOW_SIZE[0]);
    private static final int WINDOW_HEIGHT = Integer.parseInt(WINDOW_SIZE[1]);

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
        return createDriverWithOptionalGrid(options, "Chrome", headless, useGrid);
    }

    private static WebDriver createFirefox(boolean headless, boolean useGrid) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        configureOptions(options, headless);
        return createDriverWithOptionalGrid(options, "Firefox", headless, useGrid);
    }

    private static WebDriver createEdge(boolean headless, boolean useGrid) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        configureOptions(options, headless);
        return createDriverWithOptionalGrid(options, "Edge", headless, useGrid);
    }

    private static void configureOptions(Object options, boolean headless) {
        if (options instanceof ChromeOptions chrome) {
            if (headless) chrome.addArguments(
                    "--headless=new",
                    "--window-size=" + WINDOW_WIDTH + "," + WINDOW_HEIGHT);
            chrome.addArguments(
                    "--start-maximized",
                    "--disable-notifications",
                    "--no-sandbox",
                    "--disable-dev-shm-usage");
        } else if (options instanceof FirefoxOptions firefox) {
            if (headless) firefox.addArguments(
                    "-headless",
                    "--window-size=" + WINDOW_WIDTH + "," + WINDOW_HEIGHT);
            firefox.addArguments("--start-maximized");
        } else if (options instanceof EdgeOptions edge) {
            if (headless) edge.addArguments(
                    "--headless=new",
                    "--window-size=" + WINDOW_WIDTH + "," + WINDOW_HEIGHT);
            edge.addArguments("--start-maximized");
        }
    }

    private static WebDriver createDriverWithOptionalGrid(Object options, String navegador, boolean headless, boolean useGrid) {
        WebDriver driver;
        boolean usingGrid = useGrid && GRID_URL != null && !GRID_URL.isEmpty();

        if (usingGrid) {
            try {
                driver = new RemoteWebDriver(new URL(GRID_URL), (org.openqa.selenium.Capabilities) options);
            } catch (MalformedURLException e) {
                throw new RuntimeException("URL do Grid inválida: " + GRID_URL, e);
            }
        } else {
            if (options instanceof ChromeOptions chrome) driver = new ChromeDriver(chrome);
            else if (options instanceof FirefoxOptions firefox) driver = new FirefoxDriver(firefox);
            else if (options instanceof EdgeOptions edge) driver = new EdgeDriver(edge);
            else throw new RuntimeException("Opções de navegador não suportadas para execução local.");
        }

        System.out.printf(
                "[INFO] Rodando %s %s (headless=%s, resolução=%dx%d)%n",
                navegador,
                usingGrid ? "no Grid" : "local",
                headless,
                WINDOW_WIDTH,
                WINDOW_HEIGHT
        );

        return driver;
    }
}