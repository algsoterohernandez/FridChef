package com.fpdual.javaweb.client;

import com.fpdual.javaweb.enums.HttpStatus;
import com.fpdual.javaweb.enums.RecipeStatus;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.exceptions.AlreadyExistsException;
import com.fpdual.javaweb.web.servlet.dto.*;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static jakarta.ws.rs.client.Entity.entity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FridChefApiClientTest {

    @InjectMocks
    private static FridChefApiClient fridChefApiClient;
    @Mock
    private WebTarget webTarget;
    @Mock
    private Invocation.Builder builder;
    @Mock
    private Response response;
    @Mock
    private Invocation.Builder invocationBuilder;
    private UserDto exampleUserDto;
    private RecipeDto exampleRecipeDto;
    private IngredientDto exampleIngredientDto;

    @BeforeEach
    public void init() {

        fridChefApiClient = new FridChefApiClient(webTarget);

        exampleUserDto = new UserDto();
        exampleUserDto.setName("example");
        exampleUserDto.setSurname1("aaaa");
        exampleUserDto.setSurname2("bbbbb");
        exampleUserDto.setPassword("example");
        exampleUserDto.setEmail("example@a.com");

        exampleRecipeDto = new RecipeDto();
        exampleRecipeDto.setId(8);
        exampleRecipeDto.setStatus(RecipeStatus.PENDING.getStatus());

        exampleIngredientDto = new IngredientDto();
        exampleIngredientDto.setId(5);
        exampleIngredientDto.setName("Tomate");
    }

    @Test
    public void testCreateUser_validUserDto_userDtoNotNull() throws ExternalErrorException, AlreadyExistsException {
        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(entity(any(), MediaType.APPLICATION_JSON))).thenReturn(response);
        when(response.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(response.readEntity(UserDto.class)).thenReturn(exampleUserDto);

        //Execute method
        UserDto userDtoRs = fridChefApiClient.createUser(exampleUserDto);

        //Asserts
        assertNotNull(userDtoRs);

    }

    @Test
    public void testCreateUser_validUserDto_userAlreadyExistsException() {

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(entity(any(), MediaType.APPLICATION_JSON))).thenReturn(response);
        when(response.getStatus()).thenReturn(HttpStatus.NOT_MODIFIED.getStatusCode());


        //Asserts
        assertThrows(AlreadyExistsException.class, () -> fridChefApiClient.createUser(exampleUserDto));
    }

    @Test
    public void testCreateUser_validUserDto_ExternalErrorException() {

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(entity(any(), MediaType.APPLICATION_JSON))).thenReturn(response);


        //Asserts
        assertThrows(ExternalErrorException.class, () -> fridChefApiClient.createUser(exampleUserDto));

    }

    @Test
    public void testDeleteUser_validEmail_userDeletedTrue(){

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.delete()).thenReturn(response);
        when(response.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(response.readEntity(boolean.class)).thenReturn(true);

        //Execute method
        boolean deleted = fridChefApiClient.deleteUser(exampleUserDto.getEmail());

        //Asserts
        assertTrue(deleted);

    }

    @Test
    public void testDeleteUser_validEmail_userDeletedFalse(){

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.delete()).thenReturn(response);
        when(response.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());

        //Execute method
        boolean deleted = fridChefApiClient.deleteUser(exampleUserDto.getEmail());

        //Asserts
        assertFalse(deleted);

    }

    @Test
    public void testFindUser_validEmailPassword_userDtoNotNull() throws ExternalErrorException {

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(entity(any(), MediaType.APPLICATION_JSON))).thenReturn(response);
        when(response.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(response.readEntity(UserDto.class)).thenReturn(exampleUserDto);

        //Execute method
        UserDto userDtoRs = fridChefApiClient.findUser(exampleUserDto.getEmail(), exampleUserDto.getPassword());

        //Asserts
        assertNotNull(userDtoRs);
    }

    @Test
    public void testFindUser_validEmailPassword_userDtoNull() throws ExternalErrorException {

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(entity(any(), MediaType.APPLICATION_JSON))).thenReturn(response);
        when(response.getStatus()).thenReturn(HttpStatus.NO_CONTENT.getStatusCode());

        //Execute method
        UserDto userDtoRs = fridChefApiClient.findUser(exampleUserDto.getEmail(), exampleUserDto.getPassword());

        //Asserts
        assertNull(userDtoRs);
    }

    @Test
    public void testFindUser_validEmailPassword_ExternalErrorException() {

        ///Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(entity(any(), MediaType.APPLICATION_JSON))).thenReturn(response);

        //Asserts
        assertThrows(ExternalErrorException.class, () -> fridChefApiClient.findUser(exampleUserDto.getEmail(), exampleUserDto.getPassword()));
    }

    @Test
    public void testFindAllIngredients_ReturnListOfIngredients_WhenSuccessful() throws ExternalErrorException {
        // Arrange: Configuración del comportamiento esperado del objeto mock
        List<IngredientDto> expectedIngredients = Arrays.asList(
                IngredientDto.builder().id(1).name("Tomate").build(),
                IngredientDto.builder().id(2).name("Lechuga").build()
        );

        when(webTarget.path("ingredients")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);
        when(invocationBuilder.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(response.readEntity(new GenericType<List<IngredientDto>>() {
        })).thenReturn(expectedIngredients);

        // Act: Invocación del método a testear
        List<IngredientDto> actualIngredients = fridChefApiClient.findAllIngredients();

        // Assert: Verificación de los resultados
        assertEquals(expectedIngredients, actualIngredients);
        verify(webTarget, times(1)).path("ingredients");
        verify(webTarget, times(1)).request(MediaType.APPLICATION_JSON);
        verify(invocationBuilder, times(1)).get();
    }

    @Test
    void testFindAllAllergens_ReturnListOfAllergens_WhenSuccessful() throws ExternalErrorException {
        // Arrange: Configuración del comportamiento esperado del objeto mock
        List<AllergenDto> expectedAllergens = Arrays.asList(
                AllergenDto.builder().id(1).name("Gluten").build(),
                AllergenDto.builder().id(2).name("Pescado").build()
        );
        Response mockResponse = Response.ok(expectedAllergens).build();

        when(webTarget.path("allergens")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);
        when(invocationBuilder.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(response.readEntity(new GenericType<List<AllergenDto>>() {
        })).thenReturn(expectedAllergens);

        // Act: Invocación del método a testear
        List<AllergenDto> actualAllergens = fridChefApiClient.findAllAllergens();

        // Assert: Verificación de los resultados
        assertEquals(expectedAllergens, actualAllergens);
        verify(webTarget, times(1)).path("allergens");
        verify(webTarget, times(1)).request(MediaType.APPLICATION_JSON);
        verify(invocationBuilder, times(1)).get();
    }

    @Test
    public void testFindRecipeById_ReturnRecipeById_WhenSuccessful() throws ExternalErrorException {
        // Arrange: Configuración del comportamiento esperado del objeto mock

        int id = 1;
        RecipeDto expectedRecipe = new RecipeDto( /* Se crea una nueva receta para el test */);
        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(response.readEntity(RecipeDto.class)).thenReturn(expectedRecipe);
        when(webTarget.path("recipes/" + id)).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(response);

        // Act: Invocación del método a testear
        RecipeDto actualRecipe = fridChefApiClient.findRecipeById(id);

        // Assert: Verificación de los resultados
        assertEquals(expectedRecipe, actualRecipe);
        verify(webTarget, times(1)).path("recipes/" + id);
        verify(webTarget, times(1)).request(MediaType.APPLICATION_JSON);
        verify(builder, times(1)).get();
    }

    @Test
    public void testFindRecipeById_Error() {
        // Arrange: Configuración del comportamiento esperado del objeto mock
        int id = 1;
        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());
        when(webTarget.path("recipes/" + id)).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(response);

        // Assert: Verificación de que se lanza la excepción esperada
        assertThrows(ExternalErrorException.class, () -> {
            // Act: Invocación del método a testear
            fridChefApiClient.findRecipeById(id);
        });

        // Assert: Verificación de que se llamaron a los métodos adecuados
        verify(webTarget, times(1)).path("recipes/" + id);
        verify(webTarget, times(1)).request(MediaType.APPLICATION_JSON);
        verify(builder, times(1)).get();
    }

    @Test
    public void testFindByIngredients_ReturnListOfRecipesByIngredients_WhenSuccessful() throws ExternalErrorException {
        // Arrange: Configuración del comportamiento esperado del objeto mock
        List<String> ingredientsList = new ArrayList<>();
        ingredientsList.add("Aguacate");
        ingredientsList.add("Tomate");
        ingredientsList.add("Lechuga");
        RecipeFilterDto recipeFilterDto = new RecipeFilterDto();
        recipeFilterDto.setIngredients(ingredientsList);
        List<RecipeDto> expectedRecipes = new ArrayList<>();
        expectedRecipes.add(new RecipeDto(/* create a sample recipe for testing */));
        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(response.readEntity(new GenericType<List<RecipeDto>>() {
        })).thenReturn(expectedRecipes);
        when(webTarget.path("recipes/findbyingredients")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(Entity.entity(recipeFilterDto, MediaType.APPLICATION_JSON))).thenReturn(response);

        // Act: Invocación del método a testear
        List<RecipeDto> actualRecipes = fridChefApiClient.findByIngredients(ingredientsList);

        // Assert: Verificación de los resultados
        assertEquals(expectedRecipes, actualRecipes);
        verify(webTarget, times(1)).path("recipes/findbyingredients");
        verify(webTarget, times(1)).request(MediaType.APPLICATION_JSON);
        verify(builder, times(1)).post(Entity.entity(recipeFilterDto, MediaType.APPLICATION_JSON));
    }

    @Test
    public void testFindByIngredients_Error() {
        // Arrange: Configuración del comportamiento esperado del objeto mock
        List<String> ingredientsList = new ArrayList<>();
        ingredientsList.add("Aguacate");
        ingredientsList.add("Tomate");
        ingredientsList.add("Lechuga");
        RecipeFilterDto recipeFilterDto = new RecipeFilterDto();
        recipeFilterDto.setIngredients(ingredientsList);
        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());
        when(webTarget.path("recipes/findbyingredients")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(Entity.entity(recipeFilterDto, MediaType.APPLICATION_JSON))).thenReturn(response);

        // Assert: Verificación de que se lanza la excepción esperada
        assertThrows(ExternalErrorException.class, () -> {
            // Act: Invocación del método a testear
            fridChefApiClient.findByIngredients(ingredientsList);
        });

        // Assert: Verificación de que se llamaron a los métodos adecuados
        verify(webTarget, times(1)).path("recipes/findbyingredients");
        verify(webTarget, times(1)).request(MediaType.APPLICATION_JSON);
        verify(builder, times(1)).post(Entity.entity(recipeFilterDto, MediaType.APPLICATION_JSON));
    }

    @Test
    public void testFindRecipeSuggestions_ReturnListOfRecipeSuggestions_WhenSuccessful() throws ExternalErrorException {
        // Arrange: Configuración del comportamiento esperado del objeto mock
        List<String> ingredientsList = new ArrayList<>();
        ingredientsList.add("Aguacate");
        ingredientsList.add("Tomate");
        ingredientsList.add("Lechuga");
        RecipeFilterDto recipeFilterDto = new RecipeFilterDto();
        recipeFilterDto.setIngredients(ingredientsList);
        List<RecipeDto> expectedRecipes = new ArrayList<>();
        expectedRecipes.add(new RecipeDto(/* create a sample recipe for testing */));
        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(response.readEntity(new GenericType<List<RecipeDto>>() {
        })).thenReturn(expectedRecipes);
        when(webTarget.path("recipes/findSuggestions")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(Entity.entity(recipeFilterDto, MediaType.APPLICATION_JSON))).thenReturn(response);

        // Act: Invocación del método a testear
        List<RecipeDto> actualRecipes = fridChefApiClient.findRecipeSuggestions(ingredientsList);

        // Assert: Verificación de los resultados
        assertEquals(expectedRecipes, actualRecipes);
        verify(webTarget, times(1)).path("recipes/findSuggestions");
        verify(webTarget, times(1)).request(MediaType.APPLICATION_JSON);
        verify(builder, times(1)).post(Entity.entity(recipeFilterDto, MediaType.APPLICATION_JSON));
    }

    @Test
    public void testFindRecipeSuggestions_Error() {
        // Arrange: Configuración del comportamiento esperado del objeto mock
        List<String> ingredientsList = new ArrayList<>();
        ingredientsList.add("Tomate");
        ingredientsList.add("Aguacate");
        ingredientsList.add("Lechuga");
        RecipeFilterDto recipeFilterDto = new RecipeFilterDto();
        recipeFilterDto.setIngredients(ingredientsList);
        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());
        when(webTarget.path("recipes/findSuggestions")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(Entity.entity(recipeFilterDto, MediaType.APPLICATION_JSON))).thenReturn(response);

        // Assert: Verificación de que se lanza la excepción esperada
        assertThrows(ExternalErrorException.class, () -> {
            // Act: Invocación del método a testear
            fridChefApiClient.findRecipeSuggestions(ingredientsList);
        });

        // Assert: Verificación de que se llamaron a los métodos adecuados
        verify(webTarget, times(1)).path("recipes/findSuggestions");
        verify(webTarget, times(1)).request(MediaType.APPLICATION_JSON);
        verify(builder, times(1)).post(Entity.entity(recipeFilterDto, MediaType.APPLICATION_JSON));
    }
    @Test
    public void testFindByStatusPending_listRecipeDtoNull() throws ExternalErrorException {

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(HttpStatus.NO_CONTENT.getStatusCode());

        //Execute method
        List<RecipeDto> recipeDtoListRs = fridChefApiClient.findByStatusPending();

        //Asserts
        assertNull(recipeDtoListRs);
    }

    @Test
    public void testFindByStatusPending_listRecipeDtoExternalErrorException() {

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(response);

        //Asserts
        assertThrows(ExternalErrorException.class, () -> fridChefApiClient.findByStatusPending());
    }

    @Test
    public void testUpdateRecipeStatus_validRecipeDto_recipeDtoNotNull() throws ExternalErrorException, AlreadyExistsException {

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(response.readEntity(RecipeDto.class)).thenReturn(exampleRecipeDto);

        //Execute method
        RecipeDto recipeDtoRs = fridChefApiClient.updateRecipeStatus(exampleRecipeDto.getId(), exampleRecipeDto.getStatus());

        //Asserts
        assertNotNull(recipeDtoRs);
    }

    @Test
    public void testUpdateRecipeStatus_validRecipeDto_recipeDtoAlreadyExistsException() {

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(HttpStatus.NOT_MODIFIED.getStatusCode());

        //Asserts
        assertThrows(AlreadyExistsException.class,
                () -> fridChefApiClient.updateRecipeStatus(exampleRecipeDto.getId(), exampleRecipeDto.getStatus()));
    }

    @Test
    public void testUpdateRecipeStatus_validRecipeDto_recipeDtoExternalErrorException() {

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(response);

        //Asserts
        assertThrows(ExternalErrorException.class,
                () -> fridChefApiClient.updateRecipeStatus(exampleRecipeDto.getId(), exampleRecipeDto.getStatus()));
    }

    @Test
    public void testDeleteIngredient_validId_ingredientDeletedTrue() {

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.delete()).thenReturn(response);
        when(response.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(response.readEntity(boolean.class)).thenReturn(true);

        //Execute method
        boolean deleted = fridChefApiClient.deleteIngredient(exampleRecipeDto.getId());

        //Asserts
        assertTrue(deleted);

    }

    @Test
    public void testDeleteIngredient_validId_ingredientDeletedFalse() {

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.delete()).thenReturn(response);

        //Execute method
        boolean deleted = fridChefApiClient.deleteIngredient(exampleRecipeDto.getId());

        //Asserts
        assertFalse(deleted);
    }

    @Test
    public void testCreateIngredient_validIngredientDto_userDtoNotNull() throws ExternalErrorException {
        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(entity(any(), MediaType.APPLICATION_JSON))).thenReturn(response);
        when(response.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(response.readEntity(IngredientDto.class)).thenReturn(exampleIngredientDto);

        //Execute method
        IngredientDto ingredientDtoRs = fridChefApiClient.createIngredient(exampleIngredientDto.getName());

        //Asserts
        assertNotNull(ingredientDtoRs);

    }

    @Test
    public void testCreateIngredient_validIngredientDto_userDtoExternalErrorException(){

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(entity(any(), MediaType.APPLICATION_JSON))).thenReturn(response);


        //Asserts
        assertThrows(ExternalErrorException.class, () -> fridChefApiClient.createIngredient(exampleIngredientDto.getName()));

    }


    /**
     * Caso de prueba para el método {@link FridChefApiClient#findRecipesByCategory(int)}.
     * Verifica que el método devuelve una lista de DTO de recetas cuando se proporciona un ID de categoría válido.
     * El resultado esperado es una lista no vacía de DTO de recetas.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método
     */
    @Test
    public void testFindRecipesByCategory_validIdCategory_returnNonEmptyRecipeDtoList() throws ExternalErrorException{
        // Configuración del test
        int idCategory = 1;

       List<RecipeDto> expectedRecipes = new ArrayList<>();

       //simulacion respueta con Mockito
       Response responseMock = Mockito.mock(Response.class);
       when (webTarget.path(any(String.class))).thenReturn(webTarget);
       when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
       when(builder.get()).thenReturn(responseMock);

       //configuracion del comportamiento de la respuesta simulada
       when (responseMock.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
       when(responseMock.readEntity(new GenericType<List<RecipeDto>>(){})).thenReturn(expectedRecipes);

       //ejecucion del método bajo prueba
       List<RecipeDto> result = fridChefApiClient.findRecipesByCategory(idCategory);

       //verificacion del resultado
       assertEquals(expectedRecipes, result);
    }

    /**
     * Caso de prueba para el método {@link FridChefApiClient#findRecipesByCategory(int)}.
     * Verifica que el método devuelve una lista vacía de DTO de recetas cuando se proporciona un ID de categoría válido,
     * pero no hay recetas disponibles para esa categoría.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método
     */
    @Test
    public void testFindRecipeByCategory_validIdCategory_returnEmptyRecipeDtoList()throws ExternalErrorException{
        // Configuración del test
        int idCategory = 1;
        //simulacion respueta con Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(responseMock);

        //configuracion del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.NO_CONTENT.getStatusCode());

        // Ejecucion del método bajo prueba
        List<RecipeDto> result = fridChefApiClient.findRecipesByCategory(idCategory);

        // Verificación del resultado
        assertTrue(result.isEmpty());
    }

    /**
     * Caso de prueba para el método {@link FridChefApiClient#findRecipesByCategory(int)}.
     * Verifica que el método lanza una excepción de tipo ExternalErrorException cuando se proporciona un ID de categoría válido,
     * pero ocurre un error interno en el servidor.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método
     */
    @Test
    public void testFindRecipeByCategory_validIdCategory_returnThrowExternalErrorException() throws ExternalErrorException{
        // Configuración del test
        int idCategory = 1;

        // Simulacion respuesta con Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(responseMock);

        // Configuracion del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());

        //Ejecución y validación implícitas al llamar al método dentro del lambda de "assertThrows"
        assertThrows(ExternalErrorException.class, () -> {
            fridChefApiClient.findRecipesByCategory(idCategory);
        });
    }

    /**
     * Caso de prueba para el método {@link FridChefApiClient#createRecipe(RecipeDto)}.
     * Verifica que el método createRecipe devuelve un RecipeDto cuando se proporciona un RecipeDto válido.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método
     */
    @Test
    public void testCreateRecipe_validRecipeDto_returnRecipeDto() throws ExternalErrorException{
        //Configuracion del test
        RecipeDto recipeDto = new RecipeDto();
        RecipeDto expectedRecipeDto = new RecipeDto();

        //Simulacion respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path("recipes/")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(any(Entity.class))).thenReturn(responseMock);

        //Configuracion del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(responseMock.readEntity(RecipeDto.class)).thenReturn(expectedRecipeDto);

        //Ejecucion del metodo bajo prueba
        RecipeDto result = fridChefApiClient.createRecipe(recipeDto);

        //Verificacion del resultado
        assertEquals(expectedRecipeDto, result);
    }

    /**
     * Caso de prueba para el método {@link FridChefApiClient#createRecipe(RecipeDto)}.
     * Verifica que el método createRecipe lanza una ExternalErrorException cuando ocurre un error interno en el servidor.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método
     */
    @Test
    public void testCreateRecipe_validRecipeDto_returnThrowExternalErrorException() throws ExternalErrorException{
        //Configuracion del test
        RecipeDto recipeDto = new RecipeDto();

        //Simulacion respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path("recipes/")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(any(Entity.class))).thenReturn(responseMock);

        //Configuracion del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());

        //Ejecución y validación implícitas al llamar al método dentro del lambda de "assertThrows"
        assertThrows(ExternalErrorException.class,() ->{
            fridChefApiClient.createRecipe(recipeDto);
        });
    }

    /**
     * Prueba unitaria para el método {@link FridChefApiClient#findCategories()}.
     * Verifica que se devuelva una lista de CategoryDto válida cuando la respuesta es exitosa.
     *
     * @throws ExternalErrorException si ocurre un error externo.
     */
    @Test
    public void testFindCategories_validResponse_returnCategoryDtoList() throws ExternalErrorException{
        //Configuracion del test
        List<CategoryDto> expectedCategories = new ArrayList<>();
        expectedCategories.add(new CategoryDto());
        expectedCategories.add(new CategoryDto());

        //Simulacion respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path("category/")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(responseMock);

        //Configuracion del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(responseMock.readEntity(new GenericType<List<CategoryDto>>(){})).thenReturn(expectedCategories);

        //Ejecucion del metodo bajo prueba
        List<CategoryDto>result = fridChefApiClient.findCategories();
        //Verificacion del resultado
        assertEquals(expectedCategories, result);
    }

    /**
     * Prueba unitaria para el método {@link FridChefApiClient#findCategories()}.
     * Verifica que se devuelva una lista vacía de CategoryDto cuando la respuesta es sin contenido.
     *
     * @throws ExternalErrorException si ocurre un error externo.
     */
    @Test
    public void testFindCategories_noContent_returnEmptyCategoryDtoList()throws ExternalErrorException{
        //Configuración y simulacion respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path("category/")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(responseMock);

        //Configuracion del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.NO_CONTENT.getStatusCode());

        //Ejecucion del metodo bajo prueba
        List<CategoryDto> result = fridChefApiClient.findCategories();

        //Verificacion del resultado
        assertTrue(result.isEmpty());
    }

    /**
     * Prueba unitaria para el método {@link FridChefApiClient#findCategories()}.
     * Verifica que se lance una excepción ExternalErrorException cuando la respuesta es un error interno del servidor.
     *
     * @throws ExternalErrorException si ocurre un error externo.
     */
    @Test
    public void testFindCategories_internalServerError_returnThrowExternalErrorException() throws ExternalErrorException{
        //Configuración y simulación respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path("category/")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(responseMock);

        //Configuracion del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());

        //Ejecución y validación implícitas al llamar al método dentro del lambda de "assertThrows"
        assertThrows(ExternalErrorException.class, () ->{
            fridChefApiClient.findCategories();
        });
    }

    /**
     * Prueba unitaria para verificar que el método {@link FridChefApiClient#findFavorites(List<Integer>)}
     * devuelve una lista válida de RecipeDto cuando se proporcionan IDs válidos.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método
     */
    @Test
    public void testFindFavorites_validIds_returnRecipeDtoList() throws ExternalErrorException{
        //Configuracion del test
        List<Integer> ids = List.of(1,2,3);
        List<RecipeDto>expectedRecipes = new ArrayList<>();

        //Simulacion respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.queryParam(any(String.class), any())).thenReturn(webTarget);
        when(webTarget.request(any(String.class))).thenReturn(builder);
        when(builder.get()).thenReturn(responseMock);

        //Configuracion del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(responseMock.readEntity(new GenericType<List<RecipeDto>>(){})).thenReturn(expectedRecipes);

        //Ejecucion del metodo bajo prueba
        List<RecipeDto> result = fridChefApiClient.findFavorites(ids);

        //Verificacion del resultado
        assertEquals(expectedRecipes, result);
    }

    /**
     * Prueba unitaria para verificar que el método {@link FridChefApiClient#findFavorites(List<Integer>)}
     * lanza una excepción ExternalErrorException cuando ocurre un error interno en el servidor.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método
     */
    @Test
    public void testFindFavorite_internalServerError_returnThrowExternalError()throws ExternalErrorException{
        //Configuracion del test
        List<Integer> ids = List.of(1,2,3);
        List<RecipeDto>expectedRecipes = new ArrayList<>();

        //Simulacion respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.queryParam(any(String.class), any())).thenReturn(webTarget);
        when(webTarget.request(any(String.class))).thenReturn(builder);
        when(builder.get()).thenReturn(responseMock);

        //Configuracion del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());

        //Ejecución y validación implícitas al llamar al método dentro del lambda de "assertThrows"
        assertThrows(ExternalErrorException.class, () ->{
            fridChefApiClient.findFavorites(ids);
        });



    }


    //Configuracion del test
    //Simulacion respuesta utilizando Mockito
    //Configuracion del comportamiento de la respuesta simulada
    //Ejecucion del metodo bajo prueba
    //Verificacion del resultado
}
