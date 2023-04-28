package com.fpdual.javaweb.persistence.manager.dao;

import com.fpdual.javaweb.persistence.connector.MySQLConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class IngredientManager {

    //buscar todo

    public List<Ingredient> findAll() throws SQLException, ClassNotFoundException {
        Connection con = new MySQLConnector().getMySQLConnection();

        try (Statement stm = con.createStatement()) {
            ResultSet result = stm.executeQuery("select * from ingredient order by name ASC");

            result.beforeFirst();

            List<Ingredient> ingredients = new ArrayList<>();

            while (result.next()) {
                ingredients.add(new Ingredient(result));
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

