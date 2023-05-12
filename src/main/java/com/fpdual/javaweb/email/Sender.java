package com.fpdual.javaweb.email;

import lombok.Getter;
import lombok.Setter;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Sender {

    @Setter
    @Getter
    Properties mailProp = new Properties();

    @Setter
    @Getter
    Properties credentialProp = new Properties();

    /**
     * Build the sender class loading the properties from mail and credentials files.
     */
    public Sender() {
        try {
            // Loads all the properties of file "mail.properties".
            mailProp.load(getClass().getClassLoader().getResourceAsStream("mail.properties"));
            credentialProp.load(getClass().getClassLoader().getResourceAsStream("credentials.properties"));
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
    public boolean send(String from, String to, String subject, String content) {
        // Get the Session object.// and pass username and password
        Session session = createSession();

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
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }

    }

    /**
     * Send an email with from and recipient address, subject, d a simple HTML format content and an attached file.
     * @param from from email address
     * @param to recipient email address
     * @param subject email subject
     * @param text email content in html format
     * @param content path where the temp file is located
     * @return a {@link boolean} indicating if the email was sent or not.
     */
    public boolean send(String from, String to, String subject, String text, String content) throws FileNotFoundException, IOException {
        // Get the Session object.// and pass username and password
        Session session = createSession();
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Attach a file.
            //First Part of the body: text
            BodyPart texto = new MimeBodyPart();
            texto.setContent(text,"text/html");

            //Second Part of the body: project properties file.
            File file = new File(content);

            InputStream fileData = getClass().getClassLoader().getResourceAsStream("mail.properties");

            try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
                int read;
                byte[] bytes = new byte[8192];
                while ((read = fileData.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            }

            BodyPart fichero = new MimeBodyPart();
            fichero.setDataHandler(new DataHandler(new FileDataSource(file)));
            fichero.setFileName(file.getName());

            //Group all part in a object
            Multipart multiPart = new MimeMultipart();
            multiPart.addBodyPart(texto);
            multiPart.addBodyPart(fichero);

            //Set Message Content
            message.setContent(multiPart);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }

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
