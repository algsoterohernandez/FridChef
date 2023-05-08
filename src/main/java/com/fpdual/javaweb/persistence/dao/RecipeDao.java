package com.fpdual.javaweb.persistence.dao;

import lombok.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

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
    private Connection connection;

    public RecipeDao(Connection connection) {
        this.connection = connection;
    }
    public RecipeDao(int idCategory){
        this.idCategory= idCategory;
    }
    public RecipeDao(ResultSet result) {
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


    public void createRecipe(RecipeDao recipe) throws SQLException {
        String query = "INSERT INTO recipes (name, description, difficulty, time, unit_time, category_id, create_time, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, recipe.getName());
        statement.setString(2, recipe.getDescription());
        statement.setInt(3, recipe.getDifficulty());
        statement.setInt(4, recipe.getTime());
        statement.setString(5, recipe.getUnitTime());
        statement.setInt(6, recipe.getIdCategory());
        statement.setDate(7, new java.sql.Date(recipe.getCreateTime().getTime()));
        statement.setBlob(8, recipe.getImage());
        statement.executeUpdate();
        statement.close();
    }

    public void updateRecipe(RecipeDao recipe) throws SQLException {
        String query = "UPDATE recipes SET name = ?, description = ?, difficulty = ?, time = ?, unit_time = ?, category_id = ?, create_time = ?, image = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, recipe.getName());
        statement.setString(2, recipe.getDescription());
        statement.setInt(3, recipe.getDifficulty());
        statement.setInt(4, recipe.getTime());
        statement.setString(5, recipe.getUnitTime());
        statement.setInt(6, recipe.getIdCategory());
        statement.setDate(7, new java.sql.Date(recipe.getCreateTime().getTime()));
        statement.setBlob(8, recipe.getImage());
        statement.setInt(9, recipe.getId());
        statement.executeUpdate();
        statement.close();
    }

    public void deleteRecipe(int id) throws SQLException {
        String query = "DELETE FROM recipes WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
    }

    public RecipeDao getRecipeById(int id) throws SQLException {
        RecipeDao recipe = null;
        String query = "SELECT * FROM recipes WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            recipe = new RecipeDao(result);
        }
        result.close();
        statement.close();
        return recipe;
    }


    public List<RecipeDao> getAllRecipes() throws SQLException {
        List<RecipeDao> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipes";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);
        while (result.next()) {
            RecipeDao recipe = new RecipeDao(result);
            recipes.add(recipe);
        }
        result.close();
        statement.close();
        return recipes;
    }



    public List<RecipeDao> getRecipesByIngredient(String ingredientName) throws SQLException {
        List<RecipeDao> recipes = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = connection.prepareStatement("SELECT r.id, r.name, r.description, r.difficulty, r.time, r.unitTime, r.idCategory, r.createTime, r.image FROM recipe r JOIN recipe_ingredient ri ON r.id = ri.idRecipe JOIN ingredient i ON i.id = ri.idIngredient WHERE i.name = ?");
            statement.setString(1, ingredientName);
            result = statement.executeQuery();

            Map<Integer, RecipeDao> recipeMap = new HashMap<>();

            while (result.next()) {
                int recipeId = result.getInt("id");

                if (!recipeMap.containsKey(recipeId)) {
                    RecipeDao recipe = new RecipeDao(result);
                    recipeMap.put(recipeId, recipe);
                    recipes.add(recipe);
                }

                IngredientDao ingredient = new IngredientDao(result);
                recipeMap.get(recipeId).getIngredients().add(ingredient);
            }

        } finally {
            try {
                if (result != null) {
                    result.close();
                }

                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return recipes;
    }

    public List<RecipeDao> getRecipesByCategory(int categoryId) {
        List<RecipeDao> recipes = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            connection = connection;
            statement = connection.prepareStatement("SELECT id, name, description, difficulty, time, unitTime, idCategory, createTime, image FROM recipe WHERE idCategory = ?");
            statement.setInt(1, categoryId);
            result = statement.executeQuery();

            while (result.next()) {
                RecipeDao recipe = new RecipeDao(result);
                recipes.add(recipe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return recipes;
    }

}



