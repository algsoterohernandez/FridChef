package com.fpdual.javaweb.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Clase de pruebas unitarias para la clase SenderEmailService
 */
@ExtendWith(MockitoExtension.class)
public class SenderEmailServiceTest {

    @Mock
    private Properties mailProp, credentialProp;

    /**
     * Prueba unitaria para el constructor de la clase SenderEmailService.
     * Debe devolver una instancia no nula de SenderEmailService cuando se le pasan propiedades válidas.
     *
     * @throws IOException Si ocurre un error al cargar las propiedades.
     */
    @Test
    public void testSenderConstructor_notNull() throws IOException {

        //Prepare method dependencies
        doNothing().when(mailProp).load(any(InputStream.class));
        doNothing().when(credentialProp).load(any(InputStream.class));


       //Execute method
       SenderEmailService senderEmail = new SenderEmailService(mailProp, credentialProp);

       //Asserts
        assertNotNull(senderEmail);

    }

    /**
     * Prueba unitaria para el constructor de la clase SenderEmailService.
     * Debe devolver una instancia no nula de SenderEmailService incluso cuando ocurre una excepción de E/S al cargar las propiedades.
     *
     * @throws IOException Si ocurre un error al cargar las propiedades.
     */
    @Test
    public void testSenderConstructor_iOException() throws IOException {

        //Prepare method dependencies
        doNothing().when(mailProp).load(any(InputStream.class));
        doThrow(IOException.class).when(credentialProp).load(any(InputStream.class));

        //Asserts
        SenderEmailService email = new SenderEmailService(mailProp, credentialProp);
        assertNotNull(email);
    }

    /**
     * Prueba unitaria para el método sendEmail().
     * Debe devolver falso cuando se le pasa una dirección de correo electrónico incorrecta.
     * Debe lanzar una excepción de MessagingException si ocurre un error al enviar el correo electrónico.
     */
    @Test
    public void testSendEmail_wrongEmail_throwsMessagingException() {
        //Asserts
        boolean sended = new SenderEmailService(new Properties(), new Properties()).sendEmail("hola", "hola2", "hola3 ", "hola4");

        assertFalse(sended);
    }

}