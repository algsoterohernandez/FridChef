package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.CategoryDto;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet", urlPatterns = "/category")
public class CategoryServlet extends ParentServlet {

    private RecipeService recipeService;
    private FridChefApiClient apiClient;

    @Override
    public void init() {
        apiClient = new FridChefApiClient();
        recipeService = new RecipeService(apiClient);
        super.init(apiClient);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        String categoryIdParam = req.getParameter("id_category");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/header/header.jsp");

        if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
            //Obtener recetas de una categoría específica
            int idCategory = Integer.parseInt(categoryIdParam);
            List<RecipeDto> recipes = recipeService.findAllRecipesByCategoryId(idCategory);
            req.setAttribute("category", idCategory);
            req.setAttribute("recipes", recipes);

            dispatcher = req.getRequestDispatcher("/recipes/category.jsp");
        } else {
            req.getRequestDispatcher("error/recipenotfound.jsp").forward(req, resp);

        }
        dispatcher.forward(req, resp);
    }
}