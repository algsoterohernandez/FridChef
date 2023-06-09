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

@ExtendWith(MockitoExtension.class)
public class SenderEmailServiceTest {

    @Mock
    private Properties mailProp, credentialProp;

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

    @Test
    public void testSenderConstructor_iOException() throws IOException {

        //Prepare method dependencies
        doNothing().when(mailProp).load(any(InputStream.class));
        doThrow(IOException.class).when(credentialProp).load(any(InputStream.class));

        //Asserts
        SenderEmailService email = new SenderEmailService(mailProp, credentialProp);
        assertNotNull(email);
    }

    @Test
    public void testSendEmail_wrongEmail_throwsMessagingException() {
        //Asserts
        boolean sent = new SenderEmailService(new Properties(), new Properties()).sendEmail("hola", "hola2", "hola3 ", "hola4");

        assertFalse(sent);
    }

}