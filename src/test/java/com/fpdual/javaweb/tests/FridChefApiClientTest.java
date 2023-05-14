package com.fpdual.javaweb.tests;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.exceptions.UserAlreadyExistsException;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.ws.rs.client.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FridChefApiClientTest {

    @Mock
    private static WebTarget webTarget;
    private UserDto exampleUserDto;
    private static FridChefApiClient fridChefApiClient;

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
        String prediction = webTarget.path("/assign").request().post(Entity.text("-1.5,0.5\n-1,0"))
                .readEntity(String.class);
        //Execute method
        UserDto userDtoRs = fridChefApiClient.createUser(exampleUserDto);

        //Asserts
        assertNotNull(userDtoRs);


    }

    @Test
    public void testCreaterUser_validUserDto_userAlreadyExistsException() throws ExternalErrorException, UserAlreadyExistsException {

        //Prepare method dependencies
        when(fridChefApiClient.createUser(any())).thenThrow(UserAlreadyExistsException.class);

        //Execute method
        UserDto userDtoRs = fridChefApiClient.createUser(exampleUserDto);

        //Asserts
        assertNotNull(userDtoRs);
    }

    @Test
    public void testCreaterUser_validUserDto_ExternalErrorException() throws ExternalErrorException, UserAlreadyExistsException {

        //Prepare method dependencies

        //Execute method


        //Asserts
        assertThrows(ExternalErrorException.class, () -> fridChefApiClient.createUser(exampleUserDto));
    }

    @Test
    public void testDeleteUser_validEmail_userDeletedTrue() throws Exception {

        //Prepare method dependencies
        when(fridChefApiClient.deleteUser(any())).thenReturn(true);

        //Execute method
        boolean deleted = fridChefApiClient.deleteUser(exampleUserDto.getEmail());

        //Asserts
        assertTrue(deleted);
    }

    @Test
    public void testDeleteUser_validEmail_userDeletedFalse() throws Exception {

        //Prepare method dependencies
        when(fridChefApiClient.deleteUser(any())).thenReturn(false);

        //Execute method
        boolean deleted = fridChefApiClient.deleteUser(exampleUserDto.getEmail());

        //Asserts
        assertTrue(!deleted);

    }

    @Test
    public void testFindUser_validEmailPassword_userDtoNotNull() throws ExternalErrorException {

        //Prepare method dependencies
        when(fridChefApiClient.findUser(any(), any())).thenReturn(exampleUserDto);

        //Execute method
        UserDto userDtoRs = fridChefApiClient.findUser(exampleUserDto.getEmail(), exampleUserDto.getPassword());

        //Asserts
        assertNotNull(userDtoRs);
        assertTrue(userDtoRs.getEmail().equals("example@a.com") && userDtoRs.getPassword().equals("example"));

    }

    @Test
    public void testFindUser_validEmailPassword_userDtoNull() throws ExternalErrorException {

        //Prepare method dependencies
        when(fridChefApiClient.findUser(any(), any())).thenReturn(exampleUserDto);

        //Execute method
        UserDto userDtoRs = fridChefApiClient.findUser(exampleUserDto.getEmail(), exampleUserDto.getPassword());

        //Asserts
        assertNotNull(userDtoRs);


    }

    @Test
    public void testFindUser_validEmailPassword_ExternalErrorException() throws ExternalErrorException {

        //Prepare method dependencies
        when(fridChefApiClient.findUser(any(), any())).thenThrow(ExternalErrorException.class);

        //Execute method

        //Asserts
        assertThrows(ExternalErrorException.class, () -> fridChefApiClient.findUser
                (exampleUserDto.getEmail(), exampleUserDto.getPassword()));
    }

}