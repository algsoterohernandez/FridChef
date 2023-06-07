package com.fpdual.javaweb.client;

import com.fpdual.javaweb.enums.HttpStatus;
import com.fpdual.javaweb.enums.RecipeStatus;
import com.fpdual.javaweb.exceptions.BadRequestException;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.exceptions.AlreadyExistsException;
import com.fpdual.javaweb.web.servlet.dto.*;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    /**
     * Prueba unitaria para el método createUser.
     * Verifica que, al proporcionar un UserDto válido, el método devuelve un UserDto no nulo.
     *
     * @throws ExternalErrorException si se produce un error externo.
     * @throws AlreadyExistsException si el usuario ya existe.
     */
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

    /**
     * Prueba unitaria para el método createUser.
     * Verifica que, al proporcionar un UserDto válido y recibir un código de estado "NOT_MODIFIED",
     * se lance la excepción AlreadyExistsException.
     */
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

    /**
     * Prueba unitaria para el método createUser.
     * Verifica que, al proporcionar un UserDto válido y ocurrir una excepción externa,
     * se lance la excepción ExternalErrorException.
     */
    @Test
    public void testCreateUser_validUserDto_ExternalErrorException() {

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(entity(any(), MediaType.APPLICATION_JSON))).thenReturn(response);

        //Asserts
        assertThrows(ExternalErrorException.class, () -> fridChefApiClient.createUser(exampleUserDto));
    }

    /**
     * Prueba unitaria para el método deleteUser.
     * Verifica que, al proporcionar un email válido y recibir un código de estado "OK",
     * el método devuelve true, indicando que el usuario fue eliminado correctamente.
     */
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

    /**
     * Prueba unitaria para el método deleteUser.
     * Verifica que, al proporcionar un email válido y recibir un código de estado "INTERNAL_SERVER_ERROR",
     * el método devuelve false, indicando que no se pudo eliminar el usuario.
     */
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

    /**
     * Prueba unitaria para el método findUser.
     * Verifica que, al proporcionar un email y contraseña válidos, se obtiene un objeto UserDto no nulo.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
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

    /**
     * Prueba unitaria para el método findUser.
     * Verifica que, al proporcionar un email y contraseña válidos, se obtiene un objeto UserDto nulo debido a un código de estado "NO_CONTENT".
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
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

    /**
     * Prueba unitaria para el método findUser.
     * Verifica que, al proporcionar un email y contraseña válidos, se lanza la excepción ExternalErrorException.
     * Esta excepción se espera cuando ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testFindUser_validEmailPassword_ExternalErrorException() {

        ///Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(entity(any(), MediaType.APPLICATION_JSON))).thenReturn(response);

        //Asserts
        assertThrows(ExternalErrorException.class, () -> fridChefApiClient.findUser(exampleUserDto.getEmail(), exampleUserDto.getPassword()));
    }

    /**
     * Prueba unitaria para el método findAllIngredients.
     * Verifica que, al llamar al método para obtener la lista de ingredientes, se devuelva una lista de ingredientes correctamente.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta exitosa del servidor.
     * Se compara la lista de ingredientes esperada con la lista de ingredientes devuelta por el método.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
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
    }

    /**
     * Prueba unitaria para el método findAllAllergens.
     * Verifica que, al llamar al método para obtener la lista de alérgenos, se devuelva una lista de alérgenos correctamente.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta exitosa del servidor.
     * Se compara la lista de alérgenos esperada con la lista de alérgenos devuelta por el método.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
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

    }

    /**
     * Prueba unitaria para el método findRecipeById.
     * Verifica que, al llamar al método para buscar una receta por su ID, se devuelva la receta correctamente.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta exitosa del servidor.
     * Se compara la receta esperada con la receta devuelta por el método.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testFindRecipeById_ReturnRecipeById_WhenSuccessful() throws ExternalErrorException {
        // Arrange: Configuración del comportamiento esperado del objeto mock
        int id = 1;
        RecipeDto expectedRecipe = new RecipeDto(/* Se crea una nueva receta para el test */);
        Response response = mock(Response.class);
        when(response.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(response.readEntity(RecipeDto.class)).thenReturn(expectedRecipe);

        WebTarget targetMock = mock(WebTarget.class);
        Invocation.Builder builderMock = mock(Invocation.Builder.class);

        when(webTarget.path("recipes/" + id)).thenReturn(webTarget);
        when(webTarget.queryParam(anyString(), any())).thenReturn(targetMock);
        when(targetMock.request(MediaType.APPLICATION_JSON)).thenReturn(builderMock);
        when(builderMock.get()).thenReturn(response);

        // Act: Invocación del método a testear
        RecipeDto actualRecipe = fridChefApiClient.findRecipeById(id, true);

        // Assert: Verificación de los resultados
        assertEquals(expectedRecipe, actualRecipe);
    }

    /**
     * Prueba unitaria para el método findRecipeById en caso de error.
     * Verifica que, al llamar al método para buscar una receta por su ID y se produce un error en el servidor,
     * se lance la excepción ExternalErrorException.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta de error del servidor.
     * Se verifica que se lance la excepción esperada al invocar el método.
     */
    @Test
    public void testFindRecipeById_Error() {
        // Arrange: Configuración del comportamiento esperado del objeto mock
        int id = 1;
        Response response = mock(Response.class);

        when(response.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());
        WebTarget webTarget = mock(WebTarget.class);
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.queryParam(anyString(), any())).thenReturn(webTarget);


        Invocation.Builder requestBuilder = mock(Invocation.Builder.class);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(requestBuilder);

        when(requestBuilder.get()).thenReturn(response);

        FridChefApiClient fridChefApiClient = new FridChefApiClient(webTarget);

        // Assert: Verificación de que se lanza la excepción esperada
        assertThrows(ExternalErrorException.class, () -> {
            // Act: Invocación del método a testear
            fridChefApiClient.findRecipeById(id, true);
        });

    }

    /**
     * Prueba unitaria para el método findByIngredients en caso de éxito.
     * Verifica que, al llamar al método para buscar recetas por ingredientes y se obtiene una respuesta exitosa,
     * se devuelva una lista de recetas esperada.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta exitosa del servidor.
     * Se verifica que la lista de recetas devuelta sea igual a la lista de recetas esperada.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
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
    }

    /**
     * Prueba unitaria para el método findByIngredients en caso de error.
     * Verifica que, al llamar al método para buscar recetas por ingredientes y se obtiene una respuesta de error del servidor,
     * se lance la excepción ExternalErrorException.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta de error del servidor.
     * Se verifica que al invocar el método se lance la excepción esperada.
     */
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
    }

    /**
     * Prueba unitaria para el método findRecipeSuggestions en caso de éxito.
     * Verifica que, al llamar al método para buscar sugerencias de recetas por ingredientes y se obtiene una respuesta exitosa del servidor,
     * se retornen correctamente las sugerencias de recetas esperadas.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta exitosa del servidor.
     * Se verifica que las recetas retornadas coincidan con las recetas esperadas.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
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
    }

    /**
     * Prueba unitaria para el método findRecipeSuggestions en caso de error.
     * Verifica que, al llamar al método para buscar sugerencias de recetas por ingredientes y se obtiene un error del servidor,
     * se lance la excepción ExternalErrorException.
     * Se configura el comportamiento esperado del objeto mock para simular un error del servidor.
     * Se verifica que se lance la excepción esperada al invocar el método.
     */
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
    }

    /**
     * Prueba unitaria para el método findByStatusPending cuando no se encuentran recetas pendientes.
     * Verifica que, al llamar al método para buscar recetas pendientes y no se encuentran recetas,
     * se devuelve una lista nula.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta exitosa sin contenido.
     * Se ejecuta el método y se verifica que se devuelva una lista nula.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
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

    /**
     * Prueba unitaria para el método findByStatusPending cuando se produce una excepción externa.
     * Verifica que se lance una excepción del tipo ExternalErrorException al llamar al método
     * y se produce una excepción externa.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta con error.
     * Se verifica que se lance una excepción del tipo ExternalErrorException al llamar al método.
     */
    @Test
    public void testFindByStatusPending_listRecipeDtoExternalErrorException() {

        //Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(response);

        //Asserts
        assertThrows(ExternalErrorException.class, () -> fridChefApiClient.findByStatusPending());
    }

    /**
     * Prueba unitaria para el método updateRecipeStatus cuando se actualiza correctamente el estado de una receta.
     * Verifica que se devuelva un objeto RecipeDto no nulo al llamar al método y se actualice correctamente el estado de la receta.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta exitosa con un objeto RecipeDto.
     * Se verifica que se devuelva un objeto RecipeDto no nulo al llamar al método.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     * @throws AlreadyExistsException si ya existe una entidad con el mismo estado que se intenta actualizar.
     */
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

    /**
     * Prueba unitaria para el método updateRecipeStatus cuando se produce una excepción AlreadyExistsException al actualizar el estado de una receta.
     * Verifica que se lance una excepción AlreadyExistsException al llamar al método y obtener un código de respuesta NOT_MODIFIED.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta con código NOT_MODIFIED.
     * Se verifica que se lance una excepción AlreadyExistsException al llamar al método.
     */
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

    /**
     * Prueba unitaria para el método updateRecipeStatus cuando se produce una excepción ExternalErrorException al actualizar el estado de una receta.
     * Verifica que se lance una excepción ExternalErrorException al llamar al método y obtener una respuesta no exitosa.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta no exitosa.
     * Se verifica que se lance una excepción ExternalErrorException al llamar al método.
     */
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

    /**
     * Prueba unitaria para el método deleteIngredient cuando se elimina correctamente un ingrediente.
     * Verifica que se devuelva true al llamar al método y obtener una respuesta exitosa con un valor booleano true.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta exitosa con un valor booleano true.
     * Se verifica que se devuelva true al llamar al método.
     */
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

    /**
     * Prueba unitaria para el método deleteIngredient cuando no se puede eliminar un ingrediente.
     * Verifica que se devuelva false al llamar al método y obtener una respuesta no exitosa.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta no exitosa.
     * Se verifica que se devuelva false al llamar al método.
     */
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

    /**
     * Prueba unitaria para el método createIngredient cuando se crea un ingrediente válido.
     * Verifica que se devuelva un objeto IngredientDto no nulo al llamar al método y obtener una respuesta exitosa.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta exitosa.
     * Se verifica que se devuelva un objeto IngredientDto no nulo al llamar al método.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
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

    /**
     * Prueba unitaria para el método createIngredient cuando se produce una excepción ExternalErrorException al crear un ingrediente.
     * Verifica que se lance una excepción ExternalErrorException al llamar al método y obtener una respuesta no exitosa.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta no exitosa.
     * Se verifica que se lance una excepción ExternalErrorException al llamar al método.
     */
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
     * Verifica que el método lanza una excepción de tipo ExternalErrorException cuando se proporciona un ID de categoría válido,
     * pero ocurre un error interno en el servidor.
     */
    @Test
    public void testFindRecipeByCategory_validIdCategory_returnThrowExternalErrorException() {
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
     * Verifica que el método createRecipe lanza una ExternalErrorException cuando ocurre un error interno en el servidor.
     */
    @Test
    public void testCreateRecipe_validRecipeDto_returnThrowExternalErrorException() {
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
     * Verifica que se lance una excepción ExternalErrorException cuando la respuesta es un error interno del servidor.
     */
    @Test
    public void testFindCategories_internalServerError_returnThrowExternalErrorException() {
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
     * Prueba unitaria para el método findFavorites cuando se especifican ids válidos.
     * Verifica que se devuelva una lista de RecipeDto esperada.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta exitosa.
     * Se verifica que la lista devuelta coincida con la lista de RecipeDto esperada.
     *
     * @throws ExternalErrorException si se produce un error externo al llamar al servicio.
     * @throws BadRequestException si se produce una solicitud incorrecta al llamar al servicio.
     */

    @Test
    public void testFindFavorites_validIds_returnRecipeDtoList()throws ExternalErrorException, BadRequestException {
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
     * Prueba unitaria para el método findFavorites cuando se produce un error interno del servidor.
     * Verifica que se lance una excepción de tipo ExternalErrorException.
     * Se configura el comportamiento esperado del objeto mock para simular una respuesta con error interno del servidor.
     * Se verifica que se lance la excepción esperada al llamar al método.
     */
    @Test
    public void testFindFavorite_internalServerError_returnThrowExternalError(){
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

    /**
     * Prueba el método con un límite válido y espera una lista de RecipeDto como resultado.
     *
     * @throws ExternalErrorException si ocurre un error externo
     */
    @Test
    public void testFindMostRated_validLimit_returnRecipeDtoList() throws ExternalErrorException{
        //Configuracion del test
        int limit = 10;
        List<RecipeDto> expectedRecipes = new ArrayList<>();

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
        List<RecipeDto> result = fridChefApiClient.findMostRated(limit);

        //Verificacion del resultado
        assertEquals(expectedRecipes, result);
    }

    /**
     * Prueba el método cuando ocurre un error interno del servidor y espera que se lance una ExternalErrorException.
     */
    @Test
    public void testFindMostRated_internalServerError_returnThrowExternalErrorException(){
        //Configuracion del test
        int limit = 10;

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
            fridChefApiClient.findMostRated(limit);
        });

    }

    /**
     * Con este método se verifica que el método createValoration(ValorationDto) devuelva true cuando la respuesta del servidor
     * tiene un estado exitoso.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testCreateValoration_validValorationDto_returnTrue() throws ExternalErrorException{
        //Configuracion del test
        ValorationDto valorationDto = new ValorationDto();
        valorationDto.setIdRecipe(1);

        //simulacion respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.request(any(String.class))).thenReturn(builder);
        when(builder.post(any(Entity.class))).thenReturn(responseMock);

        //Configuracion del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());

        //Ejecucion del metodo bajo prueba
        Boolean result = fridChefApiClient.createValoration(valorationDto);

        //Verificacion del resultado
        assertTrue(result);

    }

    /**
     * Con este método se verifica que el método createValoration(ValorationDto) lance una ExternalErrorException
     * cuando la respuesta del servidor indica un error interno.
     */
    @Test
    public void testCreateValoration_validValorationDto_returnFalse(){
        //Configuracion del test
        ValorationDto valorationDto = new ValorationDto();
        valorationDto.setIdRecipe(1);

        //Simulacion respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.request(any(String.class))).thenReturn(builder);
        when(builder.post(any(Entity.class))).thenReturn(responseMock);

        //Configuracion del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());

        //Ejecución y validación implícitas al llamar al método dentro del lambda de "assertThrows"
        assertThrows(ExternalErrorException.class, () ->{
            fridChefApiClient.createValoration(valorationDto);
        });
    }

    /**
     * Con este método se verifica que el método findValorations(int, int) devuelva una lista de valoraciones correctamente
     * cuando la respuesta del servidor indica éxito.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testFindValorations_validIdValidLimit_returnValorationList() throws ExternalErrorException{
        //Configuracion del test
        int idRecipe = 1;
        int limit = 10;

        //Creación de la lista simulada de ValorationDto
        List<ValorationDto>valorations = new ArrayList<>();
        valorations.add(new ValorationDto());
        valorations.add(new ValorationDto());

        //Simulacion respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.queryParam(any(String.class), any())).thenReturn(webTarget);
        when(webTarget.request(any(String.class))).thenReturn(builder);
        when(builder.get()).thenReturn(responseMock);

        //Configuracion del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(responseMock.readEntity(any(GenericType.class))).thenReturn(valorations);

        // Configuración del comportamiento del builder
        when(builder.get()).thenReturn(responseMock);

        //Ejecucion del metodo bajo prueba
        List<ValorationDto> result = fridChefApiClient.findValorations(idRecipe,limit);

        //Verificacion del resultado
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    /**
     * cuando se espera obtener una lista de valoraciones válida con un límite alto.
     * Con este método se verifica que el método findValorations(int, int) devuelva una lista de valoraciones correctamente
     * cuando la respuesta del servidor indica éxito y el límite es alto.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testFindValorations_validIdHighLimit_returnValorationList() throws ExternalErrorException {
        // Configuración del test
        int idRecipe = 1;
        int limit = 1000; // Límite alto

        // Creación de la lista simulada de ValorationDto
        List<ValorationDto> valorations = new ArrayList<>();
        valorations.add(new ValorationDto());
        valorations.add(new ValorationDto());

        // Simulación de respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.queryParam(any(String.class), any())).thenReturn(webTarget);
        when(webTarget.request(any(String.class))).thenReturn(builder);
        when(builder.get()).thenReturn(responseMock);

        // Configuración del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(responseMock.readEntity(any(GenericType.class))).thenReturn(valorations);

        // Ejecución del método bajo prueba
        List<ValorationDto> result = fridChefApiClient.findValorations(idRecipe, limit);

        // Verificación del resultado
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    /**
     * cuando se espera obtener una lista de valoraciones válida con un límite bajo.
     * Con este método se verifica que el método findValorations(int, int) devuelva una lista de valoraciones correctamente
     * cuando la respuesta del servidor indica éxito y el límite es bajo.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testFindValorations_validIdLowLimit_returnValorationList() throws ExternalErrorException {
        // Configuración del test
        int idRecipe = 1;
        int limit = 1; // Límite bajo

        // Creación de la lista simulada de ValorationDto
        List<ValorationDto> valorations = new ArrayList<>();
        valorations.add(new ValorationDto());

        // Simulación de respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.queryParam(any(String.class), any())).thenReturn(webTarget);
        when(webTarget.request(any(String.class))).thenReturn(builder);
        when(builder.get()).thenReturn(responseMock);

        // Configuración del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());
        when(responseMock.readEntity(any(GenericType.class))).thenReturn(valorations);

        // Ejecución del método bajo prueba
        List<ValorationDto> result = fridChefApiClient.findValorations(idRecipe, limit);

        // Verificación del resultado
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    /**
     * cuando se recibe una respuesta de error del servidor.
     * Con este método se verifica que el método findValorations(int, int) lance una ExternalErrorException
     * cuando la respuesta del servidor indica un error interno.
     */
    @Test
    public void testFindValorations_errorResponse_throwExternalErrorException() {
        // Configuración del test
        int idRecipe = 1;
        int limit = 10;

        // Simulación de respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.queryParam(any(String.class), any())).thenReturn(webTarget);
        when(webTarget.request(any(String.class))).thenReturn(builder);
        when(builder.get()).thenReturn(responseMock);

        // Configuración del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());

        // Ejecución del método bajo prueba y verificación de la excepción
        assertThrows(ExternalErrorException.class, () -> fridChefApiClient.findValorations(idRecipe, limit));
    }

    /**
     * Con este método se verifica que el método createFavorite(int, int) devuelva true
     * cuando la respuesta del servidor indica éxito.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testCreateFavorite_validParameter_returnTrue() throws ExternalErrorException {
        // Configuración del test
        int idRecipe = 1;
        int idUser = 1;

        // Simulación de respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.request(any(String.class))).thenReturn(builder);
        when(builder.post(any(Entity.class))).thenReturn(responseMock);

        // Configuración del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());

        // Ejecución del método bajo prueba
        boolean result = fridChefApiClient.createFavorite(idRecipe, idUser);

        // Verificación del resultado
        assertTrue(result);
    }

    /**
     * Con este método se verifica que el método createFavorite(int, int) devuelva false
     * cuando la respuesta del servidor indica que el ID de la receta no existe.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testCreateFavorite_nonExistingRecipeId_returnFalse() throws ExternalErrorException {
        // Configuración del test
        int nonExistingId = 9999; // ID de receta inexistente
        int idUser = 1;

        // Simulación de respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.request(any(String.class))).thenReturn(builder);
        when(builder.post(any(Entity.class))).thenReturn(responseMock);

        // Configuración del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());

        // Ejecución del método bajo prueba
        boolean result = fridChefApiClient.createFavorite(nonExistingId, idUser);

        // Verificación del resultado
        assertFalse(result);
    }

    /**
     * Con este método se verifica que el método createFavorite(int, int) devuelva false
     * cuando la respuesta del servidor indica que el ID del usuario no existe.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testCreateFavorite_nonExistingUserId_returnFalse() throws ExternalErrorException {
        // Configuración del test
        int idRecipe = 1;
        int nonExistingId = 9999; // ID de usuario inexistente

        // Simulación de respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.request(any(String.class))).thenReturn(builder);
        when(builder.post(any(Entity.class))).thenReturn(responseMock);

        // Configuración del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());

        // Ejecución del método bajo prueba
        boolean result = fridChefApiClient.createFavorite(idRecipe, nonExistingId);

        // Verificación del resultado
        assertFalse(result);
    }

    /**
     * Con este método se verifica que el método createFavorite(int, int) lance una ExternalErrorException
     * cuando la respuesta del servidor indica un error interno.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testCreateFavorite_errorResponse_throwExternalErrorException() throws ExternalErrorException {
        // Configuración del test
        int idRecipe = 1;
        int idUser = 1;

        // Simulación de respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.request(any(String.class))).thenReturn(builder);
        when(builder.post(any(Entity.class))).thenReturn(responseMock);

        // Configuración del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());

        // Ejecución del método bajo prueba y verificación de la excepción
        boolean result = fridChefApiClient.createFavorite(idRecipe, idUser);

        //Verificación del resultado
        assertFalse(result);
    }

    /**
     * Con este método se verifica que el método deleteFavorite(int, int) devuelva true
     * cuando la respuesta del servidor indica éxito.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testDeleteFavorite_validParameter_returnTrue() throws ExternalErrorException {
        // Configuración del test
        int idRecipe = 1;
        int idUser = 1;

        // Simulación de respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.request(any(String.class))).thenReturn(builder);
        when(builder.delete()).thenReturn(responseMock);

        // Configuración del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.OK.getStatusCode());

        // Ejecución del método bajo prueba
        boolean result = fridChefApiClient.deleteFavorite(idRecipe, idUser);

        // Verificación del resultado
        assertTrue(result);
    }

    /**
     * Con este método se verifica que el método deleteFavorite(int, int) devuelva false
     * cuando la respuesta del servidor indica que el ID de la receta no existe.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testDeleteFavorite_nonExistingRecipeId_returnFalse() throws ExternalErrorException {
        // Configuración del test
        int nonExistingId = 9999; // ID de receta inexistente
        int idUser = 1;

        // Simulación de respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.request(any(String.class))).thenReturn(builder);
        when(builder.delete()).thenReturn(responseMock);

        // Configuración del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());

        // Ejecución del método bajo prueba
        boolean result = fridChefApiClient.deleteFavorite(nonExistingId, idUser);

        // Verificación del resultado
        assertFalse(result);
    }

    /**
     * Con este método se verifica que el método deleteFavorite(int, int) devuelva false
     * cuando la respuesta del servidor indica que el ID del usuario no existe.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testDeleteFavorite_nonExistingUserId_returnFalse() throws ExternalErrorException {
        // Configuración del test
        int idRecipe = 1;
        int nonExistingId = 9999; // ID de usuario inexistente

        // Simulación de respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.request(any(String.class))).thenReturn(builder);
        when(builder.delete()).thenReturn(responseMock);

        // Configuración del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());

        // Ejecución del método bajo prueba
        boolean result = fridChefApiClient.deleteFavorite(idRecipe, nonExistingId);

        // Verificación del resultado
        assertFalse(result);
    }

    /**
     * Con este método se verifica que el método deleteFavorite(int, int) lance una ExternalErrorException
     * cuando la respuesta del servidor indica un error interno.
     *
     * @throws ExternalErrorException si ocurre un error externo durante la ejecución del método.
     */
    @Test
    public void testDeleteFavorite_errorResponse_throwExternalErrorException() throws ExternalErrorException {
        // Configuración del test
        int idRecipe = 1;
        int idUser = 1;

        // Simulación de respuesta utilizando Mockito
        Response responseMock = Mockito.mock(Response.class);
        when(webTarget.path(any(String.class))).thenReturn(webTarget);
        when(webTarget.request(any(String.class))).thenReturn(builder);
        when(builder.delete()).thenReturn(responseMock);

        // Configuración del comportamiento de la respuesta simulada
        when(responseMock.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.getStatusCode());

        // Ejecución del método bajo prueba
        boolean result = fridChefApiClient.deleteFavorite(idRecipe, idUser);

        // Verificación del resultado
        assertFalse(result);
    }
}
