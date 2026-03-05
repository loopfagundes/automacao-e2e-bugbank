package app.bugbank.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigReader {

    private static final String DIR_PATH_PROPERTIES = Paths.get(System.getProperty("user.dir"), "src", "test", "resources").toString();
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream file = new FileInputStream(Paths.get(DIR_PATH_PROPERTIES, "setuprun.properties").toString())) {
            properties.load(file);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar setuprun.properties", e);
        }
    }

    public static String get(String key) {
        return System.getProperty(key.toLowerCase(), properties.getProperty(key));
    }
}