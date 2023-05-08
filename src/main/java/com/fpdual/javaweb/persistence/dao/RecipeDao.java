package com.fpdual.javaweb.persistence.dao;

import lombok.*;
import java.sql.*;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class RecipeDao {

    private int id;
    private String name;
    private String description;
    private int difficulty;
    private int time;
    private String unitTime;
    private int idCategory;
    private Date createTime;
    private Blob image;
    private List<IngredientDao> ingredients;


    public RecipeDao(ResultSet result){
        try {
            this.id = result.getInt("id");
            this.name = result.getString("name");
            this.description = result.getString("description");
            this.difficulty = result.getInt("difficulty");
            this.time = result.getInt("time");
            this.unitTime = result.getString("unit_time");
            this.idCategory = result.getInt("id_category");
            this.createTime = result.getDate("create_time");
            this.image = result.getBlob("image");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
