package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
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
public class IngredientServiceTest {

    @Mock
    private FridChefApiClient fridChefApiClient;

    private IngredientService ingredientService;

    @BeforeEach
    public void setUp() {
        ingredientService = new IngredientService(fridChefApiClient);
    }

    @Test
    public void testFindAllIngredients_returnListOfIngredients_whenSuccessful() throws ExternalErrorException {
        // Arrange
        List<IngredientDto> expectedIngredients = Arrays.asList(
                IngredientDto.builder().id(1).name("Tomate").build(),
                IngredientDto.builder().id(2).name("Lechuga").build()
        );

        when(fridChefApiClient.findAllIngredients()).thenReturn(expectedIngredients);

        // Act
        List<IngredientDto> actualIngredients = ingredientService.findAllIngredients();

        // Assert
        assertEquals(expectedIngredients, actualIngredients);
        verify(fridChefApiClient, times(1)).findAllIngredients();
    }

    @Test
    public void testFindByIngredients_returnListOfRecipeFoundByIngredients_whenSuccessful() throws ExternalErrorException {
        // Arrange
        List<String> ingredientList = Arrays.asList("Ingredient1", "Ingredient2");

        List<RecipeDto> expectedRecipes = Arrays.asList(
                RecipeDto.builder().id(1).name("Recipe1").build(),
                RecipeDto.builder().id(2).name("Recipe2").build()
        );

        when(fridChefApiClient.findByIngredients(ingredientList)).thenReturn(expectedRecipes);

        // Act
        List<RecipeDto> actualRecipes = ingredientService.findByIngredients(ingredientList);

        // Assert
        assertEquals(expectedRecipes, actualRecipes);
        verify(fridChefApiClient, times(1)).findByIngredients(ingredientList);
    }
}
