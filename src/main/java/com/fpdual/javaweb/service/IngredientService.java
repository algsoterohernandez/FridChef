package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.CategoryDto;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import com.fpdual.javaweb.web.servlet.dto.ItemDto;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;

import java.util.ArrayList;
import java.util.List;
enum UNIT {
    GR {
        public String toString() {
            return "gr";
        }
    },
    MG,

}
public class IngredientService {

    private final FridChefApiClient fridChefApiClient;

    public IngredientService(FridChefApiClient fridChefApiClient) {
        this.fridChefApiClient = fridChefApiClient;
    }

    public List<IngredientDto> findAllIngredients () {
       List<IngredientDto> ingredientDtoList = null;
        try {
            ingredientDtoList = fridChefApiClient.findAllIngredients();
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return ingredientDtoList;
    }

    public List<RecipeDto> findByIngredients (List<String> ingredientList) {
        List<RecipeDto> recipeList = null;

        try {
            recipeList = fridChefApiClient.findByIngredients(ingredientList);
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return recipeList;
    }


    public List<ItemDto> getAllIngredientUnits() {


        List<ItemDto> items = new ArrayList<>();
        items.add(new ItemDto(UNIT.MG.toString(), "miligramos"));
        items.add(new ItemDto(UNIT.GR.toString(), "gramos"));
        items.add(new ItemDto("ml", "mililitros"));
        items.add(new ItemDto("l", "litros"));
        items.add(new ItemDto("pizca", "pizca"));
        items.add(new ItemDto("cucharadita", "cucharadita"));
        items.add(new ItemDto("cucharada", "cucharada"));
        return items;
    }


}
