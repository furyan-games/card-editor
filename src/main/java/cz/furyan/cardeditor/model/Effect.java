package cz.furyan.cardeditor.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.furyan.cardeditor.pseudoenum.PseudoEnum;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

@JsonDeserialize(builder = Effect.Builder.class)
public class Effect {
    public static final Effect EMPTY = new Effect.Builder().build();

    private String stat;
    private String effectMode;
    private BigDecimal value;

    private Effect(Builder b) {
        this.stat = b.stat;
        this.effectMode = b.effectMode;
        this.value = b.value;
    }

    public String getStat() {
        return stat;
    }

    public String getEffectMode() {
        return effectMode;
    }

    public BigDecimal getValue() {
        return value;
    }

    public static class Builder {
        private String stat;
        private String effectMode;
        private BigDecimal value;

        public Builder withStat(String stat) {
            this.stat = stat;
            return this;
        }

        public Builder fxStat(ChoiceBox<PseudoEnum.PseudoEnumEntry> stat) {
            this.stat = ConvertUtils.toEnumCode(stat.getValue());
            return this;
        }

        public Builder withEffectMode(String effectMode) {
            this.effectMode = effectMode;
            return this;
        }

        public Builder fxEffectMode(ChoiceBox<PseudoEnum.PseudoEnumEntry> effectMode) {
            this.effectMode = ConvertUtils.toEnumCode(effectMode.getValue());
            return this;
        }

        public Builder withValue(BigDecimal value) {
            this.value = value;
            return this;
        }

        public Builder fxValue(TextField value) {
            this.value = ConvertUtils.toDecimal(value.getText());
            return this;
        }

        public Effect build() {
            return new Effect(this);
        }
    }
}
