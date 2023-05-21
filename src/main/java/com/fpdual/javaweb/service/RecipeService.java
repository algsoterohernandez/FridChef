package com.fpdual.javaweb.service;
import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;

import jakarta.ws.rs.core.Response;
import java.util.List;

public class RecipeService {

    private final FridChefApiClient fridChefApiClient;

    public RecipeService() {
        this.fridChefApiClient = new FridChefApiClient();
    }

    /*public List<RecipeDto> findRecipesByCategory(String category) {
        List<RecipeDto> recipeDtoList = null;
        try {
            Response response = fridChefApiClient.(category);
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                recipeDtoList = response.readEntity(List.class);
            }
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return recipeDtoList;
    }*/


    public List<RecipeDto> findRecipesByIngredients(List<String> ingredientList) {
        List<RecipeDto> recipeDtoList = null;
        try {
            Response response = (Response) fridChefApiClient.findByIngredients(ingredientList);
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                recipeDtoList = response.readEntity(List.class);
            }
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return recipeDtoList;
    }

}

