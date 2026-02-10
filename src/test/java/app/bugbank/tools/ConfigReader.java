package app.bugbank.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties;

    static {
        try {
            FileInputStream file = new FileInputStream("src/test/resources/setuprun.properties");
            properties = new Properties();
            properties.load(file);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar config.properties");
        }
    }

    public static String get(String key) {
        return System.getProperty(key.toLowerCase(), properties.getProperty(key));
    }
}