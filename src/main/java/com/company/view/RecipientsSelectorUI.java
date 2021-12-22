package com.company.view;

import com.company.RecipientsSelector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.util.ArrayList;


public class RecipientsSelectorUI extends HBox {

    private static RecipientsSelector recipientsSelector;

    private static ArrayList<ComboBox<String>> listOfComboBoxes;
    private static ArrayList<ObservableList<String>> listOfSelectableParameters;
    private static ArrayList<ArrayList<String>> listOfParameters;

    private static ArrayList<ArrayList<String>> selectedParameters;
    private static ArrayList<String> nowSelectedParameters;
    private static ComboBox<String> comboBoxForSelected;
    private static ObservableList<String> listOfSelectedParameters;

    public RecipientsSelectorUI() {
        super();
        setSpacing(10);
    }

    public void unsetSelector() {
        getChildren().clear();
        recipientsSelector = new RecipientsSelector();
        listOfComboBoxes = new ArrayList<>();
        listOfSelectableParameters = new ArrayList<>();
        selectedParameters = new ArrayList<>();
        comboBoxForSelected = new ComboBox<>();
        listOfParameters = new ArrayList<>();
        nowSelectedParameters = new ArrayList<>();
        listOfSelectedParameters = FXCollections.observableArrayList(new ArrayList());
    }

    public void setSelector(RecipientsSelector recipientsSelector1) {
        getChildren().clear();
        recipientsSelector = recipientsSelector1;
        selectedParameters = new ArrayList<>();
        listOfParameters = new ArrayList<>();
        listOfParameters = recipientsSelector.getParameters();
        listOfSelectableParameters = new ArrayList<>();

        for (int i = 0; i < listOfParameters.size(); i++) {
            ArrayList<String> arrayList = new ArrayList<>();
            for (int j = 0; j < listOfParameters.get(i).size(); j++) {
                if (!arrayList.contains(listOfParameters.get(i).get(j))) {
                    arrayList.add(listOfParameters.get(i).get(j));
                }
            }
            selectedParameters.add(new ArrayList<>());
            listOfSelectableParameters.add(FXCollections.observableArrayList(new ArrayList(arrayList)));
        }
        listOfComboBoxes = new ArrayList<>();
        nowSelectedParameters = new ArrayList<>();
        for (int i = 0; i < listOfSelectableParameters.size(); i++) {
            nowSelectedParameters.add("*");
            listOfComboBoxes.add(new ComboBox<>(listOfSelectableParameters.get(i)));
            listOfComboBoxes.get(i).setOnAction(eventHandlerForComboBoxes);
            listOfComboBoxes.get(i).setPromptText(listOfSelectableParameters.get(i).get(0));
        }
        getChildren().addAll(listOfComboBoxes);
        listOfSelectedParameters = FXCollections.observableArrayList();
        comboBoxForSelected = new ComboBox<>(listOfSelectedParameters);
        comboBoxForSelected.setPromptText("Убрать");
        Button del = new Button("-");
        del.setOnAction(event -> {
            String toDelete = comboBoxForSelected.getValue();
            if (toDelete == null || toDelete.equalsIgnoreCase("убрать")) return;
            int i = Integer.parseInt(toDelete.substring(0, toDelete.indexOf(')')));
            for (ArrayList<String> selectedParameter : selectedParameters) {
                selectedParameter.remove(i);
            }
            listOfSelectedParameters.clear();
            for (int j = 0; j < selectedParameters.get(0).size(); j++) {
                StringBuilder sb = new StringBuilder();
                sb.append(listOfSelectedParameters.size()).append(")");
                for (int z = 0; z < selectedParameters.size(); z++) {
                    if (z != 0) sb.append(",");
                    sb.append(selectedParameters.get(z).get(j));
                }
                listOfSelectedParameters.add(String.valueOf(sb));
            }
            if (listOfSelectedParameters.isEmpty()) {
                comboBoxForSelected.setValue(null);
            } else {
                comboBoxForSelected.setValue(listOfSelectedParameters.get(0));
            }
        });
        Button add = new Button("+");
        add.setOnAction(event -> {
            StringBuilder sb = new StringBuilder();
            sb.append(listOfSelectedParameters.size()).append(")");
            for (int i = 0; i < nowSelectedParameters.size(); i++) {
                if (i != 0) sb.append(",");
                sb.append(nowSelectedParameters.get(i));
                selectedParameters.get(i).add(nowSelectedParameters.get(i));
            }
            listOfSelectedParameters.add(String.valueOf(sb));
        });

        getChildren().addAll(add, del, comboBoxForSelected);
    }


    public ArrayList<String> getRecipients() {
        recipientsSelector.setSelectedParameters(selectedParameters);
        return recipientsSelector.getRecipients();
    }


    public static final EventHandler<ActionEvent> eventHandlerForComboBoxes = (event -> {
        if (((ComboBox) event.getSource()).getItems().isEmpty()) return;
        if (((ComboBox) event.getSource()).getValue() == null) return;
        int id = 0;
        for (id = 0; id < listOfSelectableParameters.size(); id++) {
            if (((String) ((ComboBox) event.getSource()).getItems().get(0))
                    .equalsIgnoreCase(listOfSelectableParameters.get(id).get(0))) {
                break;
            }
        }
        if (((ComboBox) event.getSource()).getValue().toString()
                .equalsIgnoreCase(listOfSelectableParameters.get(id).get(0))) {
            nowSelectedParameters.set(id, "*");
        } else {
            nowSelectedParameters.set(id, ((ComboBox) event.getSource()).getValue().toString());
        }
        for (int j = 0; j < listOfSelectableParameters.size(); j++) {
            listOfSelectableParameters.get(j).clear();
            for (int ji = 0; ji < listOfParameters.get(j).size(); ji++) {
                if (!listOfSelectableParameters.get(j).contains(listOfParameters.get(j).get(ji))) {
                    int h = 0;
                    if (ji == 0) {
                        listOfSelectableParameters.get(j).add(listOfParameters.get(j).get(0));
                    } else {
                        while (h < nowSelectedParameters.size()) {
                            if (nowSelectedParameters.get(h).equalsIgnoreCase("*")
                                    || listOfParameters.get(h).get(ji).equalsIgnoreCase(nowSelectedParameters.get(h))) {
                                h++;
                            } else {
                                h = nowSelectedParameters.size() + 1;
                            }
                            if (h == nowSelectedParameters.size()) {
                                listOfSelectableParameters.get(j).add(listOfParameters.get(j).get(ji));
                                h += 2;
                            }
                        }
                    }
                }
            }
        }
        for (int d = 0; d < listOfComboBoxes.size(); d++) {
            listOfComboBoxes.get(d).setOnAction(ev -> {});
            if (nowSelectedParameters.get(d).equalsIgnoreCase("*")) {
                listOfComboBoxes.get(d).setValue(listOfSelectableParameters.get(d).get(0));
            } else
                for (int a = 0; a < listOfSelectableParameters.get(d).size(); a++) {
                    if (listOfSelectableParameters.get(d).get(a).equalsIgnoreCase(nowSelectedParameters.get(d))) {
                        listOfComboBoxes.get(d).setValue(listOfSelectableParameters.get(d).get(a));
                        break;
                    }
                }
            listOfComboBoxes.get(d).setOnAction(RecipientsSelectorUI.eventHandlerForComboBoxes);
        }
    });
}
