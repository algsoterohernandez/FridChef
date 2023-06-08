package com.fpdual.javaweb.service;

import com.fpdual.javaweb.exceptions.ExternalErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.web.servlet.dto.CategoryDto;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private FridChefApiClient fridChefApiClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryService(fridChefApiClient);
    }

    @Test
    public void testGetAllCategories_validResponse_returnCategories() throws ExternalErrorException {
        // Configuración del test
        List<CategoryDto> expectedCategories = new ArrayList<>();
        expectedCategories.add(new CategoryDto(1, "legumbres"));
        expectedCategories.add(new CategoryDto(2, "pastas"));

        // Simulación de respuesta utilizando Mockito
        when(fridChefApiClient.findCategories()).thenReturn(expectedCategories);

        // Ejecución del método bajo prueba
        List<CategoryDto> result = categoryService.getAllCategories();

        // Verificación del resultado
        assertFalse(expectedCategories.isEmpty());

        // Verificar que categories contiene las categorías de prueba agregadas a expectedCategories
        assertTrue(expectedCategories.contains(new CategoryDto(1, "legumbres")));
        assertTrue(expectedCategories.contains(new CategoryDto(2, "pastas")));
    }

    @Test
    void testGetAllCategories_externalErrorException_throwExternalErrorException() throws ExternalErrorException {
        // Configuración y simulacion de respuesta utilizando Mockito
        when(fridChefApiClient.findCategories()).thenThrow(ExternalErrorException.class);

        //Ejecución del método bajo prueba
        List<CategoryDto> result = categoryService.getAllCategories();
        assertTrue(result.isEmpty());
    }
}