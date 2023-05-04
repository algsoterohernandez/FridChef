package com.fpdual.javaweb.persistence.manager;

import com.fpdual.javaweb.exceptions.UserAlreadyExistsException;
import com.fpdual.javaweb.persistence.dao.UserDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    public UserDao insertUser(Connection con, UserDao user) throws UserAlreadyExistsException{
        try (PreparedStatement stm = con.prepareStatement("INSERT INTO user (name, surname1, surname2, email, password, " +
                "create_time) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {

            stm.setString(1, user.getName());
            stm.setString(2, user.getSurname1());
            stm.setString(3, user.getSurname2());
            stm.setString(4, user.getEmail());
            stm.setString(5, user.getPassword());
            stm.setDate(6, user.getCreateTime());


            stm.executeUpdate();
            ResultSet result = stm.getGeneratedKeys();
            result.next();
            int pk = result.getInt(1);
            user.setId(pk);

            return user;

        } catch (SQLIntegrityConstraintViolationException sqlicve) {
            throw new UserAlreadyExistsException("El ususario se ha registrado con anterioridad.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public boolean deleteUser(Connection con, String email){
        boolean deleted = false;

        try (PreparedStatement stm = con.prepareStatement("DELETE FROM user WHERE EMAIL = ?")) {


            stm.setString(1, email);

            int rowsDeleted = stm.executeUpdate();

            deleted = rowsDeleted > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return deleted;
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
            System.out.println(e.getMessage());
            return null;
        }
    }

    public UserDao findById(Connection con, int id) {

        try (PreparedStatement stm = con.prepareStatement("SELECT * FROM user WHERE ID = ?")) {

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

    public UserDao findByEmailPassword(Connection con, String email, String password) {

        try (PreparedStatement stm = con.prepareStatement("SELECT * FROM user WHERE email = ? and password = ?;")) {

            stm.setString(1, email);
            stm.setString(2, password);
            ResultSet result = stm.executeQuery();
            result.beforeFirst();

            UserDao user = null;
            while (result.next()) {
                user = new UserDao(result);
            }

            return user;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}