package cz.furyan.cardeditor;

import cz.furyan.cardeditor.model.Card;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Initiator init = new Initiator();
        Card card = Importer.load(Main.class.getResource("/card.json"));
        Parent root = init.start(primaryStage, card);
        Scene scene = new Scene(root, 1220, 650);
        scene.getStylesheets().add("/dark.css");
        primaryStage.setTitle("Editor karet");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
