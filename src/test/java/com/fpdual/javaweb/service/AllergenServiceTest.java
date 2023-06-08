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
                new AllergenDto(1, "gluten"),
                new AllergenDto(2, "pescado")
        );

        when(fridChefApiClient.findAllAllergens()).thenReturn(allergenList);

        // Act
        List<AllergenDto> actualAllergens = allergenService.findAllAllergens();

        // Assert
        assertEquals(allergenList, actualAllergens);
        verify(fridChefApiClient, times(1)).findAllAllergens();
    }
}
