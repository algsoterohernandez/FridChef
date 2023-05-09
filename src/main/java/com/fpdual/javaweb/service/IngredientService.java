package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;

import java.util.List;

public class IngredientService {

    private final FridChefApiClient fridChefApiClient;

    public IngredientService() {
        this.fridChefApiClient = new FridChefApiClient();
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
}
