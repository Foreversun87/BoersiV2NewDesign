/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 *
 * @author Alexander
 */
public class UiCompUtils {

    public static void setTextFieldLimit(TextField textField, int limit) {
        textField.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                if (textField.getText().length() >= limit) {
                    textField.setText(textField.getText().substring(0, limit));
                }
            }
        });
    }

    public static void setTextFieldLimit(TextArea textArea, int limit) {
        textArea.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                if (textArea.getText().length() >= limit) {
                    textArea.setText(textArea.getText().substring(0, limit));
                }
            }
        });
    }

    public static void setColor(Node node, String color) {
        node.setStyle("-fx-text-fill: " + color + ";");
    }

    public static void fadeOut(Node node) {
        fade(node, 1.0, 0.0);
    }

    public static void fadeIn(Node node) {
        fade(node, 0.0, 1.0);
    }

    public static void fade(Node node, double start, double end) {
        FadeTransition ft = new FadeTransition(Duration.seconds(5), node);
        ft.setFromValue(start);
        ft.setToValue(end);
        ft.play();
    }

}
