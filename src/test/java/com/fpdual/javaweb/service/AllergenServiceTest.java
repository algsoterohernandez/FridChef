package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.AllergenDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AllergenServiceTest {
    @Mock
    private FridChefApiClient fridChefApiClient;

    private AllergenService allergenService;

    @BeforeEach
    public void setUp() {
         allergenService = new AllergenService(fridChefApiClient);
    }

    @Test
    public void testFindAllAllergens_returnListOfAllergens_whenSuccessful() throws ExternalErrorException {
        // Arrange
        List<AllergenDto> allergenList = Arrays.asList(
                new AllergenDto(1,"gluten"),
                new AllergenDto(2,"pescado")
        );

        when(fridChefApiClient.findAllAllergens()).thenReturn(allergenList);

        // Act
        List<AllergenDto> actualAllergens = allergenService.findAllAllergens();

        // Assert
        assertEquals(allergenList, actualAllergens);
        verify(fridChefApiClient, times(1)).findAllAllergens();
    }

    @ExtendWith(MockitoExtension.class)
    public static class SenderEmailServiceTest {

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
            boolean sended = new SenderEmailService(new Properties(), new Properties()).sendEmail("hola", "hola2", "hola3 ", "hola4");

            assertFalse(sended);
        }

    }
}
