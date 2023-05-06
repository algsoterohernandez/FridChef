package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
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

    public UserDto registerUser(UserDto userDto) {

        try {
            userDto = apiClient.createUser(userDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return userDto;

    }

    public boolean unregisterUser(String email) {
        boolean deleted = false;

        try {
            deleted = apiClient.deleteUser(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return deleted;

    }

    public UserDto findUser(String email, String password) {
        UserDto userDto = null;

        try {

            userDto = apiClient.findUser(email, password);

        } catch (Exception e) {
            System.out.println(e.getMessage());
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