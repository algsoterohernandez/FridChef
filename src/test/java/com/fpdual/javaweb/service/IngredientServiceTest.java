package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceTest {

    @InjectMocks
    private IngredientService ingredientService;
    @Mock
    private FridChefApiClient fridChefApiClient;
    private IngredientDto exampleIngredientDto;


    @BeforeEach
    public void setUp() {

        ingredientService = new IngredientService(fridChefApiClient);

        exampleIngredientDto = new IngredientDto();
        exampleIngredientDto.setId(5);
        exampleIngredientDto.setName("Tomate");

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
    }

   @Test
    public void testDeleteIngredient_validId_ingredientDtoTrue()  {

        //Prepare method dependencies
        when(fridChefApiClient.deleteIngredient(anyInt())).thenReturn(true);

        //Execute method
        boolean deleted = ingredientService.deleteIngredient(exampleIngredientDto.getId());

        //Asserts
        assertTrue(deleted);

    }

    @Test
    public void testDeleteIngredient_validId_ingredientDtoFalse()  {

        //Prepare method dependencies
        when(fridChefApiClient.deleteIngredient(anyInt())).thenReturn(false);

        //Execute method
        boolean deleted = ingredientService.deleteIngredient(exampleIngredientDto.getId());

        //Asserts
        assertFalse(deleted);
    }

    @Test
    public void testDeleteIngredient_validId_ingredientDtoException(){

        //Prepare method dependencies
        when(fridChefApiClient.deleteIngredient(anyInt())).thenThrow();

        //Asserts
        assertThrows(Exception.class, () -> ingredientService.deleteIngredient(exampleIngredientDto.getId()));

    }

    @Test
    public void testCreateIngredient_validName_ingredientDtoNotNull() throws ExternalErrorException {

        //Prepare method dependencies
        when(fridChefApiClient.createIngredient(anyString())).thenReturn(exampleIngredientDto);

        //Execute method
        IngredientDto ingredientDtoRs = ingredientService.createIngredient(exampleIngredientDto.getName());

        //Asserts
        assertNotNull(ingredientDtoRs);
        assertTrue(ingredientDtoRs.getName().equals("Tomate"));

    }

    @Test
    public void testCreateIngredient_validName_ingredientDtoExternalErrorException() throws ExternalErrorException {

        //Prepare method dependencies
        when(fridChefApiClient.createIngredient(anyString())).thenThrow(ExternalErrorException.class);

        //Asserts
        assertThrows(ExternalErrorException.class, () -> ingredientService.createIngredient(exampleIngredientDto.getName()));
    }

}