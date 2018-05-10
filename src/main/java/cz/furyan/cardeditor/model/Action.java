package cz.furyan.cardeditor.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.furyan.cardeditor.pseudoenum.PseudoEnum;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonDeserialize(builder = Action.Builder.class)
public class Action {
    public static final Action EMPTY = new Action.Builder().build();

    private String type;
    private String text;
    private List<Condition> conditions;
    private String effectDescription;
    private List<Effect> effects;

    private Action(Builder b) {
        this.type = b.type;
        this.text = b.text;
        this.conditions = b.conditions;
        this.effectDescription = b.effectDescription;
        this.effects = b.effects;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public String getEffectDescription() {
        return effectDescription;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public static class Builder {
        private String type;
        private String text;
        private List<Condition> conditions = Collections.emptyList();
        private String effectDescription;
        private List<Effect> effects = Collections.emptyList();

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder fxType(ChoiceBox<PseudoEnum.PseudoEnumEntry> type) {
            this.type = ConvertUtils.toEnumCode(type.getValue());
            return this;
        }

        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        public Builder fxText(TextField text) {
            this.text = text.getText();
            return this;
        }

        public Builder withConditions(List<Condition> conditions) {
            this.conditions = Collections.unmodifiableList(new ArrayList<>(conditions));
            return this;
        }

        public Builder withEffectDescription(String effectDescription) {
            this.effectDescription = effectDescription;
            return this;
        }

        public Builder fxEffectDescription(TextField effectDescription) {
            this.effectDescription = effectDescription.getText();
            return this;
        }

        public Builder withEffects(List<Effect> effects) {
            this.effects = Collections.unmodifiableList(new ArrayList<>(effects));
            return this;
        }

        public Action build() {
            return new Action(this);
        }
    }

}
