package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.AllergenDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
public class AllergenServiceTest {
    @Mock
    private FridChefApiClient fridChefApiClient;

    private AllergenService allergenService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        allergenService = new AllergenService(fridChefApiClient);
    }

    @Test
    public void testFindAllAllergens() throws ExternalErrorException {
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
}
