package com.company.view;

import com.company.RecipientsReader;
import com.company.RecipientsSelector;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;


public class RecipientsReaderUI extends HBox {


    public void init() {
        setSpacing(10);

        Label label = new Label("Необранний файл з отримувачами");

        final FileChooser fileChooser1 = new FileChooser();
        Button button1 = new Button("Обрати");
        button1.setOnAction(event -> {
            File file = fileChooser1.showOpenDialog(Window.primaryStage);
            if (file == null) return;
            String string = file.getAbsolutePath();
            RecipientsSelector rs = new RecipientsSelector();
            rs.setRecipientsAndParameters(RecipientsReader.readRecipients(string));
            if (!rs.isEmpty()) {
                label.setText("Отримувачі з " + string.substring(string.lastIndexOf('/') + 1));
                Window.recipientsSelectorUI.setSelector(rs);
            } else {
                label.setText("Необранний файл з отримувачами");
                Window.recipientsSelectorUI.unsetSelector();
            }
        });
        getChildren().addAll(button1, label);
    }
}
