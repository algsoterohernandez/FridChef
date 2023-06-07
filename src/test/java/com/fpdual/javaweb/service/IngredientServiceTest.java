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

    /**
     * Caso de prueba para el método findAllIngredients.
     * Debería devolver una lista de ingredientes obtenida del cliente API.
     *
     * @throws ExternalErrorException Si ocurre un error externo al llamar al cliente API.
     */
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

    /**
     * Caso de prueba para el método findByIngredients.
     * Debería devolver una lista de recetas encontradas por los ingredientes especificados.
     *
     * @throws ExternalErrorException Si ocurre un error externo al llamar al cliente API.
     */
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

    /**
     * Caso de prueba para el método deleteIngredient.
     * Debería eliminar un ingrediente y devolver true.
     */
   @Test
    public void testDeleteIngredient_validId_ingredientDtoTrue()  {

        //Prepare method dependencies
        when(fridChefApiClient.deleteIngredient(anyInt())).thenReturn(true);

        //Execute method
        boolean deleted = ingredientService.deleteIngredient(exampleIngredientDto.getId());

        //Asserts
        assertTrue(deleted);

    }

    /**
     * Caso de prueba para el método deleteIngredient.
     * Debería eliminar un ingrediente y devolver false.
     */
    @Test
    public void testDeleteIngredient_validId_ingredientDtoFalse()  {

        //Prepare method dependencies
        when(fridChefApiClient.deleteIngredient(anyInt())).thenReturn(false);

        //Execute method
        boolean deleted = ingredientService.deleteIngredient(exampleIngredientDto.getId());

        //Asserts
        assertFalse(deleted);
    }

    /**
     * Caso de prueba para el método deleteIngredient.
     * Debería lanzar una excepción al eliminar un ingrediente.
     */
    @Test
    public void testDeleteIngredient_validId_ingredientDtoException(){

        //Prepare method dependencies
        when(fridChefApiClient.deleteIngredient(anyInt())).thenThrow();

        //Asserts
        assertThrows(Exception.class, () -> ingredientService.deleteIngredient(exampleIngredientDto.getId()));

    }

    /**
     * Caso de prueba para el método createIngredient.
     * Debería crear un nuevo ingrediente y devolver el objeto IngredientDto resultante.
     *
     * @throws ExternalErrorException Si ocurre un error externo al llamar al cliente API.
     */
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

    /**
     * Caso de prueba para el método createIngredient.
     * Debería lanzar una excepción de tipo ExternalErrorException al crear un ingrediente.
     *
     * @throws ExternalErrorException Si ocurre un error externo al llamar al cliente API.
     */
    @Test
    public void testCreateIngredient_validName_ingredientDtoExternalErrorException() throws ExternalErrorException {

        //Prepare method dependencies
        when(fridChefApiClient.createIngredient(anyString())).thenThrow(ExternalErrorException.class);

        //Asserts
        assertThrows(ExternalErrorException.class, () -> ingredientService.createIngredient(exampleIngredientDto.getName()));
    }

}