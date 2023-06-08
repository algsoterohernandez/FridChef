package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.ValorationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class ValorationServiceTest {
    @InjectMocks
    private ValorationService valorationService;

    @Mock
    private FridChefApiClient fridChefApiClient;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        valorationService = new ValorationService(fridChefApiClient);
    }

    @Test
    public void testCreateValoration_validValorationDto_returnValorationCreated() throws ExternalErrorException {
        //Configuración del test
        ValorationDto valorationDto = new ValorationDto();
        valorationDto.setId(1);
        valorationDto.setValoration(4.5);
        boolean expected = true;

        //Simulación de respuesta utilizando Mockito
        when(fridChefApiClient.createValoration(valorationDto)).thenReturn(true);

        //Ejecución del método bajo prueba
        boolean actual = valorationService.createValoration(valorationDto);

        //Verificación del resultado
        assertEquals(expected, actual);
        verify(fridChefApiClient, times(1)).createValoration(valorationDto);
    }

    @Test
    public void testCreateValoration_validValorationDto_returnValorationCreatedFailed() throws ExternalErrorException{
        //Configuración del test
        ValorationDto valorationDto = new ValorationDto();
        valorationDto.setId(1);
        valorationDto.setValoration(4.5);
        boolean expected = false;

        //Simulación de respuesta utilizando Mockito
        doThrow(new ExternalErrorException("Ha ocurrido un error creando la valoración")).when(fridChefApiClient).createValoration(valorationDto);

        //Ejecución del método bajo prueba
        boolean actual = valorationService.createValoration(valorationDto);

        //Verificaciónd el resultado
        assertEquals(expected, actual);
        verify(fridChefApiClient, times(1)).createValoration(valorationDto);
    }

    @Test
    public void testFindValorations_validIdRecipeAndLimit_returnValorationsList() throws ExternalErrorException{
        // Configuración del test
        int idRecipe = 1;
        int limit = 10;
        List<ValorationDto> expectedValorations = new ArrayList<>();
        expectedValorations.add(new ValorationDto());
        expectedValorations.add(new ValorationDto());

        // Simulación de respuesta utilizando Mockito
        when(fridChefApiClient.findValorations(idRecipe, limit)).thenReturn(expectedValorations);

        // Ejecución del método bajo prueba
        List<ValorationDto> actualValorations = valorationService.findValorations(idRecipe, limit);

        // Verificación del resultado
        assertEquals(expectedValorations, actualValorations);
        verify(fridChefApiClient, times(1)).findValorations(idRecipe, limit);
    }

    @Test
    public void testFindValorations_externalErrorOccurred() throws ExternalErrorException {
        // Configuración del test
        int idRecipe = 1;
        int limit = 10;
        ExternalErrorException expectedException = new ExternalErrorException("Error al buscar las valoraciones");

        // Simulación de respuesta utilizando Mockito
        when(fridChefApiClient.findValorations(idRecipe, limit)).thenThrow(expectedException);

        // Ejecución y verificación del resultado
        ExternalErrorException actualException = assertThrows(ExternalErrorException.class, () -> {
            valorationService.findValorations(idRecipe, limit);
        });

        // Verificación de la excepción
        assertEquals(expectedException, actualException);
        verify(fridChefApiClient, times(1)).findValorations(idRecipe, limit);
    }
}
