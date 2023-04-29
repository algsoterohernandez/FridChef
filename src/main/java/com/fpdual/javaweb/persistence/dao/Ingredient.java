package com.fpdual.javaweb.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Ingredient {
    private int id;
    private String name;
    private List<Allergen> allergens;


    public Ingredient(ResultSet result) {
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

    public List<Allergen> getAllergens() {
        return allergens;
    }
}
