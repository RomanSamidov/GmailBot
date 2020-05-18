package com.company;


import com.company.ssl.SendersManager;
import com.company.view.Authorization;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

     private static SendersManager senders = new SendersManager();
     private static ArrayList<String> recipients = new ArrayList<>();
     private static boolean hasError = false;

        public static void main(String[] args){

            hasError = senders.readSenders("Senders.txt");
            readRecipients("Recipients.txt");

            hasError = senders.hasError();

            Authorization authorization = new Authorization();
            authorization.launchIt(args);
    }

    public static boolean sendMessages(String subject, String text) {
        boolean hasErr = senders.sendMessages(recipients, subject, text);
        return hasErr;
    }

    public static void readRecipients(String file) {
        try(FileReader reader = new FileReader(file))
        {
            while (true) {
                String username = "";

                int c = reader.read();
                if( c == '[') {
                    c = reader.read();
                    while (c != ']') {
                        if(c != ' ') username = username + (char) c;
                        c = reader.read();
                    }

                    recipients.add(username);
                }

                if(c == -1) {
                    break;
                }
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static boolean isHasError() {
        return hasError;
    }

    public static String getErrors() {
            String errorOut = "";
        for (String error: senders.getErrors()) {
            errorOut += error + "\n";
        }
        return errorOut;
    }
}