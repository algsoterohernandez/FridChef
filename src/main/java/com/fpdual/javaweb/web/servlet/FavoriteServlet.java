package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.FavoriteService;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.service.ValorationService;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name="FavoriteServlet", urlPatterns = {"/favorite"})
public class FavoriteServlet extends ParentServlet{

    private FridChefApiClient apiClient;
    private FavoriteService favoriteService;

    @Override
    public void init() {
        apiClient = new FridChefApiClient();
        favoriteService = new FavoriteService(apiClient);
        super.init(apiClient);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDto user = (UserDto) req.getSession().getAttribute("sessionUser");

        if(user != null && user.getId() != 0) {

            int idUser = user.getId();
            String recipeId = req.getParameter("id");
            int idRecipe = Integer.parseInt(recipeId);

            if (recipeId == null || recipeId.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            boolean favoriteAdded = favoriteService.addFavorite(idUser, idRecipe);

            if (favoriteAdded) {
                List<Integer> favoriteList = user.getFavoriteList();
                favoriteList.add(idRecipe);
            }
        }else{
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        UserDto user = (UserDto) req.getSession().getAttribute("sessionUser");

        if(user != null && user.getId() != 0){

            int idUser = user.getId();
            String recipeId = req.getParameter("id");
            int idRecipe = Integer.parseInt(recipeId);

            if(recipeId == null || recipeId.isEmpty()){
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            boolean favoriteRemoved = favoriteService.removeFavorite(idUser, idRecipe);

            if(favoriteRemoved){
                List<Integer> favoriteList = user.getFavoriteList();
                favoriteList.removeIf(existingRecipeId -> recipeId.equals(idRecipe));
            }
        }else{
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}