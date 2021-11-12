package com.company;


import java.util.ArrayList;


public class RecipientsSelector {

    private static ArrayList<String> recipients = new ArrayList<>();
    private static ArrayList<ArrayList<String>> recipientsAdvanced = new ArrayList<>();
    private static ArrayList<ArrayList<String>> cities = new ArrayList<>();


    public static void setRecipients(ArrayList<String> recipients) {
        RecipientsSelector.recipients = recipients;
        recipientsAdvanced = new ArrayList<>();
    }


    public static void setRecipientsAdvanced(ArrayList<ArrayList<String>> recipientsAdvanced) {
        RecipientsSelector.recipientsAdvanced = recipientsAdvanced;
        recipients = new ArrayList<>();

    }

    public static boolean readRecipientsFromFile(String path) {
        RecipientsReader.readRecipients(path);
        return !recipients.isEmpty() || !recipientsAdvanced.isEmpty();
    }


    public static ArrayList<String> getRecipients() {
        if(cities.get(0).isEmpty()) {
            if(!recipientsAdvanced.isEmpty()) return recipientsAdvanced.get(0);
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


    public static boolean isAdvanced() {
        return !recipientsAdvanced.isEmpty();
    }


    public static ArrayList<ArrayList<String>> getRecipientsAdvanced() {
        return recipientsAdvanced;
    }


    public static void setCities(ArrayList<ArrayList<String>> cities) {
        RecipientsSelector.cities = cities;
    }
}
