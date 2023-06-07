package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.exceptions.AlreadyExistsException;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Clase de pruebas unitarias para la clase UserService.
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private static UserService userService;
    @Mock
    private FridChefApiClient apiClient;
    private UserDto exampleUserDto;

    @BeforeEach
    public void init() {
        userService = new UserService(apiClient);

        exampleUserDto = new UserDto();
        exampleUserDto.setName("example");
        exampleUserDto.setSurname1("aaaa");
        exampleUserDto.setSurname2("bbbbb");
        exampleUserDto.setPassword("example");
        exampleUserDto.setEmail("example@a.com");

    }

    /**
     * Prueba unitaria para el constructor de UserService.
     * Debe devolver una instancia no nula de UserService.
     */
    @Test
    public void testUserServiceConstructor() {

        UserService userService = new UserService(apiClient);
        assertNotNull(userService);

    }

    /**
     * Prueba unitaria para el método registerUser().
     * Debe devolver un UserDto no nulo cuando la llamada al cliente FridChefApiClient tiene éxito.
     *
     * @throws ExternalErrorException   si ocurre un error durante la comunicación externa.
     * @throws AlreadyExistsException   si el usuario ya existe.
     */

    @Test
    public void testRegisterUser_validUserDto_userDtoNotNull() throws ExternalErrorException, AlreadyExistsException {

        //Prepare method dependencies
        when(apiClient.createUser(any())).thenReturn(exampleUserDto);

        //Execute method
        UserDto userDtoRs = userService.registerUser(exampleUserDto);

        //Asserts
        assertNotNull(userDtoRs);
        assertTrue(userDtoRs.getName().equals("example"));

    }

    /**
     * Prueba unitaria para el método registerUser().
     * Debe lanzar AlreadyExistsException cuando el usuario ya existe.
     *
     * @throws ExternalErrorException   si ocurre un error durante la comunicación externa.
     * @throws AlreadyExistsException   si el usuario ya existe.
     */
    @Test
    public void testRegisterUser_validUserDto_userDtoAlreadyExistsException() throws ExternalErrorException, AlreadyExistsException {

        //Prepare method dependencies
        when(apiClient.createUser(any())).thenThrow(AlreadyExistsException.class);

        //Execute method
        UserDto userDtoRs = userService.registerUser(exampleUserDto);

        //Asserts
        assertNotNull(userDtoRs);
        assertTrue(userDtoRs.isAlreadyExists());

    }

    /**
     * Prueba unitaria para el método registerUser().
     * Debe lanzar ExternalErrorException cuando ocurre un error durante la comunicación externa.
     *
     * @throws ExternalErrorException   si ocurre un error durante la comunicación externa.
     * @throws AlreadyExistsException   si el usuario ya existe.
     */
    @Test
    public void testRegisterUser_validUserDto_userDtoExternalErrorException() throws ExternalErrorException, AlreadyExistsException {

        //Prepare method dependencies
        when(apiClient.createUser(any())).thenThrow(ExternalErrorException.class);

        //Asserts
        assertThrows(ExternalErrorException.class, () -> userService.registerUser(exampleUserDto));

    }

    /**
     * Prueba unitaria para el método unregisterUser().
     * Debe devolver true cuando se elimina el usuario correctamente.
     */
    @Test
    public void testUnregisterUser_validEmail_userDeletedTrue()  {

        //Prepare method dependencies
        when(apiClient.deleteUser(any())).thenReturn(true);

        //Execute method
        boolean deleted = userService.unregisterUser(exampleUserDto.getEmail());

        //Asserts
        assertTrue(deleted);

    }

    /**
     * Prueba unitaria para el método unregisterUser().
     * Debe devolver false cuando no se puede eliminar el usuario.
     */
    @Test
    public void testUnregisterUser_validEmail_userDeletedFalse()  {

        //Prepare method dependencies
        when(apiClient.deleteUser(any())).thenReturn(false);

        //Execute method
        boolean deleted = userService.unregisterUser(exampleUserDto.getEmail());

        //Asserts
        assertTrue(!deleted);

    }

    /**
     * Prueba unitaria para el método unregisterUser().
     * Debe lanzar una excepción cuando ocurre un error durante la eliminación del usuario.
     *
     * @throws Exception si ocurre un error durante la eliminación del usuario.
     */
    @Test
    public void testUnregisterUser_validEmail_userException(){

        //Prepare method dependencies
        when(apiClient.deleteUser(any())).thenThrow();

        //Asserts
        assertThrows(Exception.class, () -> userService.unregisterUser(exampleUserDto.getEmail()));

    }

    /**
     * Prueba unitaria para el método findUser().
     * Debe devolver un UserDto no nulo cuando la llamada al cliente FridChefApiClient tiene éxito.
     *
     * @throws Exception si ocurre un error durante la búsqueda del usuario.
     */
    @Test
    public void testFindUser_validUserDto_userDtoNotNull() throws Exception {

        //Prepare method dependencies
        when(apiClient.findUser(any(), any())).thenReturn(exampleUserDto);

        //Execute method
        UserDto userDtoRs = userService.findUser(exampleUserDto.getEmail(), exampleUserDto.getPassword());

        //Asserts
        assertNotNull(userDtoRs);
        assertTrue(userDtoRs.getEmail().equals("example@a.com") &&
                userDtoRs.getPassword().equals("example"));

    }

    /**
     * Prueba unitaria para el método findUser().
     * Debe lanzar ExternalErrorException cuando ocurre un error durante la búsqueda del usuario.
     *
     * @throws Exception si ocurre un error durante la búsqueda del usuario.
     */
    @Test
    public void testFindUser_validUserDto_userDtoExternalErrorException() throws Exception {

        //Prepare method dependencies
        when(apiClient.findUser(any(), any())).thenThrow(ExternalErrorException.class);

        //Asserts
        assertThrows(ExternalErrorException.class, () -> userService.findUser(exampleUserDto.getEmail(), exampleUserDto.getPassword()));

    }
}