package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.service.FavoriteService;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="FavoriteServlet", urlPatterns = {"/favorite"})
public class FavoriteServlet extends ParentServlet{

    private FridChefApiClient apiClient;
    private FavoriteService favoriteService;
    private RecipeService recipeService;

    @Override
    public void init() {
        apiClient = new FridChefApiClient();
        favoriteService = new FavoriteService(apiClient);
        recipeService = new RecipeService(apiClient);
        super.init(apiClient);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        this.fillCategories(req);
        UserDto user = (UserDto)  req.getSession().getAttribute("sessionUser");
        List<RecipeDto> favoriteRecipes = new ArrayList<>();

        if(user != null){
            List<Integer> favoriteRecipeIds = user.getFavoriteList();
            for(Integer recipeId : favoriteRecipeIds){
                RecipeDto recipe = null;
                try {
                    recipe = recipeService.findRecipe(recipeId);
                } catch (ExternalErrorException e) {
                    throw new RuntimeException(e);
                }
                if(recipe!= null){
                    favoriteRecipes.add(recipe);
                }
            }
        }

        req.setAttribute("favoriteRecipes", favoriteRecipes);
        req.getRequestDispatcher("/recipes/favorite.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        UserDto user = (UserDto) req.getSession().getAttribute("sessionUser");

        if(user != null && user.getId() != 0) {
            String recipeId = req.getParameter("id");

            if (recipeId == null || recipeId.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            int idRecipe = Integer.parseInt(recipeId);
            user = favoriteService.addFavorite(idRecipe, user);
            req.getSession().setAttribute("sessionUser", user);
        }else{
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        this.fillCategories(req);
        UserDto user = (UserDto) req.getSession().getAttribute("sessionUser");

        if(user != null && user.getId() != 0){

            String recipeId = req.getParameter("id");

            if(recipeId == null || recipeId.isEmpty()){
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            int idRecipe = Integer.parseInt(recipeId);
            user = favoriteService.removeFavorite(idRecipe, user);
            req.getSession().setAttribute("sessionUser", user);
        }else{
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}