package com.company.ssl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SendersManager {
    private Sender sender;
    private ArrayList<String> errors = new ArrayList<>();


    public SendersManager(){}


    public void setSender(String username, String password) { sender = new Sender(username, password);}


    public boolean sendMessages(ArrayList<String> toEmails, String subject, String text, List<File> files) {
        boolean hasError = false;
        int i = 0, numberEmails = toEmails.size();

        while (i < numberEmails) {
            try {
                sender.send(subject, text, toEmails.get(i), files);
                errors.add("На " + toEmails.get(i)+ " от " +sender.getUsername()+ " отправлено \n");
            } catch (Exception e) {
                errors.add("НА " + toEmails.get(i)+ " ОТ " +sender.getUsername()+ " НЕ ОТПРАВЛЕНО! \n" + e.getMessage());
                hasError = true;
            }
            i++;
        }
        return hasError;
    }


    public ArrayList<String> getErrors() {
        ArrayList<String> errorsOut = errors;
        errors = new ArrayList<>();
        return errorsOut;
    }
}
