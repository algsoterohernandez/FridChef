package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.BadRequestException;
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
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "FavoriteServlet", urlPatterns = {"/favorite"})
public class FavoriteServlet extends ParentServlet {

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        this.fillCategories(req);
        UserDto user = (UserDto) req.getSession().getAttribute("sessionUser");
        List<RecipeDto> favoriteRecipes = new ArrayList<>();

        if (user != null) {
            List<Integer> favoriteRecipeIds = user.getFavoriteList();
            try {
                favoriteRecipes = recipeService.findFavorites(favoriteRecipeIds);
                req.setAttribute("favoriteRecipes", favoriteRecipes);
                req.getRequestDispatcher("/recipes/favorite.jsp").forward(req, resp);

            } catch (BadRequestException e) {
                req.setAttribute("error", "No se ha podido cargar esta página. Vuelva a intentarlo más tarde.");
                req.getRequestDispatcher("/recipes/favorite.jsp").forward(req, resp);

            } catch (ExternalErrorException eeee) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        // favorite/?id_recipe=2&recipe_favorite=[add|remove]
        String recipeFavorite = req.getParameter("recipe_favorite");
        String idRecipe = req.getParameter("id_recipe");
        UserDto user = (UserDto) req.getSession().getAttribute("sessionUser");
        try {
            if (recipeFavorite.equals("add")) {
                user = favoriteService.addFavorite(Integer.parseInt(idRecipe), user);
            } else if (recipeFavorite.equals("remove")) {
                user = favoriteService.removeFavorite(Integer.parseInt(idRecipe), user);
            } else {
                throw new InvalidParameterException("invalid recipeFavorite action");
            }

            req.getSession().setAttribute("sessionUser", user);
            resp.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}