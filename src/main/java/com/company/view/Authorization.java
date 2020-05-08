package com.company.view;

import com.company.Main;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Authorization extends Application {
    public void start(final Stage primaryStage) {
        try {

            VBox root = new VBox();

            final TextField messageSubject = new TextField();
            root.getChildren().addAll(new Label("Тема сообщения:"), messageSubject);

            final TextArea messageText = new TextArea();

            if(Main.isHasError()) messageText.setText(Main.getErrors());

            root.getChildren().addAll(new Label("Сообщение:"), messageText);

            Button buttonNextView = new Button("Отправить");
            buttonNextView.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    Main.sendMessages( messageSubject.getText(), messageText.getText());
                    messageText.setText(Main.getErrors());

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