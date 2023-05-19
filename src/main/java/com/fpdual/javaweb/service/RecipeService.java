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

    public RecipeDto createRecipe(RecipeDto recipeDto) {

        recipeDto.setId(1345);
        return recipeDto;
    }

}
