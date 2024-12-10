package com.example.scheduleyourweek.utility;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    private static String adminEmail = "matoshiqlirim@gmail.com"; // replace this with your email

    private static String AppPassword = "rcis mhrs bgfv qiil"; // generate an app password with google





    public static void sendCode(String receiverEmail, String sixDigitCode) throws MessagingException{
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(adminEmail, AppPassword);
            }
        });


        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(adminEmail));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
        message.setSubject("OTP Code");
        message.setText("This is your OTP Code: " + sixDigitCode);

        Transport.send(message);

    }


}
