package com.team3_3.Planner.utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * <h1>Example Class</h1>
 *<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna
 *  aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
 *  Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur
 *  sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
 *
 * @author   Max James
 * @version  1.0
 * @since    08/05/2021
 *
 * <h2>Change Log</h2>
 *   - {DATE}: {NOTES} - {INITIALS}
 *
 *
 * <h2>Refrences: </>
 *  - Offical JavaDoc help page @link https://www.oracle.com/uk/technical-resources/articles/java/javadoc-tool.html
 *  - https://stackoverflow.com/questions/27614793/error-in-sending-email-through-smtp
 *  - https://stackoverflow.com/questions/35347269/javax-mail-authenticationfailedexception-535-5-7-8-username-and-password-not-ac
 */

public abstract class Email
{
    private static final String USERNAME = "ueateam33";  // Gmail username
    private static final String PASSWORD = "Team33Team33"; // Gmail password

    // NOTE: Two-Step Authentication needs to be turned OFF
    // NOTE: 'Allow Less Secure App' needs to be turned ON

    public static void sendEmail(String email, String subject, String body) throws MessagingException
    {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.user", USERNAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");


        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(USERNAME));

        InternetAddress toAddress = new InternetAddress(email);
        message.addRecipient(Message.RecipientType.TO, toAddress);

        message.setSubject(subject);
        message.setText(body);

        Transport transport = session.getTransport("smtp");

        transport.connect("smtp.gmail.com", USERNAME, PASSWORD);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public static void main(String[] args) throws MessagingException
    {
        String email = "james@jamessergeant.uk"; // temp-mail.org - good site for testing recipient
        String subject = "Test email";
        String body = "This is the body of test email";

        sendEmail(email, subject, body);
    }
}