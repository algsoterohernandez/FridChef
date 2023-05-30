package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.enums.Unit;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import com.fpdual.javaweb.web.servlet.dto.ItemDto;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;

import java.util.ArrayList;
import java.util.List;

public class IngredientService {

    private final FridChefApiClient fridChefApiClient;

    public IngredientService(FridChefApiClient fridChefApiClient) {
        this.fridChefApiClient = fridChefApiClient;
    }

    public List<IngredientDto> findAllIngredients() {
        List<IngredientDto> ingredientDtoList = null;
        try {
            ingredientDtoList = fridChefApiClient.findAllIngredients();
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return ingredientDtoList;
    }

    public List<RecipeDto> findByIngredients(List<String> ingredientList) {
        List<RecipeDto> recipeList = null;

        try {
            recipeList = fridChefApiClient.findByIngredients(ingredientList);
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return recipeList;
    }

    public List<ItemDto> getAllUnits() {

        List<ItemDto> items = new ArrayList<>();
        items.add(new ItemDto(Unit.MG.toString(), "miligramos"));
        items.add(new ItemDto(Unit.GR.toString(), "gramos"));
        items.add(new ItemDto(Unit.ML.toString(), "mililitros"));
        items.add(new ItemDto(Unit.L.toString(), "litros"));
        items.add(new ItemDto(Unit.PIZCA.toString(), "pizca"));
        items.add(new ItemDto(Unit.CUCHARADITA.toString(), "cucharadita"));
        items.add(new ItemDto(Unit.CUCHARADA.toString(), "cucharada"));
        return items;
    }

    public boolean deleteIngredient(int id) {
        boolean deleted;

        try {
            deleted = fridChefApiClient.deleteIngredient(id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }

        return deleted;

    }

    public IngredientDto createIngredient(String name) throws ExternalErrorException {
        IngredientDto ingredientDto;

        try {

            ingredientDto = fridChefApiClient.createIngredient(name);


        } catch (ExternalErrorException eee) {
            System.out.println(eee.getMessage());
            throw eee;
        }

        return ingredientDto;

    }

}
