package com.fpdual.javaweb.service;

import com.fpdual.javaweb.exceptions.UserAlreadyExistsException;
import com.fpdual.javaweb.persistence.connector.MySQLConnector;
import com.fpdual.javaweb.persistence.dao.UserDao;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import com.fpdual.javaweb.persistence.manager.UserManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class UserService {

    private MySQLConnector connector;
    private UserManager userManager;

    public UserService(MySQLConnector connector, UserManager userManager) {
        this.connector = connector;
        this.userManager = userManager;
    }

    public UserDto registerUser(UserDto userDto) throws SQLException, UserAlreadyExistsException{
        Connection con = null;

        try {
            con = connector.getMySQLConnection();
            UserDao userDao = this.userManager.insertUser(con, mapToDao(userDto));
            userDto = mapToDto(userDao);


        } catch (UserAlreadyExistsException e) {
            userDto.setAlreadyExists(true);

        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe);

        } catch (SQLException sqle) {
            System.out.println(sqle);

        } finally {
            if (con != null) {
                con.close();
            }
            return  userDto;
        }
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

}