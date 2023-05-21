package com.fpdual.javaweb.email;


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
public class SenderEmailTest {

    @Mock
    private Properties mailProp, credentialProp;

    @Test
    public void testSenderConstructor_notNull() throws IOException {

        //Prepare method dependencies
        doNothing().when(mailProp).load(any(InputStream.class));
        doNothing().when(credentialProp).load(any(InputStream.class));


       //Execute method
       SenderEmail senderEmail = new SenderEmail(mailProp, credentialProp);

       //Asserts
        assertNotNull(senderEmail);

    }

    @Test
    public void testSenderConstructor_iOException() throws IOException {

        //Prepare method dependencies
        doNothing().when(mailProp).load(any(InputStream.class));
        doThrow(IOException.class).when(credentialProp).load(any(InputStream.class));

        //Asserts
        SenderEmail email = new SenderEmail(mailProp, credentialProp);
        assertNotNull(email);
    }

    @Test
    public void testSendEmail_wrongEmail_throwsMessagingException() {
        //Asserts
        boolean sended = new SenderEmail(new Properties(), new Properties()).sendEmail("hola", "hola2", "hola3 ", "hola4");

        assertFalse(sended);
    }

}