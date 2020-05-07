package com.company.view;

import com.company.Main;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Authorization extends Application {
    public void start(final Stage primaryStage) {
        try {

            final VBox root = new VBox();

            final TextField loginField = new TextField();
            root.getChildren().addAll(new Label("Логин:"), loginField);

            final PasswordField passwordField = new PasswordField();
            root.getChildren().addAll(new Label("Пароль:"), passwordField);

            final Button buttonNextView = new Button("Далее");
            buttonNextView.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    Main.setSslSender(new com.company.ssl.Sender(loginField.getText(), passwordField.getText()));
                    getMessage(primaryStage);
                }
            });
            root.getChildren().add(buttonNextView);

            Scene scene = new Scene(root,400,400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void getMessage(final Stage primaryStage){
        try {

            VBox root = new VBox();

            final TextField messageRecipient = new TextField();
            root.getChildren().addAll(new Label("Кому:"), messageRecipient);

            final TextField messageSubject = new TextField();
            root.getChildren().addAll(new Label("Тема сообщения:"), messageSubject);

            final TextArea messageText = new TextArea();
            root.getChildren().addAll(new Label("Сообщение:"), messageText);

            Button buttonNextView = new Button("Далее");
            buttonNextView.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    Main.sendMessage(messageRecipient.getText(), messageSubject.getText(), messageText.getText());
                    primaryStage.close();
                }
            });
            root.getChildren().add(buttonNextView);

            Scene scene = new Scene(root,400,400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void launchIt(String[] args) {
        launch(args);
    }

}