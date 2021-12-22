package com.company.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;


public class MessageInputUI extends VBox {

    private TextArea messageText;


    public void init() {
        setSpacing(10);
        TextField messageSubject = new TextField();
        messageSubject.setPrefWidth(200);
        messageText = new TextArea();
        messageText.setPrefHeight(600);
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(new Label("Тема сообщения:"), messageSubject);
        getChildren().addAll(hBox, getBoxWithButtons(), new Label("Сообщение:"), messageText);
    }


    private HBox getBoxWithButtons() {
        HBox box = new HBox();
        Image strongI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ЖИРНЫЙ.png")));
        ImageView strongIv = new ImageView(strongI);
        strongIv.setFitHeight(20);
        strongIv.setFitWidth(20);
        Button strong = new Button("", strongIv);

        strong.setOnAction(event -> messageText.appendText("<strong> Текст </strong>"));
        Image emI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/КУРСИВ.png")));
        ImageView emIv = new ImageView(emI);
        emIv.setFitHeight(20);
        emIv.setFitWidth(20);

        Button em = new Button("", emIv);
        em.setOnAction(event -> messageText.appendText("<em> Текст </em>"));
        Image uI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ПОДЧЕРКНУТЫЙ.png")));
        ImageView uIv = new ImageView(uI);
        uIv.setFitHeight(20);
        uIv.setFitWidth(20);

        Button u = new Button("", uIv);
        u.setOnAction(event -> messageText.appendText("<u> Текст </u>"));

        TextField sizeField = new TextField("14");
        Image sizeI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/РАЗМЕР.png")));
        ImageView sizeIv = new ImageView(sizeI);
        sizeIv.setFitHeight(20);
        sizeIv.setFitWidth(20);

        Button size = new Button("", sizeIv);
        size.setOnAction(event -> {
            try {
                messageText.appendText("<font size=\"" + Integer.parseInt(sizeField.getText()) + "\"> Текст </font>");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Image plusI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/РАЗМЕР(+).png")));
        ImageView plusIv = new ImageView(plusI);
        plusIv.setFitHeight(20);
        plusIv.setFitWidth(20);

        Button plus = new Button("", plusIv);
        plus.setOnAction(event -> sizeField.setText(String.valueOf(Integer.parseInt(sizeField.getText()) + 2)));
        Image minusI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/РАЗМЕР(-).png")));
        ImageView minusIv = new ImageView(minusI);
        minusIv.setFitHeight(20);
        minusIv.setFitWidth(20);

        Button minus = new Button("", minusIv);
        minus.setOnAction(event -> sizeField.setText(String.valueOf(Integer.parseInt(sizeField.getText()) - 2)));

        Button normal = new Button("Редактор");
        normal.setOnAction(event -> Window.window.getHostServices().showDocument("https://htmled.it/redaktor/"));

        box.getChildren().addAll(strong, em, u, size, sizeField, plus, minus, normal);
        return box;
    }

    public String getText() {
        return messageText.getText();
    }
}
