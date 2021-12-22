package com.company;

import java.util.ArrayList;


public class RecipientsSelector {

    private ArrayList<ArrayList<String>> recipientsAndParameters = new ArrayList<>();
    private ArrayList<ArrayList<String>> selectedParameters = new ArrayList<>();


    public ArrayList<String> getRecipients() {
        ArrayList<String> preResultRecipients;
        if (selectedParameters.get(0).isEmpty()) {
            preResultRecipients = new ArrayList(recipientsAndParameters.get(0));
            if(preResultRecipients.get(0).equalsIgnoreCase("почта"))
                preResultRecipients.remove(0);
        } else {
            preResultRecipients = new ArrayList();
            for (int i = 1; i < recipientsAndParameters.get(0).size(); i++) {
                String s = recipientsAndParameters.get(0).get(i);
                if (!preResultRecipients.contains(s)) {
                    for (int h = 0; h < selectedParameters.get(0).size(); h++) {
                        int j = 0;
                        while (j < selectedParameters.size()) {
                            if (selectedParameters.get(j).get(h).equalsIgnoreCase("*")
                                    || recipientsAndParameters.get(j + 1).get(i).equalsIgnoreCase(selectedParameters.get(j).get(h))) {
                                j++;
                            } else {
                                j = selectedParameters.size() + 1;
                            }
                            if (j == selectedParameters.size()) {
                                preResultRecipients.add(s);
                                h = selectedParameters.get(0).size() + 1;
                            }
                        }
                    }
                }
            }
        }
        ArrayList<String> resultRecipients = new ArrayList<>();
        for (String s : preResultRecipients) {
            if (!resultRecipients.contains(s)) resultRecipients.add(s);
        }
        return resultRecipients;
    }


    public void setRecipientsAndParameters(ArrayList<ArrayList<String>> recipientsAndParameters) {
        this.recipientsAndParameters = recipientsAndParameters;
        selectedParameters = new ArrayList<>();
    }


    public boolean isEmpty() {
        return recipientsAndParameters.isEmpty();
    }


    public ArrayList<ArrayList<String>> getParameters() {
        ArrayList<ArrayList<String>> parameters = new ArrayList<>();
        for (int i = 1; i < recipientsAndParameters.size(); i++) {
            parameters.add(new ArrayList<>(recipientsAndParameters.get(i)));
        }
        return parameters;
    }


    public void setSelectedParameters(ArrayList<ArrayList<String>> selectedParameters) {
        this.selectedParameters = selectedParameters;
    }
}
