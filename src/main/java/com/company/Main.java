package com.company;


import com.company.ssl.Sender;
import com.company.view.Authorization;
import javafx.application.Application;

public class Main {
     private static com.company.ssl.Sender sslSender;//= new com.company.ssl.Sender("roman.samidov@gmail.com", "Samadidov22847");

        public static void main(String[] args){


            Authorization authorization = new Authorization();
            authorization.launchIt(args);

    }

    public static void sendMessage(String toEmail, String subject, String text) {
         sslSender.send(subject, text, toEmail);
    }

    public static void setSslSender(Sender sslSender) {
        Main.sslSender = sslSender;
    }
}