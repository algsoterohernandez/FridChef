package com.fpdual.javaweb.service;

import com.fpdual.javaweb.constants.CredentialsConstants;
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

/**
 * Clase que proporciona funcionalidad para enviar correos electrónicos.
 */
public class SenderEmailService {

    private Properties mailProp, credentialProp;

    /**
     * Constructor de la clase SenderEmailService.
     *
     * @param mailProp Propiedades relacionadas con la configuración del correo.
     * @param credentialProp Propiedades relacionadas con las credenciales de autenticación.
     */
    public SenderEmailService(Properties mailProp, Properties credentialProp) {

        try {
            this.mailProp = mailProp;
            this.credentialProp = credentialProp;

            // Cargar las propiedades de correo desde el archivo "mail.properties"
            this.mailProp.load(getClass().getClassLoader().getResourceAsStream("mail.properties"));

            // Cargar las propiedades de credenciales desde el archivo "credentials.properties"
            this.credentialProp.load(getClass().getClassLoader().getResourceAsStream("credentials.properties"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Envía un correo electrónico.
     *
     * @param from Dirección de correo electrónico del remitente.
     * @param to Dirección de correo electrónico del destinatario.
     * @param subject Asunto del correo electrónico.
     * @param content Contenido del correo electrónico.
     * @return true si el correo electrónico se envía exitosamente, false de lo contrario.
     */
    public boolean sendEmail(String from, String to, String subject, String content) {

        // Obtener el objeto Session y pasar las credenciales de autenticación
        Session session = createSession();

        boolean sent = false;

        try {
            // Crear un objeto MimeMessage por defecto.
            MimeMessage message = new MimeMessage(session);

            // Establecer el campo From: del encabezado.
            message.setFrom(new InternetAddress(from));

            // Establecer el campo To: del encabezado.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Establecer el campo Subject: del encabezado.
            message.setSubject(subject);

            // Establecer el contenido del mensaje
            message.setContent(content, "text/html");

            System.out.println("sending...");

            // Enviar el mensaje
            Transport.send(message);
            System.out.println("Sent message successfully....");

            sent = true;

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

        return sent;
    }

    /**
     * Crea una instancia de Session para la configuración del correo electrónico.
     *
     * @return Objeto Session configurado.
     */
    private Session createSession() {
        Session session = Session.getInstance(mailProp, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(credentialProp.getProperty(CredentialsConstants.USER),
                        credentialProp.getProperty(CredentialsConstants.PASSWD));
            }
        });
        return session;
    }
}