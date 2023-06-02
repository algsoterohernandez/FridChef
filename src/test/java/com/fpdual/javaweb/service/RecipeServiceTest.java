package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.enums.RecipeStatus;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.IngredientRecipeDto;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @InjectMocks
    private RecipeService recipeService;
    @Mock
    private FridChefApiClient apiClient;

    private RecipeDto exampleRecipeDto;

    @BeforeEach
    public void setUp() {

        recipeService = new RecipeService(apiClient);


        exampleRecipeDto = new RecipeDto();
        exampleRecipeDto.setId(8);
        exampleRecipeDto.setStatus(RecipeStatus.PENDING.getStatus());

    }

    @Test
    public void testFindRecipeSuggestions_returnListOfRecipeSuggestions_whenSuccessful() throws ExternalErrorException {
        // Arrange: Configuración del comportamiento esperado del objeto mock
        List<String> ingredientList = Arrays.asList("Tomate", "Lechuga");

        List<RecipeDto> expectedSuggestions = Arrays.asList(
                // Creación del primer objeto RecipeDto utilizando un builder
                RecipeDto.builder()
                        .id(1)
                        .name("Ensalada de tomate y lechuga")
                        .ingredients(Arrays.asList(
                                // Creación de los objetos IngredientDto dentro de la lista de ingredientes
                                IngredientRecipeDto.builder().idIngredient(1).nameIngredient("Tomate").build(),
                                IngredientRecipeDto.builder().idIngredient(2).nameIngredient("Lechuga").build()
                        ))
                        .build(),

                // Creación del segundo objeto RecipeDto utilizando un builder
                RecipeDto.builder()
                        .id(2)
                        .name("Sopa de tomate")
                        .ingredients(Arrays.asList(
                                // Creación de los objetos IngredientDto dentro de la lista de ingredientes
                                IngredientRecipeDto.builder().idIngredient(1).nameIngredient("Tomate").build(),
                                IngredientRecipeDto.builder().idIngredient(3).nameIngredient("Cebolla").build(),
                                IngredientRecipeDto.builder().idIngredient(4).nameIngredient("Caldo").build()
                        ))
                        .build()
        );

        when(apiClient.findRecipeSuggestions(ingredientList)).thenReturn(expectedSuggestions);

        // Act: Invocación del método a testear
        List<RecipeDto> actualSuggestions = recipeService.findRecipeSuggestions(ingredientList);

        // Assert: Verificación de los resultados
        assertEquals(expectedSuggestions, actualSuggestions);
        verify(apiClient, times(1)).findRecipeSuggestions(ingredientList);
    }

    @Test
    void testFindRecipeById_Success() throws ExternalErrorException {
        // Arrange: Configuración del comportamiento esperado del objeto mock
        int id = 1;
        RecipeDto expectedRecipe = RecipeDto.builder()
                .id(id)
                .name("Recipe 1")
                .build();

        when(apiClient.findRecipeById(id)).thenReturn(expectedRecipe);

        // Act: Invocación del método a testear
        RecipeDto result = recipeService.findRecipeById(id);

        // Assert: Verificación de los resultados
        verify(apiClient).findRecipeById(id);
        assertEquals(expectedRecipe, result);
    }

    @Test
    void testFindRecipeById_Error() throws ExternalErrorException {
        // Arrange: Configuración del comportamiento esperado del objeto mock
        int id = 1;
        ExternalErrorException expectedException = new ExternalErrorException("Error");

        when(apiClient.findRecipeById(id)).thenThrow(expectedException);

        // Act: Invocación del método a testear
        RecipeDto result = recipeService.findRecipeById(id);

        // Assert: Verificación de los resultados
        verify(apiClient).findRecipeById(id);
        assertNull(result);
    }

    @Test
    public void testFindByStatusPending_listRecipeDtoNotNull() throws ExternalErrorException {

        //Prepare method dependencies
        when(apiClient.findByStatusPending()).thenReturn(new ArrayList<>(Arrays.asList(exampleRecipeDto)));

        //Execute method
        List<RecipeDto> recipeDtoListRs = recipeService.findByStatusPending();

        //Asserts
        assertNotNull(recipeDtoListRs);

    }

   @Test
    public void testFindByStatusPending_listRecipeDtoExternalErrorException() throws ExternalErrorException {

        //Prepare method dependencies
       when(apiClient.findByStatusPending()).thenThrow(ExternalErrorException.class);

        //Asserts
        assertThrows(ExternalErrorException.class, () -> recipeService.findByStatusPending());

    }

    @Test
    public void testUpdateRecipeStatus_validIdStatus_recipeDtoNotNull() throws Exception {

        //Prepare method dependencies
        when(apiClient.updateRecipeStatus(anyInt(),anyString())).thenReturn(exampleRecipeDto);

        //Execute method
        RecipeDto recipeDtoRs = recipeService.updateRecipeStatus(exampleRecipeDto.getId(), exampleRecipeDto.getStatus());

        //Asserts
        assertNotNull(recipeDtoRs);

    }

    @Test
    public void testUpdateRecipeStatus_validIdStatus_recipeDtoException() throws Exception {

        //Prepare method dependencies
        when(apiClient.updateRecipeStatus(anyInt(),anyString())).thenThrow(ExternalErrorException.class);

        //Asserts
        assertThrows(ExternalErrorException.class, () -> recipeService.updateRecipeStatus(exampleRecipeDto.getId(), exampleRecipeDto.getStatus()));

    }
}