package cz.furyan.cardeditor;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.furyan.cardeditor.model.Card;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Importer {

    public static Card load(URL url) {
        try (InputStream is = url.openStream()) {
            return load(is);
        } catch (IOException e) {
            throw new EditorException("Couldn't read card from file", e);
        }
    }

    public static Card load(String filename) {
        File file = new File(filename);
        try (FileInputStream fis = new FileInputStream(file))
        {
            return load(fis);
        } catch (IOException e) {
            throw new EditorException("Couldn't read card from file", e);
        }
    }

    private static Card load(InputStream is) throws IOException {
        try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.toString()))
        {
            scanner.useDelimiter("\\A");
            String cardString = scanner.hasNext() ? scanner.next() : "";
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(cardString, Card.class);
        }
    }
}
