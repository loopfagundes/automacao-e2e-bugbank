package app.bugbank.tools;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;

public class PropertiesManager {

    private static final Path BASE_RESOURCES = Paths.get(System.getProperty("user.dir"), "src", "test", "resources");
    private static final String FOLDER_DATA = "data";

    private static Path resolveFile(String nameProp) {
        return BASE_RESOURCES.resolve(FOLDER_DATA).resolve(nameProp + ".properties");
    }

    private static Properties loadOrCreate(Path filePath) {
        try {
            Files.createDirectories(filePath.getParent());
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }
            Properties props = new Properties();
            try (InputStream in = Files.newInputStream(filePath)) {
                props.load(in);
            }
            return props;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar/criar properties: " + filePath, e);
        }
    }

    public static void setProperty(String nameProp, String key, String value) {
        Path filePath = resolveFile(nameProp);
        Properties props = loadOrCreate(filePath);
        props.setProperty(key, value);
        try (OutputStream out = Files.newOutputStream(filePath)) {
            props.store(out, null);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar properties: " + filePath, e);
        }
    }

    public static String getProperty(String nameProp, String key) {
        Path filePath = resolveFile(nameProp);
        Properties props = loadOrCreate(filePath);
        return props.getProperty(key);
    }
}