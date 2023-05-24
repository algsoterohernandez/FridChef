package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import com.fpdual.javaweb.web.servlet.dto.IngredientRecipeDto;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    private FridChefApiClient apiClient;

    private RecipeService recipeService;

    @BeforeEach
    public void setUp() {

        recipeService = new RecipeService(apiClient);
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
}
