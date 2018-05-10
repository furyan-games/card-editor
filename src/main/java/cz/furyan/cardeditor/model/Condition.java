package cz.furyan.cardeditor.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.furyan.cardeditor.pseudoenum.PseudoEnum;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

@JsonDeserialize(builder = Condition.Builder.class)
public class Condition {
    public final static Condition EMPTY = new Condition.Builder().build();

    private String stat;
    private String operator;
    private BigDecimal falseThreshold;
    private BigDecimal trueThreshold;
    private String value;

    private Condition(Builder b) {
        this.stat = b.stat;
        this.operator = b.operator;
        this.falseThreshold = b.falseThreshold;
        this.trueThreshold = b.trueThreshold;
    }

    public String getStat() {
        return stat;
    }

    public String getOperator() {
        return operator;
    }

    public BigDecimal getFalseThreshold() {
        return falseThreshold;
    }

    public BigDecimal getTrueThreshold() {
        return trueThreshold;
    }


    public static class Builder {
        private String stat;
        private String operator;
        private BigDecimal falseThreshold;
        private BigDecimal trueThreshold;

        public Builder withStat(String stat) {
            this.stat = stat;
            return this;
        }

        public Builder fxStat(ChoiceBox<PseudoEnum.PseudoEnumEntry> stat) {
            this.stat = ConvertUtils.toEnumCode(stat.getValue());
            return this;
        }

        public Builder withOperator(String operator) {
            this.operator = operator;
            return this;
        }

        public Builder fxOperator(ChoiceBox<PseudoEnum.PseudoEnumEntry> operator) {
            this.operator = ConvertUtils.toEnumCode(operator.getValue());
            return this;
        }

        public Builder withFalseThreshold(BigDecimal falseThreshold) {
            this.falseThreshold = falseThreshold;
            return this;
        }

        public Builder fxFalseThreshold(TextField falseThreshold) {
            this.falseThreshold = ConvertUtils.toDecimal(falseThreshold.getText());
            return this;
        }

        public Builder withTrueThreshold(BigDecimal trueThreshold) {
            this.trueThreshold = trueThreshold;
            return this;
        }

        public Builder fxTrueThreshold(TextField trueThreshold) {
            this.trueThreshold = ConvertUtils.toDecimal(trueThreshold.getText());
            return this;
        }

        public Condition build() {
            return new Condition(this);
        }
    }
}
