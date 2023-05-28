package com.fpdual.javaweb.client;

import com.fpdual.javaweb.enums.HttpStatus;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.exceptions.UserAlreadyExistsException;
import com.fpdual.javaweb.web.servlet.dto.*;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    @BeforeEach
    public void init() {

        fridChefApiClient = new FridChefApiClient(webTarget);

        exampleUserDto = new UserDto();
        exampleUserDto.setName("example");
        exampleUserDto.setSurname1("aaaa");
        exampleUserDto.setSurname2("bbbbb");
        exampleUserDto.setPassword("example");
        exampleUserDto.setEmail("example@a.com");


    }

    @Test
    public void testCreaterUser_validUserDto_userDtoNotNull() throws ExternalErrorException, UserAlreadyExistsException {
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
    public void testCreaterUser_validUserDto_userAlreadyExistsException() {

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(entity(any(), MediaType.APPLICATION_JSON))).thenReturn(response);
        when(response.getStatus()).thenReturn(HttpStatus.NOT_MODIFIED.getStatusCode());


        //Asserts
        assertThrows(UserAlreadyExistsException.class, () -> fridChefApiClient.createUser(exampleUserDto));
    }

    @Test
    public void testCreaterUser_validUserDto_ExternalErrorException() {

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(entity(any(), MediaType.APPLICATION_JSON))).thenReturn(response);


        //Asserts
        assertThrows(ExternalErrorException.class, () -> fridChefApiClient.createUser(exampleUserDto));

    }

    @Test
    public void testDeleteUser_validEmail_userDeletedTrue() throws Exception {

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
    public void testDeleteUser_validEmail_userDeletedFalse() throws Exception {

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.delete()).thenReturn(response);
        when(response.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());

        //Execute method
        boolean deleted = fridChefApiClient.deleteUser(exampleUserDto.getEmail());

        //Asserts
        assertTrue(!deleted);

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
        assertTrue(userDtoRs.getEmail().equals("example@a.com") && userDtoRs.getPassword().equals("example"));

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
        when(response.readEntity(new GenericType<List<IngredientDto>>() {})).thenReturn(expectedIngredients);

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
        when(response.readEntity(new GenericType<List<AllergenDto>>() {})).thenReturn(expectedAllergens);

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
        when(response.readEntity(new GenericType<List<RecipeDto>>() {})).thenReturn(expectedRecipes);
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
        when(response.readEntity(new GenericType<List<RecipeDto>>() {})).thenReturn(expectedRecipes);
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
}