package com.company.view;

import com.company.Main;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Authorization extends Application {
    public void start(final Stage primaryStage) {
        try {

            VBox root = new VBox();

            final TextField messageSubject = new TextField();
            root.getChildren().addAll(new Label("Тема сообщения:"), messageSubject);
            final TextArea messageText = new TextArea();

            HBox box = new HBox();

            Button strong = new Button("полужирный");
            strong.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    messageText.appendText( "<strong> Текст </strong>");
                }
            });
            Button em = new Button("курсив");
            em.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    messageText.appendText( "<em> Текст </em>");
                }
            });
            Button u = new Button("подчерк");
            u.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    messageText.appendText( "<u> Текст </u>");
                }
            });

            TextField sizeField = new TextField("14");
            Button size = new Button("Size");
            size.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                   try {
                       messageText.appendText( "<font size=\"" + Integer.parseInt(sizeField.getText()) + "\"> Текст </font>");
                   } catch ( Exception e) { System.out.println(e);}
                }
            });

            ObservableList<String> langs = FXCollections.observableArrayList("Красный", "Синий", "Зеленый", "Жёлтый");
            ComboBox<String> langsComboBox = new ComboBox<String>(langs);
            langsComboBox.setOnAction(event -> {
                switch (langsComboBox.getValue()){
                    case "Красный":
                        messageText.appendText( "<font color=\"red\"> Текст </font>");
                        break;
                    case "Синий":
                        messageText.appendText( "<font color=\"blue\"> Текст </font>");
                        break;
                    case "Зеленый":
                        messageText.appendText( "<font color=\"green\"> Текст </font>");
                        break;
                    case "Жёлтый":
                        messageText.appendText( "<font color=\"yellow\"> Текст </font>");
                        break;
                }
            });

            ObservableList<String> fonts = FXCollections.observableArrayList("Arial", "Times New Roman", "Calibri");
            ComboBox<String> fontComboBox = new ComboBox<String>(fonts);
            fontComboBox.setOnAction(event -> {
                switch (fontComboBox.getValue()){
                    case "Arial":
                        messageText.appendText( "<font face=\"Arial\"> Текст </font>");
                        break;
                    case "Times New Roman":
                        messageText.appendText( "<font face=\"Times New Roman\"> Текст </font>");
                        break;
                    case "Calibri":
                        messageText.appendText( "<font face=\"Calibri\"> Текст </font>");
                        break;

                }
            });

            Button normal = new Button("Редактор");
            normal.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    getHostServices().showDocument("https://htmled.it/redaktor/");
                }
            });

            box.getChildren().addAll(strong, em, u, size, sizeField, langsComboBox, fontComboBox, normal);

            HBox box2 = new HBox();

            final FileChooser fileChooser = new FileChooser();
            final List<File>[] files = new List[1];
            Button button = new Button("Select some Pictures");
            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                     files[0] = fileChooser.showOpenMultipleDialog(primaryStage);
                    for (File file: files[0]) {
                        System.out.println(file.getAbsolutePath());
                    }


                };
            });

            Button buttonNextView = new Button("Отправить");
            buttonNextView.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    Main.sendMessages( messageSubject.getText(), messageText.getText(), files[0]);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText("Результат:");
                    alert.setContentText(Main.getErrors());
                    alert.showAndWait();

                }
            });

            box2.getChildren().addAll(buttonNextView, button);

            root.getChildren().addAll(box, new Label("Сообщение:"), messageText, box2);

            Scene scene = new Scene(root,1280,720);
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