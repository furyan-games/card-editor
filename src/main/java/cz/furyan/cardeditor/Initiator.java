package cz.furyan.cardeditor;

import cz.furyan.cardeditor.model.Action;
import cz.furyan.cardeditor.model.ConvertUtils;
import cz.furyan.cardeditor.model.Card;
import cz.furyan.cardeditor.model.Condition;
import cz.furyan.cardeditor.model.Effect;
import cz.furyan.cardeditor.pseudoenum.PseudoEnum;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static cz.furyan.cardeditor.pseudoenum.Enums.*;

public class Initiator {
    private static final int MAX_ACTIONS = 3;
    private static final int MAX_CONDITIONS = 8;
    private static final int MAX_ACTION_CONDITIONS = 3;
    private static final int MAX_ACTION_EFFECTS = 3;

    public Parent start(final Stage primaryStage, Card model) {
        Pane root = FxmlUtils.load("card.fxml");

        ((TextField)root.lookup("#title")).setText(model.getTitle());
        ((TextArea)root.lookup("#text")).setText(model.getText());
        ((TextField)root.lookup("#imageUrl")).setText(model.getImageUrl());

        Pane actions = (Pane) root.lookup("#actions");
        Pane conditions = (Pane) root.lookup("#conditions");
        Pane conditionsWrapper = (Pane) root.lookup("#conditionsWrapper");

        Button saveCard = (Button) root.lookup("#saveCard");
        saveCard.setOnMouseClicked(
                event -> Exporter.save(root)
        );

        Button newCard = (Button) root.lookup("#newCard");
        newCard.setOnMouseClicked(event -> {
            Card card = Importer.load(Main.class.getResource("/card.json"));
            primaryStage.getScene().setRoot(this.start(primaryStage, card));
        });

        Button loadCard = (Button) root.lookup("#loadCard");
        loadCard.setOnMouseClicked(event -> {
//            Card card = Importer.load(filename);
//            primaryStage.getScene().setRoot(this.start(primaryStage, card));
        });

        for (Condition conditionModel : model.getConditions()) {
            createCondition(conditions, conditionModel);
        }

        Button addCondition = (Button) conditionsWrapper.lookup("#addCondition");
        addCondition.setOnMouseClicked(event -> {
            if (conditions.getChildren().size() < MAX_CONDITIONS) {
                createCondition(conditions, Condition.EMPTY);
            }
        });

        for (Action actionModel : model.getActions()) {
            createAction(actions, actionModel);
        }

        Button addAction = (Button) root.lookup("#addAction");
        addAction.setOnMouseClicked(event -> {
            if (actions.getChildren().size() < MAX_ACTIONS) {
                createAction(actions, Action.EMPTY);
            }
        });

        return root;
    }

    private void createAction(Pane actions, Action model) {
        Parent action = FxmlUtils.load("action.fxml");
        FxmlUtils.enumCast(action.lookup("#type")).setValue(ActionTypes.ofCode(model.getType()));
        ((TextField)action.lookup("#text")).setText(model.getText());
        ((TextField)action.lookup("#effectDesc")).setText(model.getEffectDescription());

        actions.getChildren().add(action);
        final Pane conditions = (Pane) action.lookup("#conditions");
        for (Condition conditionModel : model.getConditions()) {
            createCondition(conditions, conditionModel);
        }

        final ChoiceBox<PseudoEnum.PseudoEnumEntry> actionType = FxmlUtils.enumCast(action.lookup("#type"));
        actionType.setConverter(ActionTypes.getConverter());
        actionType.setItems(FXCollections.observableList(ActionTypes.getValues()));

        Pane effects = (Pane) action.lookup("#effects");
        for (Effect effectModel : model.getEffects()) {
            createEffect(effects, effectModel);
        }

        Button remove = (Button) action.lookup("#remove");
        remove.setOnMouseClicked(event -> actions.getChildren().remove(action));

        Button addCondition = (Button) action.lookup("#addCondition");
        addCondition.setOnMouseClicked(event -> {
            if (conditions.getChildren().size() < MAX_ACTION_CONDITIONS) {
                createCondition(conditions, Condition.EMPTY);
            }
        });

        Button addEffect = (Button) action.lookup("#addEffect");
        addEffect.setOnMouseClicked(event -> {
            if (effects.getChildren().size() < MAX_ACTION_EFFECTS) {
                createEffect(effects, Effect.EMPTY);
            }
        });
    }

    private void createCondition(Pane conditions, Condition model) {
        Pane condition = FxmlUtils.load("condition.fxml");

        FxmlUtils.enumCast(condition.lookup("#stat")).setValue(Stats.ofCode(model.getStat()));
        FxmlUtils.enumCast(condition.lookup("#operator")).setValue(Operators.ofCode(model.getOperator()));
        ((TextField)condition.lookup("#falseThreshold")).setText(ConvertUtils.toText(model.getFalseThreshold()));
        ((TextField)condition.lookup("#trueThreshold")).setText(ConvertUtils.toText(model.getTrueThreshold()));

        ChoiceBox<PseudoEnum.PseudoEnumEntry> stat = FxmlUtils.enumCast(condition.lookup("#stat"));
        stat.setConverter(Stats.getConverter());
        stat.setItems(FXCollections.observableList(Stats.getValues()));

        ChoiceBox<PseudoEnum.PseudoEnumEntry> operator = FxmlUtils.enumCast(condition.lookup("#operator"));
        operator.setConverter(Operators.getConverter());
        operator.setItems(FXCollections.observableList(Operators.getValues()));

        Button removeButton = (Button) condition.lookup("#remove");
        removeButton.setOnMouseClicked(event ->
                conditions.getChildren().remove(condition)
        );
        conditions.getChildren().add(condition);
    }

    private void createEffect(Pane effects, Effect model) {
        Pane effect = FxmlUtils.load("effect.fxml");
        FxmlUtils.enumCast(effect.lookup("#stat")).setValue(Stats.ofCode(model.getStat()));
        FxmlUtils.enumCast(effect.lookup("#mode")).setValue(EffectModes.ofCode(model.getEffectMode()));
        ((TextField)effect.lookup("#value")).setText(ConvertUtils.toText(model.getValue()));

        ChoiceBox<PseudoEnum.PseudoEnumEntry> stat = FxmlUtils.enumCast(effect.lookup("#stat"));
        stat.setConverter(Stats.getConverter());
        stat.setItems(FXCollections.observableList(Stats.getValues()));

        ChoiceBox<PseudoEnum.PseudoEnumEntry> mode = FxmlUtils.enumCast(effect.lookup("#mode"));
        mode.setConverter(EffectModes.getConverter());
        mode.setItems(FXCollections.observableList(EffectModes.getValues()));

        Button removeButton = (Button) effect.lookup("#remove");
        removeButton.setOnMouseClicked(event ->
                effects.getChildren().remove(effect)
        );

        effects.getChildren().add(effect);
    }

}
