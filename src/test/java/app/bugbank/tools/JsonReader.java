package app.bugbank.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonReader {

    private static final JsonNode rootNode;

    static {
        ObjectMapper mapper = new ObjectMapper();
        try {
            rootNode = mapper.readTree(new File("src/test/resources/data/testData.json"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Não foi possível ler o arquivo JSON.");
        }
    }

    /**
     * Retorna o valor de uma chave no JSON.
     * @param parentNode Chave pai (ex: "Login")
     * @param key Chave interna (ex: "Senha")
     * @return valor da chave como String
     */
    public static String getDataJson(String parentNode, String key) {
        if (rootNode.has(parentNode) && rootNode.get(parentNode).has(key)) {
            return rootNode.get(parentNode).get(key).asText();
        }
        throw new RuntimeException("Chave '" + key + "' não encontrada em '" + parentNode + "'");
    }
}