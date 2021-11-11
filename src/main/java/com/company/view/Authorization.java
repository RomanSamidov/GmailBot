package com.company.view;

import com.company.ErrorsWriter;
import com.company.RecipientsSelector;
import com.company.ssl.SendersManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Authorization extends Application {

    private SendersManager senders = new SendersManager();

    public void start(final Stage primaryStage) {
        try {
            primaryStage.setMaxHeight(700);
            primaryStage.setMinHeight(300);
            primaryStage.setHeight(500);
            primaryStage.setMaxWidth(1300);
            primaryStage.setMinWidth(600);
            primaryStage.setWidth(600);
            VBox root = new VBox();

            HBox box3 = new HBox();
            box3.setSpacing(10);

            RecipientsSelectorUI box4 = new RecipientsSelectorUI();
            box4.setSpacing(10);

            Label label = new Label("Необранний файл з отримувачами");

            final FileChooser fileChooser1 = new FileChooser();
            Button button1 = new Button("Обрати");
            button1.setOnAction(event -> {
                String string = fileChooser1.showOpenDialog(primaryStage).getAbsolutePath();
                if(RecipientsSelector.readRecipientsFromFile(string)) {
                    label.setText("Отримувачі з " + string.substring(string.lastIndexOf('/')+1));
                    box4.setSelector();
                } else  label.setText("Необранний файл з отримувачами");
            });
            box3.getChildren().addAll(button1, label);


            root.setSpacing(10);
            root.setPadding(new Insets(20, 20, 20, 20));
            root.setStyle("-fx-base: rgba(60, 60, 60, 255);");
            final TextField messageSubject = new TextField();
            root.getChildren().addAll(box3, box4, new Label("Тема сообщения:"), messageSubject);
            final TextArea messageText = new TextArea();
            messageText.setPrefHeight(600);
//            messageText.setMaxHeight(600);
            HBox box = new HBox();

            Image strongI=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ЖИРНЫЙ.png")));
            ImageView strongIv=new ImageView(strongI);
            strongIv.setFitHeight(20);
            strongIv.setFitWidth(20);
            Button strong = new Button("", strongIv);

            strong.setOnAction(event -> messageText.appendText( "<strong> Текст </strong>"));
            Image emI=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/КУРСИВ.png")));
            ImageView emIv=new ImageView(emI);
            emIv.setFitHeight(20);
            emIv.setFitWidth(20);
            Button em = new Button("",emIv);
            em.setOnAction(event -> messageText.appendText( "<em> Текст </em>"));
            Image uI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ПОДЧЕРКНУТЫЙ.png")));
            ImageView uIv=new ImageView(uI);
            uIv.setFitHeight(20);
            uIv.setFitWidth(20);
            Button u = new Button("", uIv);
            u.setOnAction(event -> messageText.appendText( "<u> Текст </u>"));

            TextField sizeField = new TextField("14");
            Image sizeI=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/РАЗМЕР.png")));
            ImageView sizeIv=new ImageView(sizeI);
            sizeIv.setFitHeight(20);
            sizeIv.setFitWidth(20);
            Button size = new Button("", sizeIv);
            size.setOnAction(event -> {
               try {
                   messageText.appendText( "<font size=\"" + Integer.parseInt(sizeField.getText()) + "\"> Текст </font>");
               } catch ( Exception e) {  e.printStackTrace();}
            });
            Image plusI=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/РАЗМЕР(+).png")));
            ImageView plusIv=new ImageView(plusI);
            plusIv.setFitHeight(20);
            plusIv.setFitWidth(20);
            Button plus = new Button("", plusIv);
            plus.setOnAction(event -> sizeField.setText(String.valueOf(Integer.parseInt(sizeField.getText())+2)));
            Image minusI=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/РАЗМЕР(-).png")));
            ImageView minusIv=new ImageView(minusI);
            minusIv.setFitHeight(20);
            minusIv.setFitWidth(20);
            Button minus = new Button("", minusIv);
            minus.setOnAction(event -> sizeField.setText(String.valueOf(Integer.parseInt(sizeField.getText())-2)));

//            ObservableList<String> langs = FXCollections.observableArrayList("Красный", "Синий", "Зеленый", "Жёлтый");
//            ComboBox<String> langsComboBox = new ComboBox<String>(langs);
//            langsComboBox.setOnAction(event -> {
//                switch (langsComboBox.getValue()){
//                    case "Красный":
//                        messageText.appendText( "<font color=\"red\"> Текст </font>");
//                        break;
//                    case "Синий":
//                        messageText.appendText( "<font color=\"blue\"> Текст </font>");
//                        break;
//                    case "Зеленый":
//                        messageText.appendText( "<font color=\"green\"> Текст </font>");
//                        break;
//                    case "Жёлтый":
//                        messageText.appendText( "<font color=\"yellow\"> Текст </font>");
//                        break;
//                }
//            });
//
//            ObservableList<String> fonts = FXCollections.observableArrayList("Arial", "Times New Roman", "Calibri");
//            ComboBox<String> fontComboBox = new ComboBox<String>(fonts);
//            fontComboBox.setOnAction(event -> {
//                switch (fontComboBox.getValue()){
//                    case "Arial":
//                        messageText.appendText( "<font face=\"Arial\"> Текст </font>");
//                        break;
//                    case "Times New Roman":
//                        messageText.appendText( "<font face=\"Times New Roman\"> Текст </font>");
//                        break;
//                    case "Calibri":
//                        messageText.appendText( "<font face=\"Calibri\"> Текст </font>");
//                        break;
//
//                }
//            });

            Button normal = new Button("Редактор");
            normal.setOnAction(event -> getHostServices().showDocument("https://htmled.it/redaktor/"));

//            box.getChildren().addAll(strong, em, u, size, sizeField, langsComboBox, fontComboBox, normal);

            box.getChildren().addAll(strong, em, u, size, sizeField, plus, minus, normal);

            HBox box2 = new HBox();
            box2.setSpacing(10);

            final FileChooser fileChooser = new FileChooser();
            final List<File> files = new ArrayList<>();

            Image addI=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ФАЙЛ.png")));
            ImageView addIv=new ImageView(addI);
            addIv.setFitHeight(20);
            addIv.setFitWidth(20);

            Button button = new Button("", addIv);
//            button.setRotate(90);
            button.setOnAction(event -> files.addAll(fileChooser.showOpenMultipleDialog(primaryStage)));

            Button buttonNextView = new Button("Отправить");
            buttonNextView.setOnAction(event -> {

                // create a text input dialog
                TextInputDialog td = new TextInputDialog("Введите вашу почту.");
                td.getDialogPane().setStyle("-fx-base: rgba(60, 60, 60, 255);");
                // setHeaderText
                td.setHeaderText("Введите вашу почту");
                // show the text input dialog
                Optional<String> login = td.showAndWait();

                PasswordDialog pd = new PasswordDialog();
                pd.getDialogPane().setStyle("-fx-base: rgba(60, 60, 60, 255);");
                Optional<String> password = pd.showAndWait();

                senders.setSender(login.get(), password.get());
                ArrayList<String> recipients = RecipientsSelector.getRecipients();
                boolean hasError = senders.sendMessages(recipients, messageSubject.getText(), messageText.getText(), files);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("Результат:");
                if(!hasError){
                    alert.setContentText("Всем отправило");
                }
                else {
                    alert.setContentText("Не всем отправило");
                    ErrorsWriter.writeErrorsODS(senders.getErrors(), recipients);
                }
                alert.getDialogPane().setStyle("-fx-base: rgba(60, 60, 60, 255);");
                alert.showAndWait();

            });

            box2.getChildren().addAll(buttonNextView, button);

            root.getChildren().addAll(box, new Label("Сообщение:"), messageText, box2);

            Scene scene = new Scene(root,500,600);
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