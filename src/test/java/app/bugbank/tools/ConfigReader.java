package app.bugbank.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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

    private static Properties loadProperties(String nameFolder, String nameProp) {
        Properties props = new Properties();
        Path filePath = Paths.get(DIR_PATH_PROPERTIES, nameFolder, nameProp + ".properties");
        if (Files.exists(filePath)) {
            try (FileInputStream file = new FileInputStream(filePath.toFile())) {
                props.load(file);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao carregar o arquivo de propriedades: " + filePath, e);
            }
        } else {
            throw new RuntimeException("Arquivo de propriedades não encontrado: " + filePath);
        }
        return props;
    }

    public static void setProperty(String nameFolder, String nameProp, String key, String value) {
        Properties props = loadProperties(nameFolder, nameProp);
        props.setProperty(key, value);
        saveProperties(nameFolder, nameProp, props);
    }

    private static void saveProperties(String nameFolder, String name, Properties properties) {
        Path filePath = Paths.get(DIR_PATH_PROPERTIES, nameFolder, name + ".properties");
        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            properties.store(outputStream, null);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar o arquivo: " + filePath, e);
        }
    }
}