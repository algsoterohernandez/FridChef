package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.exceptions.UserAlreadyExistsException;
import com.fpdual.javaweb.web.servlet.dto.UserDto;

public class UserService {

    private final FridChefApiClient apiClient;


    public UserService(FridChefApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public UserDto registerUser(UserDto userDto) throws ExternalErrorException {

        try {
            userDto = apiClient.createUser(userDto);

        } catch (UserAlreadyExistsException uaee) {
            if (userDto != null) {
                userDto.setAlreadyExists(true);
            }

        } catch (ExternalErrorException eee) {
            System.out.println(eee.getMessage());
            throw eee;
        }

        return userDto;

    }

    public boolean unregisterUser(String email) throws Exception{
        boolean deleted;

        try {
            deleted = apiClient.deleteUser(email);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }

        return deleted;

    }

    public UserDto findUser(String email, String password) throws Exception{
        UserDto userDto;

        try {
            userDto = apiClient.findUser(email, password);

        } catch (ExternalErrorException eee) {
            System.out.println(eee.getMessage());
            throw eee;
        }

        return userDto;

    }
}