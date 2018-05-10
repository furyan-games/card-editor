package cz.furyan.cardeditor.model;

import cz.furyan.cardeditor.pseudoenum.PseudoEnum;

import java.math.BigDecimal;

public class ConvertUtils {
    private ConvertUtils() {
    }

    public static BigDecimal toDecimal(String string) {
        return string != null && !string.isEmpty() ? new BigDecimal(string) : null;
    }

    public static String toText(Object obj) {
        return obj != null ? obj.toString() : null;
    }

    public static String toEnumCode(PseudoEnum.PseudoEnumEntry pseudoEnum) {
        return pseudoEnum != null ? pseudoEnum.code : null;
    }

}
