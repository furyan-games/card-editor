package cz.furyan.cardeditor.pseudoenum;

import javafx.util.StringConverter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class PseudoEnum {
    private final List<PseudoEnumEntry> values;
    private final Map<String, PseudoEnumEntry> codeMap;
    private final Map<String, PseudoEnumEntry> textMap;
    private final PseudoEnumConverter converter;

    public PseudoEnum(List<PseudoEnumEntry> values, Map<String, PseudoEnumEntry> codeMap, Map<String, PseudoEnumEntry> textMap) {
        this.values = values;
        this.codeMap = codeMap;
        this.textMap = textMap;
        this.converter = new PseudoEnumConverter(textMap);
    }

    public static PseudoEnum load(String filename) {
        BufferedReader bReader = new BufferedReader(new InputStreamReader(
                ClassLoader.getSystemResourceAsStream(filename)));
        Set<String> namesUsed = new HashSet<>();
        List<PseudoEnumEntry> entries = bReader.lines()
                .map(l -> l.split(";"))
                .map(arr -> new PseudoEnumEntry(arr[0], arr[1]))
                .collect(Collectors.toList());
        for (PseudoEnumEntry entry : entries) {
            if (!namesUsed.add(entry.text)) {
                throw new IllegalStateException("Two stats have the same name!");
            }
        }
        return new PseudoEnum(
                Collections.unmodifiableList(entries),
                Collections.unmodifiableMap(entries.stream().collect(Collectors.toMap(s -> s.code, s -> s))),
                Collections.unmodifiableMap(entries.stream().collect(Collectors.toMap(s -> s.text, s -> s)))
        );
    }

    public List<PseudoEnumEntry> getValues() {
        return values;
    }

    public PseudoEnumEntry ofCode(String code) {
        return codeMap.get(code);
    }

    public PseudoEnumEntry ofText(String text) {
        return textMap.get(text);
    }

    public PseudoEnumConverter getConverter() {
        return converter;
    }

    public static class PseudoEnumEntry {
        public final String code;
        public final String text;

        public PseudoEnumEntry(String code, String text) {
            this.code = code;
            this.text = text;
        }

    }

    public static class PseudoEnumConverter extends StringConverter<PseudoEnumEntry> {
        private final Map<String, PseudoEnumEntry> textMapping;

        public PseudoEnumConverter(Map<String, PseudoEnumEntry> textMapping) {
            this.textMapping = textMapping;
        }

        @Override
        public String toString(PseudoEnumEntry object) {
            return object != null ? object.text : null;
        }

        @Override
        public PseudoEnumEntry fromString(String string) {
            return textMapping.get(string);
        }
    }
}
