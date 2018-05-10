package cz.furyan.cardeditor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.furyan.cardeditor.model.Action;
import cz.furyan.cardeditor.model.Card;
import cz.furyan.cardeditor.model.Condition;
import cz.furyan.cardeditor.model.Effect;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Exporter {

    static void save(Node root) {
        Card card = new Card.Builder()
                .fxTitle((TextField)root.lookup("#title"))
                .fxText((TextArea)root.lookup("#text"))
                .fxImageUrl((TextField)root.lookup("#imageUrl"))
                .withConditions(exportConditions(root))
                .withActions(exportActions(root))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        final String cardJson;
        try {
            cardJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(card);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Couldn't map to JSON", e);
        }
        try {
            FileWriter writer = new FileWriter(card.getTitle() + ".json");
            writer.write(cardJson);
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException("Couldn't write to file", e);
        }
    }

    private static List<Condition> exportConditions(Node root) {
        List<Condition> conditions = new ArrayList<>();
        for (Node cond : ((Pane)root.lookup("#conditions")).getChildren()) {
            Condition condition = new Condition.Builder()
                    .fxStat(FxmlUtils.enumCast(cond.lookup("#stat")))
                    .fxOperator(FxmlUtils.enumCast(cond.lookup("#operator")))
                    .fxFalseThreshold((TextField)cond.lookup("#falseThreshold"))
                    .fxTrueThreshold((TextField)cond.lookup("#trueThreshold"))
                    .build();
            conditions.add(condition);
        }
        return conditions;
    }

    private static List<Action> exportActions(Node root) {
        List<Action> actions = new ArrayList<>();
        for (Node act : ((Pane)root.lookup("#actions")).getChildren()) {
            if (!(act instanceof Pane)) {
                continue;
            }
            List<Condition> actConditions = new ArrayList<>();
            for (Node cond : ((Pane)act.lookup("#conditions")).getChildren()) {
                Condition condition = new Condition.Builder()
                        .fxStat(FxmlUtils.enumCast(cond.lookup("#stat")))
                        .fxOperator(FxmlUtils.enumCast(cond.lookup("#operator")))
                        .fxFalseThreshold((TextField) cond.lookup("#falseThreshold"))
                        .fxTrueThreshold((TextField) cond.lookup("#trueThreshold"))
                        .build();
                actConditions.add(condition);
            }
            List<Effect> actEffects = new ArrayList<>();
            for (Node eff : ((Pane)act.lookup("#effects")).getChildren()) {
                Effect effect = new Effect.Builder()
                        .fxStat(FxmlUtils.enumCast(eff.lookup("#stat")))
                        .fxEffectMode(FxmlUtils.enumCast(eff.lookup("#mode")))
                        .fxValue((TextField) eff.lookup("#value"))
                        .build();
                actEffects.add(effect);
            }
            Action action = new Action.Builder()
                    .fxType(FxmlUtils.enumCast(act.lookup("#type")))
                    .fxText((TextField) act.lookup("#text"))
                    .withEffects(actEffects)
                    .fxEffectDescription((TextField) act.lookup("#effectDesc"))
                    .withConditions(actConditions)
                    .build();
            actions.add(action);
        }
        return actions;
    }
}
