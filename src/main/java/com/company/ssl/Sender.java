package com.company.ssl;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.List;
import java.util.Properties;

public class Sender {
    private String username;
    private String password;
    private Properties props;

    public String getUsername() {
        return username;
    }

    public Sender(String username, String password) {
        this.username = username;
        this.password = password;

        String host = "smtp.gmail.com";
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    }

    public void send(String subject, String text, String toEmail, List<File> files){
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            //от кого
            message.setFrom(new InternetAddress(username));
            //кому
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            //тема сообщения
            message.setSubject(subject);
            //текст
////////////////////////////////////////////////////////////////////

            MimeMultipart multipart = new MimeMultipart("related");

            // first part  (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
           // String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";
            ///messageBodyPart.setContent(htmlText, "text/html");
            messageBodyPart.setContent(text , "text/html; charset=utf-8");
            // add it
            multipart.addBodyPart(messageBodyPart);

            // second part (the image)
            try {
                for (File file : files) {
                    messageBodyPart = new MimeBodyPart();
                    DataSource fds = new FileDataSource(file.getAbsolutePath());
                    messageBodyPart.setDataHandler(new DataHandler(fds));
//                    messageBodyPart.setHeader("Content-ID","<image>");
                    messageBodyPart.setFileName(file.getName());
//                    messageBodyPart.setDescription("2");
//                    messageBodyPart.setDisposition("3");
//                    messageBodyPart.setText("4");
                    //                     add it
                    multipart.addBodyPart(messageBodyPart);
                }
            }catch (Exception e) {

            }
            // put everything together
            message.setContent(multipart);

 ///////////////////////////////////////////////////////////////////////////
            //отправляем сообщение
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}