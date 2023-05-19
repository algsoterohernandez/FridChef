package com.fpdual.javaweb.client;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.exceptions.UserAlreadyExistsException;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.*;

import static jakarta.ws.rs.client.Entity.entity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FridChefApiClientTest {

    @Mock
    private WebTarget webTarget;
    private UserDto exampleUserDto;
    @Mock
    private Invocation.Builder builder;
    @Mock
    private Response response;
    private static FridChefApiClient fridChefApiClient;

    @Mock
    private Invocation.Builder invocationBuilder;


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
        when(response.getStatus()).thenReturn(200);
        when(response.readEntity(UserDto.class)).thenReturn(exampleUserDto);

        //Execute method
        UserDto userDtoRs = fridChefApiClient.createUser(exampleUserDto);

        //Asserts
        assertNotNull(userDtoRs);

    }

    @Test
    public void testCreaterUser_validUserDto_userAlreadyExistsException() throws ExternalErrorException, UserAlreadyExistsException {
        //String errorMessage = "El usuario ya existe en el sistema.";

        /*//Prepare method dependencies
        when(webTarget.path(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(entity(any(), MediaType.APPLICATION_JSON))).thenReturn(response);
        when(response.getStatus()).thenReturn(304);
        when(mock(UserAlreadyExistsException.class).getMessage()).thenReturn(errorMessage);

        //Execute method
        UserDto userDtoRs = fridChefApiClient.createUser(exampleUserDto);

        //Asserts
        assertNotNull(userDtoRs);
        assertTrue(userDtoRs.isAlreadyExists());*/
    }

    @Test///////////////////////////////////
    public void testCreaterUser_validUserDto_ExternalErrorException() throws ExternalErrorException, UserAlreadyExistsException {

        //Prepare method dependencies


        //Execute method
       //UserDto userDtoRs = fridChefApiClient.createUser(exampleUserDto);

        //Asserts
        //assertNotNull(userDtoRs);
        //assertTrue(userDtoRs.isAlreadyExists());

    }

    @Test
    public void testDeleteUser_validEmail_userDeletedTrue() throws Exception {

        //Prepare method dependencies


        //Execute method
        //boolean deleted = fridChefApiClient.deleteUser(exampleUserDto.getEmail());

        //Asserts
        //assertTrue(deleted);

    }

    @Test
    public void testDeleteUser_validEmail_userDeletedFalse() throws Exception {

        //Prepare method dependencies


        //Execute method
        //boolean deleted = fridChefApiClient.deleteUser(exampleUserDto.getEmail());

        //Asserts
        //assertTrue(!deleted);

    }

    @Test
    public void testFindUser_validEmailPassword_userDtoNotNull() throws ExternalErrorException {

        //Prepare method dependencies


        //Execute method
        //UserDto userDtoRs = fridChefApiClient.findUser(exampleUserDto.getEmail(), exampleUserDto.getPassword());

        //Asserts
        //assertNotNull(userDtoRs);
        //assertTrue(userDtoRs.getEmail().equals("example@a.com") && userDtoRs.getPassword().equals("example"));

    }

    @Test
    public void testFindUser_validEmailPassword_userDtoNull() throws ExternalErrorException {

        //Prepare method dependencies


        //Execute method
        //UserDto userDtoRs = fridChefApiClient.findUser(exampleUserDto.getEmail(), exampleUserDto.getPassword());

        //Asserts
        //assertNotNull(userDtoRs);


    }

    @Test
    public void testFindUser_validEmailPassword_ExternalErrorException() throws ExternalErrorException {

        //Prepare method dependencies


        //Execute method

        //Asserts
        //assertThrows(ExternalErrorException.class, () -> fridChefApiClient.findUser(exampleUserDto.getEmail(), exampleUserDto.getPassword()));
    }
    @Test
    public void testFindAllIngredients_dReturnListOfIngredients_WhenSuccessful() throws ExternalErrorException {
        // Arrange: Configuración del comportamiento esperado del objeto mock
        List<IngredientDto> expectedIngredients = Arrays.asList(
                IngredientDto.builder().id(1).name("Tomate").build(),
                IngredientDto.builder().id(2).name("Lechuga").build()
        );

        when(webTarget.path("ingredients")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);
        when(invocationBuilder.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(200);
        when(response.readEntity(new GenericType<List<IngredientDto>>() {})).thenReturn(expectedIngredients);

        // Act: Invocación del método a testear
        List<IngredientDto> actualIngredients = fridChefApiClient.findAllIngredients();

        // Assert: Verificación de los resultados
        assertEquals(expectedIngredients, actualIngredients);
        verify(webTarget, times(1)).path("ingredients");
        verify(webTarget, times(1)).request(MediaType.APPLICATION_JSON);
        verify(invocationBuilder, times(1)).get();
    }

}