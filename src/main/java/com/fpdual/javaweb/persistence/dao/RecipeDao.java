package com.fpdual.javaweb.persistence.dao;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class RecipeDao {

    private int id;
    private String name;
    private String description;
    private int difficulty;
    private int time;
    private String unit_time;
    private int id_category;
    private Date crete_time;
    private Blob image;

    public RecipeDao(ResultSet result) {
        try {
            this.id = result.getInt("id");
            this.name = result.getString("name");
            this.description = result.getString("description");
            this.difficulty = result.getInt("difficulty");
            this.time = result.getInt("time");
            this.unit_time = result.getString("unit_time");
            this.id_category = result.getInt("id_category");
            this.crete_time = result.getDate("crete_time");
            this.image = result.getBlob("image");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
