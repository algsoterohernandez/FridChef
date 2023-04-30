package com.fpdual.javaweb.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class IngredientDao {
    private int id;
    private String name;
    private List<AllergenDao> allergens;


    public IngredientDao(ResultSet result) {
        try {
            this.id = result.getInt("id");
            this.name = result.getString("name");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<AllergenDao> getAllergens() {
        return allergens;
    }
}
