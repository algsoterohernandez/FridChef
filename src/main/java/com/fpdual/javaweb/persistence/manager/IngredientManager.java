package com.fpdual.javaweb.persistence.manager;

import com.fpdual.javaweb.persistence.connector.MySQLConnector;
import com.fpdual.javaweb.persistence.dao.IngredientDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class IngredientManager {

    //buscar todo

    public List<IngredientDao> findAll() throws SQLException, ClassNotFoundException {
        Connection con = new MySQLConnector().getMySQLConnection();

        try (Statement stm = con.createStatement()) {
            ResultSet result = stm.executeQuery("select * from ingredient order by name ASC");

            result.beforeFirst();

            List<IngredientDao> ingredients = new ArrayList<>();

            while (result.next()) {
                ingredients.add(new IngredientDao(result));
            }

            return ingredients;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            con.close();
        }
    }
    public List<IngredientDao> findByIngredients() throws SQLException, ClassNotFoundException {
        Connection con = new MySQLConnector().getMySQLConnection();

        try (Statement stm = con.createStatement()) {

            ResultSet result = stm.executeQuery("select * from ingredient order by name ASC");

            result.beforeFirst();

            List<IngredientDao> ingredients = new ArrayList<>();

            while (result.next()) {
                ingredients.add(new IngredientDao(result));
            }

            return ingredients;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            con.close();
        }
    }


}

