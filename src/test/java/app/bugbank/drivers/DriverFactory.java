package app.bugbank.drivers;

import app.bugbank.tools.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static final String GRID_URL = System.getProperty("grid.url", ConfigReader.get("GRID_URL"));
    private static final String[] WINDOW_SIZE = ConfigReader.get("WINDOW_SIZE_ACTIVE").split("x");
    private static final int WINDOW_WIDTH = Integer.parseInt(WINDOW_SIZE[0]);
    private static final int WINDOW_HEIGHT = Integer.parseInt(WINDOW_SIZE[1]);

    public static WebDriver createDriver() {
        String browser = ConfigReader.get("NAVEGADOR").toLowerCase();
        boolean headless = Boolean.parseBoolean(ConfigReader.get("HEADLESS"));
        boolean useGrid = Boolean.parseBoolean(ConfigReader.get("USE_GRID"));

        return switch (browser) {
            case "chrome" -> createChrome(headless, useGrid);
            case "firefox" -> createFirefox(headless, useGrid);
            case "edge" -> createEdge(headless, useGrid);
            default -> throw new RuntimeException("Navegador inválido.");
        };
    }

    private static WebDriver createChrome(boolean headless, boolean useGrid) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=" + WINDOW_WIDTH + "," + WINDOW_HEIGHT);
        }
        options.addArguments(
                "--disable-notifications",
                "--no-sandbox",
                "--disable-dev-shm-usage"
        );
        WebDriver driver = createDriverWithOptionalGrid(options, useGrid);
        if (!headless) {
            driver.manage().window().maximize();
        }
        log("Chrome", headless, useGrid);
        return driver;
    }

    private static WebDriver createFirefox(boolean headless, boolean useGrid) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        WebDriver driver = createDriverWithOptionalGrid(options, useGrid);
        driver.manage().window().setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        log("Firefox", headless, useGrid);
        return driver;
    }

    private static WebDriver createEdge(boolean headless, boolean useGrid) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=" + WINDOW_WIDTH + "," + WINDOW_HEIGHT);
        }
        WebDriver driver = createDriverWithOptionalGrid(options, useGrid);
        if (!headless) {
            driver.manage().window().maximize();
        }
        log("Edge", headless, useGrid);
        return driver;
    }

    private static WebDriver createDriverWithOptionalGrid(org.openqa.selenium.Capabilities options, boolean useGrid) {
        boolean usingGrid = useGrid && GRID_URL != null && !GRID_URL.isEmpty();
        if (usingGrid) {
            try {
                return new RemoteWebDriver(new URL(GRID_URL), options);
            } catch (MalformedURLException e) {
                throw new RuntimeException("URL do Grid inválida: " + GRID_URL, e);
            }
        }

        return switch (options) {
            case ChromeOptions chrome -> new ChromeDriver(chrome);
            case FirefoxOptions firefox -> new FirefoxDriver(firefox);
            case EdgeOptions edge -> new EdgeDriver(edge);
            default -> throw new RuntimeException("Opções não suportadas.");
        };
    }

    private static void log(String browser, boolean headless, boolean useGrid) {
        System.out.printf("[INFO] Rodando %s %s (headless=%s, resolução=%dx%d)%n",
                browser,
                useGrid ? "no Grid" : "local",
                headless,
                WINDOW_WIDTH,
                WINDOW_HEIGHT
        );
    }
}