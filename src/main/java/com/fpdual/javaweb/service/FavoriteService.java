package com.fpdual.javaweb.service;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.web.servlet.dto.FavoriteDto;
import com.fpdual.javaweb.web.servlet.dto.UserDto;

import java.util.List;

public class FavoriteService {
    private final FridChefApiClient apiClient;

    public FavoriteService(FridChefApiClient apiClient) {
            this.apiClient = apiClient;
    }

    public UserDto addFavorite(int idRecipe, UserDto user) {
        try{
        boolean favoriteAdded = apiClient.createFavorite(idRecipe, user.getId());
            if(favoriteAdded){
                user = addRecipeToFavoriteList(idRecipe, user);
            }
        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    private UserDto addRecipeToFavoriteList(int idRecipe, UserDto user){
        List<Integer> favoriteList = user.getFavoriteList();
        if(!favoriteList.contains(idRecipe)){
            favoriteList.add(idRecipe);
        }
        user.setFavoriteList(favoriteList);
        return user;
    }

    public UserDto removeFavorite(int idRecipe, UserDto user){
        try {
        boolean favoriteRemoved = apiClient.deleteFavorite(idRecipe, user.getId());
            if(favoriteRemoved){
                user = removeRecipeToFavoriteList(idRecipe, user);
            }
        }catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    private UserDto removeRecipeToFavoriteList(int idRecipe, UserDto user){
        List<Integer> favoriteList = user.getFavoriteList();
        favoriteList.removeIf(recipeId -> recipeId.equals(idRecipe));
        user.setFavoriteList(favoriteList);
        return user;
    }

}
