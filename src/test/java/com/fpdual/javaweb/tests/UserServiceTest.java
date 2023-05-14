package com.fpdual.javaweb.tests;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.exceptions.UserAlreadyExistsException;
import com.fpdual.javaweb.service.UserService;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

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

    @Test
    public void testUserServiceConstructor() {

        UserService userService = new UserService(apiClient);
        assertNotNull(userService);

    }

    @Test
    public void testRegisterUser_validUserDto_userDtoNotNull() throws ExternalErrorException, UserAlreadyExistsException {

        //Prepare method dependencies
        when(apiClient.createUser(any())).thenReturn(exampleUserDto);

        //Execute method
        UserDto userDtoRs = userService.registerUser(exampleUserDto);

        //Asserts
        assertNotNull(userDtoRs);
        assertTrue(userDtoRs.getName().equals("example"));

    }

    @Test
    public void testRegisterUser_validUserDto_userDtoAlreadyExistsException() throws ExternalErrorException, UserAlreadyExistsException {

        //Prepare method dependencies
        when(apiClient.createUser(any())).thenThrow(UserAlreadyExistsException.class);

        //Execute method
        UserDto userDtoRs = userService.registerUser(exampleUserDto);

        //Asserts
        assertNotNull(userDtoRs);
        assertTrue(userDtoRs.isAlreadyExists());

    }

    @Test
    public void testRegisterUser_validUserDto_userDtoExternalErrorException() throws ExternalErrorException, UserAlreadyExistsException {

        //Prepare method dependencies
        when(apiClient.createUser(any())).thenThrow(ExternalErrorException.class);

        //Execute methods

        //Asserts
        assertThrows(ExternalErrorException.class, () -> userService.registerUser(exampleUserDto));

    }

    @Test
    public void testUnregisterUser_validEmail_userDeletedTrue() throws Exception {

        //Prepare method dependencies
        when(apiClient.deleteUser(any())).thenReturn(true);

        //Execute method
        boolean deleted = userService.unregisterUser(exampleUserDto.getEmail());

        //Asserts
        assertTrue(deleted);

    }

    @Test
    public void testUnregisterUser_validEmail_userDeletedFalse() throws Exception {

        //Prepare method dependencies
        when(apiClient.deleteUser(any())).thenReturn(false);

        //Execute method
        boolean deleted = userService.unregisterUser(exampleUserDto.getEmail());

        //Asserts
        assertTrue(!deleted);

    }

    @Test
    public void testUnregisterUser_validEmail_userException() throws Exception {

        //Prepare method dependencies
        when(apiClient.deleteUser(any())).thenThrow(Exception.class);

        //Execute method

        //Asserts
        assertThrows(Exception.class, () -> userService.unregisterUser(exampleUserDto.getEmail()));

    }

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

    @Test
    public void testFindUser_validUserDto_userDtoExternalErrorException() throws Exception {

        //Prepare method dependencies
        when(apiClient.findUser(any(), any())).thenThrow(ExternalErrorException.class);

        //Execute method

        //Asserts
        assertThrows(ExternalErrorException.class, () -> userService.findUser(exampleUserDto.getEmail(), exampleUserDto.getPassword()));

    }

    @Test
    public void testEncryptPassword_validString_encryptedString() {

        //Prepare method dependencies

        //Execute method
        String rs = userService.encryptPassword("example123");

        //Asserts
        assertNotNull(rs);
        assertTrue(rs.equals("7df065c23f49f57077f9113611d6d877"));

    }

    @Test
    public void testEncryptPassword_nullString_nullPointerException() {

        //Prepare method dependencies

        //Execute method

        //Asserts
        assertThrows(NullPointerException.class, () -> userService.encryptPassword(null));

    }

}