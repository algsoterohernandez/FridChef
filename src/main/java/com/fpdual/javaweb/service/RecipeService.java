package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;


import java.util.List;

public class RecipeService {

    private final FridChefApiClient apiClient;

    public RecipeService(FridChefApiClient apiClient) {
        this.apiClient = apiClient;
    }
    public List<RecipeDto> findRecipeSuggestions(List<String> ingredientList) {
        List<RecipeDto> recipeSuggestions = null;
        try {
            recipeSuggestions = apiClient.findRecipeSuggestions(ingredientList);
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return recipeSuggestions;
    }
    public List<RecipeDto> findAllRecipesByCategoryId(int idCategory) {
        List<RecipeDto> recipeDtoList = null;
        try {
            recipeDtoList = apiClient.findRecipesByCategory(idCategory);
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return  recipeDtoList;
    }

    public RecipeDto registerRecipe(RecipeDto recipeDto) throws ExternalErrorException{
        try{
            recipeDto = apiClient.createRecipe(recipeDto);

        }catch (ExternalErrorException e){
            System.out.println(e.getMessage());
            throw e;
        }
        return recipeDto;
    }

    public RecipeDto findRecipeById(int id) {
        try {
            RecipeDto recipeDto = apiClient.findRecipeById(id);
            return recipeDto;
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
            return null; // tengo que ver como manejo este error
        }
    }

    public List<RecipeDto> findByStatusPending() throws ExternalErrorException{
        List<RecipeDto> recipeDtoList;

        try {
            recipeDtoList = apiClient.findByStatusPending();

        } catch (ExternalErrorException eee) {
            System.out.println(eee.getMessage());
            throw eee;

        }

        return recipeDtoList;

    }

    public RecipeDto updateRecipeStatus(int id, String status) throws Exception{
        RecipeDto recipeDto;

        try {
            recipeDto = apiClient.updateRecipeStatus(id, status);

        } catch (ExternalErrorException eee) {
            System.out.println(eee.getMessage());
            throw eee;

        }

        return recipeDto;

    }

}