package com.company.view;

import com.company.ErrorsWriter;
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
import java.util.*;


public class Window extends Application {

    public static final Window window = new Window();
    private static final SendersManager senders = new SendersManager();
    public static Stage primaryStage;
    public static RecipientsReaderUI recipientsReaderUI;
    public static RecipientsSelectorUI recipientsSelectorUI;
    public static MessageInputUI messageInputUI;


    public void start(final Stage primaryStage) {
        Window.primaryStage = primaryStage;
        try {
            primaryStage.setMaxHeight(700);
            primaryStage.setMinHeight(300);
            primaryStage.setHeight(500);
            primaryStage.setMaxWidth(1300);
            primaryStage.setMinWidth(600);
            primaryStage.setWidth(600);

            VBox root = new VBox();
            root.setSpacing(10);
            root.setPadding(new Insets(20, 20, 20, 20));
            root.setStyle("-fx-base: rgba(60, 60, 60, 255);");

            recipientsReaderUI = new RecipientsReaderUI();
            recipientsSelectorUI = new RecipientsSelectorUI();
            messageInputUI = new MessageInputUI();

            recipientsReaderUI.init();
            messageInputUI.init();
            root.getChildren().addAll(recipientsReaderUI, recipientsSelectorUI, messageInputUI);

            final List<File> files = new ArrayList<>();

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

                if(login.isPresent() && password.isPresent()) {
                    senders.setSender(login.get(), password.get());
                } else throw new NoSuchElementException();
                ArrayList<String> recipients = recipientsSelectorUI.getRecipients();
                boolean hasError = senders.sendMessages(recipients, messageInputUI.getText(), messageInputUI.getText(), files);

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

            Button delFiles = new Button("Открепить файли");
            delFiles.setVisible(false);
            delFiles.setOnAction(event -> {
                files.clear();
                delFiles.setVisible(false);
            });

            final FileChooser fileChooser = new FileChooser();
            Image addI=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ФАЙЛ.png")));
            ImageView addIv=new ImageView(addI);
            addIv.setFitHeight(20);
            addIv.setFitWidth(20);
            Button button = new Button("", addIv);

            button.setOnAction(event -> {
                    List<File> list = fileChooser.showOpenMultipleDialog(primaryStage);
                    if(list == null) return;
                    files.addAll(list);
                delFiles.setVisible(true);
            });



            HBox box2 = new HBox();
            box2.setSpacing(10);
            box2.getChildren().addAll(buttonNextView, button, delFiles);

            root.getChildren().addAll(box2);

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