package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;

import java.util.List;

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
}
