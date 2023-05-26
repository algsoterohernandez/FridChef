package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RecipeServlet", urlPatterns = "/recipes")
public class RecipeServlet extends HttpServlet {

    private RecipeService recipeService;

    @Override
    public void init() {
        recipeService = new RecipeService(new FridChefApiClient());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String recipeId = request.getParameter("id");
        RecipeDto recipe = recipeService.findRecipeById(Integer.parseInt(recipeId)); // Obtiene los detalles de la receta

        request.setAttribute("recipe", recipe); // Establece los detalles de la receta como atributo en el request
        request.getRequestDispatcher("/recipe/recipe.jsp").forward(request, response); // Redirecciona al recipe.jsp para mostrar los detalles de la receta
    }
}

    /*@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RecipeDto> recipes = recipeService.findRecipeByCategory();
        req.setAttribute("RecipeList", recipes);

        RequestDispatcher dispatcher = req.getRequestDispatcher("recipe.jsp");
        dispatcher.forward(req, resp);
    }*/


