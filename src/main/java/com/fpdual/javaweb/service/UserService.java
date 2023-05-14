package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.exceptions.UserAlreadyExistsException;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.security.MessageDigest;

public class UserService {

    private final FridChefApiClient apiClient;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private MessageDigest md5;

    public UserService(FridChefApiClient apiClient) {
        this.apiClient = apiClient;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
        UserDto userDto = null;

        try {
            userDto = apiClient.findUser(email, password);

        } catch (ExternalErrorException eee) {
            System.out.println(eee.getMessage());
            throw eee;
        }

        return userDto;

    }

    public String encryptPassword(String password) {
        md5.update(password.getBytes());
        byte[] digest = md5.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

}