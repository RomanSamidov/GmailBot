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
    private ObservableList<String> selected;

    private String citi, region, selectedToDelete;
    private ArrayList<ArrayList<String>> selectedCities;

    private ObservableList<String> possibleCities;

    public void setSelector () {
        getChildren().clear();
        if(RecipientsSelector.isAdvanced()) {
            selectedCities = new ArrayList<>();
            selectedCities.add(new ArrayList<>());
            selectedCities.add(new ArrayList<>());

            ArrayList<ArrayList<String>> recipientsAdvanced = RecipientsSelector.getRecipientsAdvanced();

            ArrayList<String> strings = new ArrayList<>();
            for (String s:recipientsAdvanced.get(2)) {
                if(!strings.contains(s)) strings.add(s);
            }
            ObservableList<String> regions = FXCollections.observableArrayList( new ArrayList(strings));
            regionComboBox = new ComboBox<>(regions);
            regionComboBox.setOnAction(event -> {
                region = regionComboBox.getValue();
                possibleCities.clear();
                for (int i = 0; i < recipientsAdvanced.get(1).size(); i++) {
                    if(region.equals(recipientsAdvanced.get(2).get(i)))
                        if(!possibleCities.contains(recipientsAdvanced.get(1).get(i)))
                            possibleCities.add(recipientsAdvanced.get(1).get(i));
                }
            });

            strings = new ArrayList<>();
            for (String s:recipientsAdvanced.get(1)) {
                if(!strings.contains(s)) strings.add(s);
            }

//            ObservableList<String> cities
            possibleCities = FXCollections.observableArrayList();
            citiesComboBox = new ComboBox<>(possibleCities);
            citiesComboBox.setOnAction(event -> citi = citiesComboBox.getValue());

            selected = FXCollections.observableArrayList();
            ComboBox<String> selectedComboBox = new ComboBox<>(selected);
            selectedComboBox.setOnAction(event -> {
                selectedToDelete = selectedComboBox.getValue();
            });

            Button del = new Button("-");
            del.setOnAction(event -> {
                int i = Integer.parseInt(selectedToDelete.substring(0,selectedToDelete.indexOf(')')));
                selected.remove(selectedToDelete);
                selectedCities.get(0).remove(i);
                selectedCities.get(1).remove(i);
                RecipientsSelector.setCities(selectedCities);

            });

            Button add = new Button("+");
            add.setOnAction(event -> {
                if(region == null) return;
                if(citi == null){
                    citi = "Всі міста";
                }
                selected.add(selectedCities.get(0).size() + ")" + citi + ", " + region);
                selectedCities.get(0).add(citi);
                selectedCities.get(1).add(region);
                RecipientsSelector.setCities(selectedCities);
            });

            getChildren().addAll(regionComboBox, citiesComboBox, add, del, selectedComboBox);
        }
    }


}
