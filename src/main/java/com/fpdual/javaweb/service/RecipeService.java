package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;

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
    public List<RecipeDto> findRecipesByCategory(int idCategory) {
        List<RecipeDto> recipeDtoList = null;
        try {
            Response response = (Response) apiClient.(idCategory);
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                recipeDtoList = response.readEntity(new GenericType<List<RecipeDto>>() {});
            }
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return recipeDtoList;
    }

    public List<RecipeDto> findRecipesByIngredients(List<String> ingredientList) {
          List<RecipeDto> recipeDtoList = null;
          try {
              Response response = (Response) apiClient.findByIngredients(ingredientList);
              if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                  recipeDtoList = response.readEntity(List.class);
              }
          } catch (ExternalErrorException e) {
              System.out.println(e.getMessage());
          }
          return recipeDtoList;
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

}
