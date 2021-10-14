package com.company;


import com.company.ssl.SendersManager;
import com.company.view.Authorization;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

     public static SendersManager senders = new SendersManager();
     private static ArrayList<String> recipients = new ArrayList<>();
     private static boolean hasError = false;

        public static void main(String[] args){


//            hasError = senders.hasError();

            Authorization authorization = new Authorization();
            authorization.launchIt(args);
    }

    public static boolean setRecipients(String path) {
        recipients = RecipientsReader.readRecipients(path);
        return !recipients.isEmpty();
    }

    public static boolean sendMessages(String subject, String text, List<File> files) {
//            while(true) {
                boolean hasErr = senders.sendMessages(recipients, subject, text, files);
//            }
        return hasErr;
    }

    public static boolean isHasError() {
        return hasError;
    }

    public static String getErrors() {
            StringBuilder errorOut = new StringBuilder();
        for (String error: senders.getErrors()) {
            errorOut.append(error).append("\n");
        }
        return errorOut.toString();
    }
}