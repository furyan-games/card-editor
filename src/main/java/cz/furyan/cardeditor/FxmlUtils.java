package cz.furyan.cardeditor;

import cz.furyan.cardeditor.pseudoenum.PseudoEnum;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class FxmlUtils {
    private FxmlUtils() {
    }

    public static Pane load(String resource) {
        try {
            URL res = FxmlUtils.class.getResource("/layout/"  + resource);
            return (Pane) FXMLLoader.load(res);
        } catch (IOException e) {
            throw new EditorException("Couldn't load FXML resource", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static ChoiceBox<PseudoEnum.PseudoEnumEntry> enumCast(Object obj) {
        return (ChoiceBox<PseudoEnum.PseudoEnumEntry>) obj;
    }

}
