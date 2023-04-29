package com.fpdual.javaweb.persistence.manager;

import com.fpdual.javaweb.persistence.dao.UserDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    public UserDao insertUser(Connection con, UserDao user){
        try (PreparedStatement stm = con.prepareStatement("INSERT INTO user (name, surname1, surname2, email, password, " +
                "create_time) VALUES (?,?,?,?,?,?)")) {

            stm.setString(1, user.getName());
            stm.setString(2, user.getSurname1());
            stm.setString(3, user.getSurname2());
            stm.setString(4, user.getEmail());
            stm.setString(5, user.getPassword());
            stm.setDate(6,user.getCreateTime());

            //Si el execute me da problemas puede que tenga que modificar el metodo a void
            ResultSet result = stm.executeQuery();

            UserDao dbuser = new UserDao(result);

            return dbuser;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<UserDao> findAll(Connection con) {
        try (Statement stm = con.createStatement()) {

            ResultSet result = stm.executeQuery("SELECT * FROM user");
            result.beforeFirst();

            List<UserDao> users = new ArrayList<>();
            while (result.next()) {
                users.add(new UserDao(result));
            }

            return users;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserDao findById(Connection con, int id) {

        try (PreparedStatement stm = con.prepareStatement("SELECT * FROM City WHERE ID = ?")) {

            stm.setInt(1, id);
            ResultSet result = stm.executeQuery();
            result.beforeFirst();

            UserDao user = null;
            while (result.next()) {
                user = new UserDao(result);
            }

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}