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
import java.util.Collections;
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

    /**
     * Prueba unitaria para el método {@link RecipeService#findAllRecipesByCategoryId(int)}
     * cuando se buscan las recetas por una categoría válida y se retorna una lista de RecipeDto.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testFindAllRecipesByCategoryId_validIntidCategory_returnRecipeDtoList() throws ExternalErrorException {
        //Configuracion del test y simulacion respuesta utilizando Mockito
        int idCategory = 1;
        List<RecipeDto> expectedRecipeList = Arrays.asList(
                RecipeDto.builder().id(1).name("Arroz frito").build(),
                RecipeDto.builder().id(2).name("pasta carbonara").build(),
                RecipeDto.builder().id(3).name("lentejas").build()
        );

        //Configuracion del comportamiento de la respuesta simulada
        when(apiClient.findRecipesByCategory(idCategory)).thenReturn(expectedRecipeList);

        //Ejecucion del metodo bajo prueba
        List<RecipeDto> actualRecipeList = recipeService.findAllRecipesByCategoryId(idCategory);

        //Verificacion del resultado
        assertEquals(expectedRecipeList, actualRecipeList);
        verify(apiClient, times(1)).findRecipesByCategory(idCategory);
    }

    /**
     * Prueba unitaria para el método {@link RecipeService#findAllRecipesByCategoryId(int)}
     * cuando se busca una categoría que no tiene recetas y se retorna una lista vacía de RecipeDto.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testFindAllRecipesByCategoryId_emptyList_returnEmptyRecipeDtoList() throws ExternalErrorException {
        //Configuracion del test y simulacion respuesta utilizando Mockito
        int idCategory = 1;
        List<RecipeDto> expectedRecipeList = Collections.emptyList();

        //Configuracion del comportamiento de la respuesta simulada
        when(apiClient.findRecipesByCategory(idCategory)).thenReturn(expectedRecipeList);

        //Ejecucion del metodo bajo prueba
        List<RecipeDto> actualRecipeList = recipeService.findAllRecipesByCategoryId(idCategory);

        //Verificacion del resultado
        assertEquals(expectedRecipeList, actualRecipeList);
        verify(apiClient, times(1)).findRecipesByCategory(idCategory);
    }

    /**
     * Prueba unitaria para el método {@link RecipeService#findAllRecipesByCategoryId(int)}
     * cuando ocurre un error externo al buscar las recetas por una categoría y se captura la excepción.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testFindAllRecipesByCategoryId_catchesExternalErrorException() throws ExternalErrorException {
        //Configuracion del test y simulacion respuesta utilizando Mockito
        int idCategory = 1;
        ExternalErrorException expectedException = new ExternalErrorException("Error al obtener las recetas por esa categoría");

        //Configuracion del comportamiento de la respuesta simulada
        when(apiClient.findRecipesByCategory(idCategory)).thenThrow(expectedException);

        // Ejecución del método bajo prueba
        List<RecipeDto> actualRecipeList = recipeService.findAllRecipesByCategoryId(idCategory);

        // Verificación del resultado
        assertNull(actualRecipeList);
        verify(apiClient, times(1)).findRecipesByCategory(idCategory);
    }

    /**
     * Prueba unitaria para el método {@link RecipeService#registerRecipe(RecipeDto)}
     * cuando el registro de la receta se realiza correctamente.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testRegisterRecipe_validRecipeDto_returnRegisteredRecipeDto () throws ExternalErrorException{
        //Configuracion del test y simulacion respuesta utilizando Mockito
        RecipeDto recipeDto = new RecipeDto();
        when(apiClient.createRecipe(recipeDto)).thenReturn(recipeDto);

        //Ejecucion del metodo bajo prueba
        RecipeDto result = recipeService.registerRecipe(recipeDto);

        //Verificacion del resultado
        assertEquals(recipeDto, result);
        verify(apiClient, times(1)).createRecipe(recipeDto);
    }

    /**
     * Prueba unitaria para el método {@link RecipeService#registerRecipe(RecipeDto)}
     * cuando ocurre un error externo durante el registro de la receta.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testRegisterRecipe_externalErrorOccurred() throws ExternalErrorException {
        // Configuración del test
        RecipeDto recipeDto = new RecipeDto();
        ExternalErrorException exception = new ExternalErrorException("Ha ocurrido un error al registrar la receta");
        when(apiClient.createRecipe(recipeDto)).thenThrow(exception);

        // Ejecución y verificación del resultado
        Throwable throwable = assertThrows(ExternalErrorException.class, () -> {
            recipeService.registerRecipe(recipeDto);
        });

        assertEquals("Ha ocurrido un error al registrar la receta", throwable.getMessage());
        verify(apiClient, times(1)).createRecipe(recipeDto);
    }

    @Test
    void testFindRecipeById_Success() throws ExternalErrorException {
        // Arrange: Configuración del comportamiento esperado del objeto mock
        int id = 1;
        RecipeDto expectedRecipe = RecipeDto.builder()
                .id(id)
                .name("Recipe 1")
                .build();

        when(apiClient.findRecipeById(id, true)).thenReturn(expectedRecipe);

        // Act: Invocación del método a testear
        RecipeDto result = recipeService.findRecipeById(id, true);

        // Assert: Verificación de los resultados
        verify(apiClient).findRecipeById(id, true);
        assertEquals(expectedRecipe, result);
    }

    @Test
    void testFindRecipeById_Error() throws ExternalErrorException {
        // Arrange: Configuración del comportamiento esperado del objeto mock
        int id = 1;
        ExternalErrorException expectedException = new ExternalErrorException("Error");

        when(apiClient.findRecipeById(id, true)).thenThrow(expectedException);

        // Act: Invocación del método a testear
        RecipeDto result = recipeService.findRecipeById(id, true);

        // Assert: Verificación de los resultados
        verify(apiClient).findRecipeById(id, true);
        assertNull(result);
    }

    /**
     * Prueba unitaria para el método {@link RecipeService#findFavorites(List)}
     * cuando se recuperan correctamente las recetas favoritas.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testFindFavorites_validIntegerIdsList_returnRecipesFoundList() throws ExternalErrorException {
        // Configuración del test
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        List<RecipeDto> expectedRecipes = new ArrayList<>();
        expectedRecipes.add(new RecipeDto());
        expectedRecipes.add(new RecipeDto());
        when(apiClient.findFavorites(ids)).thenReturn(expectedRecipes);

        // Ejecución del método bajo prueba
        List<RecipeDto> result = recipeService.findFavorites(ids);

        // Verificación del resultado
        assertEquals(expectedRecipes, result);
        verify(apiClient, times(1)).findFavorites(ids);
    }

    /**
     * Prueba unitaria para el método {@link RecipeService#findFavorites(List)}
     * cuando ocurre un error externo al buscar las recetas favoritas y se captura la excepción.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testFindFavorites_externalErrorOccurred() throws ExternalErrorException {
        // Configuración del test
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ExternalErrorException expectedException = new ExternalErrorException("Error");
        when(apiClient.findFavorites(ids)).thenThrow(expectedException);

        // Ejecución y verificación del resultado
        Throwable throwable = assertThrows(ExternalErrorException.class, () -> {
            recipeService.findFavorites(ids);
        });

        // Verificación de la excepción
        assertEquals("Error", throwable.getMessage());
    }

    /**
     * Prueba unitaria para el método {@link RecipeService#findMostRated(int)}
     * cuando se buscan las recetas mejor valoradas correctamente.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testFindMostRated_validLimit_returnRecipesFoundList() throws ExternalErrorException {
        // Configuración del test
        int limit = 10;
        List<RecipeDto> expectedRecipes = new ArrayList<>();
        expectedRecipes.add(new RecipeDto());
        expectedRecipes.add(new RecipeDto());
        when(apiClient.findMostRated(limit)).thenReturn(expectedRecipes);

        // Ejecución del método bajo prueba
        List<RecipeDto> result = recipeService.findMostRated(limit);

        // Verificación del resultado
        assertEquals(expectedRecipes, result);
        verify(apiClient, times(1)).findMostRated(limit);
    }

    /**
     * Prueba unitaria para el método {@link RecipeService#findMostRated(int)}
     * cuando ocurre un error externo durante la búsqueda de las recetas mejor valoradas.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testFindMostRated_externalErrorOccurred() throws ExternalErrorException {
        // Configuración del test
        int limit = 10;
        ExternalErrorException expectedException = new ExternalErrorException("Error");
        when(apiClient.findMostRated(limit)).thenThrow(expectedException);

        // Ejecución y verificación del resultado
        Throwable throwable = assertThrows(ExternalErrorException.class, () -> {
            recipeService.findMostRated(limit);
        });

        // Verificación de la excepción
        assertEquals("Error", throwable.getMessage());
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