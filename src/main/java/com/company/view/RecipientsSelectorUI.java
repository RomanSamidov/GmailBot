package com.company.view;

import com.company.RecipientsSelector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class RecipientsSelectorUI extends HBox {

    private ComboBox<String> citiesComboBox;
    private ComboBox<String> regionComboBox;
    private Button add;
    private ObservableList<String> selected;
    private ComboBox<String> selectedComboBox;

    private String citi, region;
    private ArrayList<ArrayList<String>> selectedCities;


    public void setSelector () {
        getChildren().clear();
        if(RecipientsSelector.isAdvanced()) {
            selectedCities = new ArrayList<>();
            selectedCities.add(new ArrayList<>());
            selectedCities.add(new ArrayList<>());

            ArrayList<ArrayList<String>> recipientsAdvanced = RecipientsSelector.getRecipientsAdvanced();

            ArrayList<String> strings = new ArrayList<>();
            for (String s:recipientsAdvanced.get(1)) {
                if(!strings.contains(s)) strings.add(s);
            }
            ObservableList<String> cities = FXCollections.observableArrayList( new ArrayList(strings));
            citiesComboBox = new ComboBox<>(cities);
            citiesComboBox.setOnAction(event -> citi = citiesComboBox.getValue());

            strings = new ArrayList<>();
            for (String s:recipientsAdvanced.get(2)) {
                if(!strings.contains(s)) strings.add(s);
            }
            ObservableList<String> regions = FXCollections.observableArrayList( new ArrayList(strings));
            regionComboBox = new ComboBox<>(regions);
            regionComboBox.setOnAction(event -> region = regionComboBox.getValue());

            selected = FXCollections.observableArrayList();
            selectedComboBox = new ComboBox<>(selected);
            selectedComboBox.setOnAction(event -> {
//            citi = citiesComboBox.getValue();
            });

            add = new Button("+");
            add.setOnAction(event -> {
                selected.add(selectedCities.get(0).size() + " " + citi + ", " + region);
                selectedCities.get(0).add(citi);
                selectedCities.get(1).add(region);
                RecipientsSelector.setCities(selectedCities);
            });

            getChildren().addAll(citiesComboBox, regionComboBox, add, selectedComboBox);
        }
    }


}
