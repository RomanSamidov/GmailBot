package com.company.view;

import com.company.RecipientsSelector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import java.util.ArrayList;


public class RecipientsSelectorUI extends HBox {

    private RecipientsSelector recipientsSelector;

    private ComboBox<String> citiesComboBox;
    private ComboBox<String> regionComboBox;
    private ComboBox<String> selectedComboBox;
    private ObservableList<String> listOfSelected;

    private String citi, region;
    private ArrayList<ArrayList<String>> selectedCities;

    private ObservableList<String> possibleCities;

    public RecipientsSelectorUI() {
        super();
        setSpacing(10);
    }

    public void unsetSelector () {
        getChildren().clear();
        recipientsSelector = new RecipientsSelector();
        citiesComboBox = new ComboBox<>();
        regionComboBox = new ComboBox<>();
        selectedComboBox = new ComboBox<>();
        listOfSelected = FXCollections.observableArrayList();
        citi = null;
        region = null;
        selectedCities = new ArrayList<>();
        possibleCities = FXCollections.observableArrayList();
    }

    public void setSelector (RecipientsSelector recipientsSelector1) {
        getChildren().clear();
        recipientsSelector = recipientsSelector1;
        if(recipientsSelector.isAdvanced()) {
            selectedCities = new ArrayList<>();
            selectedCities.add(new ArrayList<>());
            selectedCities.add(new ArrayList<>());

            ArrayList<ArrayList<String>> recipientsAdvanced = recipientsSelector.getRecipientsAdvanced();

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

            listOfSelected = FXCollections.observableArrayList();
            selectedComboBox = new ComboBox<>(listOfSelected);
//            selectedComboBox.setOnAction(event -> selectedToDelete = selectedComboBox.getValue());

            Button del = new Button("-");
            del.setOnAction(event -> {
                String toDelete = selectedComboBox.getValue();
                if(toDelete == null) return;
                int i = Integer.parseInt(toDelete.substring(0,toDelete.indexOf(')')));
                selectedCities.get(0).remove(i);
                selectedCities.get(1).remove(i);
                listOfSelected.clear();
                for (int j = 0; j < selectedCities.get(1).size(); j++) {
                    listOfSelected.add(listOfSelected.size() + ")" + selectedCities.get(0).get(j) + ", " + selectedCities.get(1).get(j));
                }
            });

            Button add = new Button("+");
            add.setOnAction(event -> {
                if(region == null) return;
                if(citi == null){
                    citi = "Всі міста";
                }
                listOfSelected.add(selectedCities.get(0).size() + ")" + citi + ", " + region);
                selectedCities.get(0).add(citi);
                selectedCities.get(1).add(region);
            });

            getChildren().addAll(regionComboBox, citiesComboBox, add, del, selectedComboBox);
        }
    }

    public ArrayList<String> getRecipients() {
        recipientsSelector.setCities(selectedCities);
        return recipientsSelector.getRecipients();
    }
}
