package com.company;


import java.util.ArrayList;


public class RecipientsSelector {

    private ArrayList<String> recipients = new ArrayList<>();
    private ArrayList<ArrayList<String>> recipientsAdvanced = new ArrayList<>();
    private ArrayList<ArrayList<String>> cities = new ArrayList<>();


    public void setRecipients(ArrayList<String> recipients) {
        this.recipients = recipients;
        recipientsAdvanced = new ArrayList<>();
    }


    public void setRecipientsAdvanced(ArrayList<ArrayList<String>> recipientsAdvanced) {
        this.recipientsAdvanced = recipientsAdvanced;
        recipients = new ArrayList<>();

    }

    public boolean isEmpty() {
        return recipients.isEmpty() && recipientsAdvanced.isEmpty();
    }


    public ArrayList<String> getRecipients() {
        if(cities.get(0).isEmpty()) {
            if(!recipientsAdvanced.isEmpty())
                return recipientsAdvanced.get(0);
                return recipients;

        } else {
            ArrayList<String> recipients = new ArrayList<>();
            for (int i = 0; i < recipientsAdvanced.get(0).size(); i++) {
                for (int j = 0; j < cities.get(0).size(); j++) {
                    if ((recipientsAdvanced.get(1).get(i).equals(cities.get(0).get(j)) || "Всі міста".equals(cities.get(0).get(j)) ) &&
                            recipientsAdvanced.get(2).get(i).equals(cities.get(1).get(j))) {
                        recipients.add(recipientsAdvanced.get(0).get(i));
                        j = cities.get(0).size() + 10;//break;
                    }
                }
            }
            return recipients;
        }
    }


    public boolean isAdvanced() {
        return !recipientsAdvanced.isEmpty();
    }


    public ArrayList<ArrayList<String>> getRecipientsAdvanced() {
        return recipientsAdvanced;
    }


    public void setCities(ArrayList<ArrayList<String>> cities) {
        this.cities = cities;
    }
}
