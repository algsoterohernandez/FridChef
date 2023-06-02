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

    public boolean addFavorite(int idRecipe, int idUser) {
        boolean favoriteAdded=false;
        try{
            favoriteAdded = apiClient.createFavorite(idUser, idRecipe);

        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return favoriteAdded;
    }

//    private void addRecipeToFavoriteList(int idRecipe){
//        UserDto user = new UserDto();
//        List<Integer> favoriteList = user.getFavoriteList();
//        favoriteList.add(idRecipe);
//    }

    public boolean removeFavorite(int idRecipe, int idUser){
        boolean favoriteRemoved = false;
        try {
            favoriteRemoved = apiClient.deleteFavorite(idRecipe, idUser);
//        if(favoriteRemoved){
//            removeRecipeToFavoriteList(int idRecipe);
//        }
        }catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
        }
        return favoriteRemoved;
    }

//    private void removeRecipeToFavoriteList(int idRecipe){
//        UserDto user = new UserDto();
//        List<Integer> favoriteList = user.getFavoriteList();
//        favoriteList.removeIf(recipeId -> recipeId.equals(idRecipe));
//    }

}
