package com.fpdual.javaweb.email;

import lombok.Getter;
import lombok.Setter;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

@Setter
@Getter

public class SenderEmail {

    private Properties mailProp, credentialProp;
    /**
     * Build the sender class loading the properties from mail and credentials files.
     */
    public SenderEmail(Properties mailProp, Properties credentialProp) {

        try {

            this.mailProp = mailProp;
            this.credentialProp = credentialProp;
            this.mailProp.load(getClass().getClassLoader().getResourceAsStream("mail.properties"));
            this.credentialProp.load(getClass().getClassLoader().getResourceAsStream("credentials.properties"));

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Send a simple email with from and recipient address, subject and a simple HTML format content.
     * @param from from email address
     * @param to recipient email address
     * @param subject email subject
     * @param content email content in html format
     * @return a {@link boolean} indicating if the email was sent or not.
     */
    public boolean sendEmail(String from, String to, String subject, String content) {

        // Get the Session object.// and pass username and password
        Session session = createSession();

        boolean sent = false;

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setContent(content,"text/html" );

            System.out.println("sending...");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");

            sent = true;

        } catch (MessagingException mex) {

            mex.printStackTrace();

        }

        return sent;

    }


    private Session createSession() {
        Session session = Session.getInstance(mailProp, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(credentialProp.getProperty(CredentialsConstants.USER),
                        credentialProp.getProperty(CredentialsConstants.PASSWD));
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);
        return session;
    }
}