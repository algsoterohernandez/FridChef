package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.service.CategoryService;
import com.fpdual.javaweb.service.IngredientService;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RecipeDetailsServlet", urlPatterns = {"/details-recipe"})
public class RecipeDetailsServlet extends HttpServlet {
    private RecipeService recipeService;

    @Override
    public void init() {
        FridChefApiClient apiClient = new FridChefApiClient();
        recipeService = new RecipeService(apiClient);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //se obtiene el parámetro de la url
        String recipeId = req.getParameter("id");
        if (recipeId == null || recipeId.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            UserDto user = (UserDto) req.getSession().getAttribute("sessionUser");

            if (user == null) {
                req.setAttribute("not_user", true);
                req.getRequestDispatcher("/recipes/details-recipe.jsp").forward(req, resp);
                return;
            }

            int idRecipe = Integer.parseInt(recipeId);

            RecipeDto recipe = recipeService.findRecipe(idRecipe);

            if (recipe == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            req.setAttribute("recipe", recipe);
            req.getRequestDispatcher("/recipes/details-recipe.jsp").forward(req, resp);

        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}
