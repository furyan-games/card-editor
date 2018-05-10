package cz.furyan.cardeditor.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonDeserialize(builder = Card.Builder.class)
public class Card {
    private String title;
    private String text;
    private String imageUrl;
    private List<Condition> conditions;
    private List<Action> actions;

    private Card(Builder b) {
        this.title = b.title;
        this.text = b.text;
        this.imageUrl = b.imageUrl;
        this.conditions = b.conditions;
        this.actions = b.actions;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public List<Action> getActions() {
        return actions;
    }

    public static class Builder {
        private String title;
        private String text;
        private String imageUrl;
        private List<Condition> conditions = Collections.emptyList();
        private List<Action> actions = Collections.emptyList();

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder fxTitle(TextField title) {
            this.title = title.getText();
            return this;
        }

        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        public Builder fxText(TextArea text) {
            this.text = text.getText();
            return this;
        }

        public Builder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder fxImageUrl(TextField imageUrl) {
            this.imageUrl = imageUrl.getText();
            return this;
        }

        public Builder withConditions(List<Condition> conditions) {
            this.conditions = Collections.unmodifiableList(new ArrayList<>(conditions));
            return this;
        }

        public Builder withActions(List<Action> actions) {
            this.actions = Collections.unmodifiableList(new ArrayList<>(actions));
            return this;
        }

        public Card build() {
            return new Card(this);
        }
    }
}
