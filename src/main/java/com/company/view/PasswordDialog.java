package com.company.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;


public class PasswordDialog extends Dialog<String> {
    private final PasswordField passwordField;

    public PasswordDialog() {
        setTitle("Введите ваш пароль");
        setHeaderText("Введите ваш пароль.");
        ButtonType passwordButtonType = new ButtonType("Ок", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(passwordButtonType, ButtonType.CANCEL);
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        HBox hBox = new HBox();
        hBox.getChildren().add(passwordField);
        hBox.setPadding(new Insets(20));
        HBox.setHgrow(passwordField, Priority.ALWAYS);
        getDialogPane().setContent(hBox);
        Platform.runLater(() -> passwordField.requestFocus());
        setResultConverter(dialogButton -> {
            if (dialogButton == passwordButtonType) {
                return passwordField.getText();
            }
            return null;
        });
    }

}