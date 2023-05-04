package com.fpdual.javaweb.service;

import com.fpdual.javaweb.exceptions.UserAlreadyExistsException;
import com.fpdual.javaweb.persistence.connector.MySQLConnector;
import com.fpdual.javaweb.persistence.dao.UserDao;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import com.fpdual.javaweb.persistence.manager.UserManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.Date;

public class UserService {

    private final MySQLConnector connector;
    private final UserManager userManager;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private MessageDigest md5;

    public UserService(MySQLConnector connector, UserManager userManager) {
        this.connector = connector;
        this.userManager = userManager;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public UserDto registerUser(UserDto userDto) {

        try (Connection con = connector.getMySQLConnection()) {

            UserDao userDao = this.userManager.insertUser(con, mapToDao(userDto));
            userDto = mapToDto(userDao);


        } catch (UserAlreadyExistsException e) {
            userDto.setAlreadyExists(true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return userDto;

    }

    public boolean unregisterUser(String email) {
        boolean deleted = false;

        try (Connection con = connector.getMySQLConnection()) {

            deleted = this.userManager.deleteUser(con, email);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return deleted;

    }

    public UserDto findUser(String email, String password) {
        UserDto userDto = null;

        try (Connection con = connector.getMySQLConnection()) {

            UserDao userDao = this.userManager.findByEmailPassword(con, email, password);
            userDto = mapToDto(userDao);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return userDto;

    }


    public UserDao mapToDao(UserDto userDto) {
        UserDao userDao = new UserDao();

        userDao.setId(userDto.getId());
        userDao.setName(userDto.getName());
        userDao.setSurname1(userDto.getSurname1());
        userDao.setSurname2(userDto.getSurname2());
        userDao.setEmail(userDto.getEmail());
        userDao.setPassword(userDto.getPassword());
        userDao.setCreateTime(new Date(System.currentTimeMillis()));

        return userDao;
    }

    public UserDto mapToDto(UserDao userDao) {
        UserDto userDto = new UserDto();

        userDto.setId(userDao.getId());
        userDto.setName(userDao.getName());
        userDto.setSurname1(userDao.getSurname1());
        userDto.setSurname2(userDao.getSurname2());
        userDto.setEmail(userDao.getEmail());
        userDto.setPassword(userDao.getPassword());

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