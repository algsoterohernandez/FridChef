package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.CategoryService;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.CategoryDto;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RecipeServlet", urlPatterns = "/recipes")
public class RecipeServlet extends HttpServlet {

    private RecipeService recipeService;
    private CategoryService categoryService;

    @Override
    public void init() {
        recipeService = new RecipeService(new FridChefApiClient());
        categoryService = new CategoryService(new FridChefApiClient());

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<CategoryDto> categories = categoryService.getAllCategories();
        req.setAttribute("categoryList", categories);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/header/header.jsp");
        dispatcher.forward(req, resp);

        //String categoryIdParam = request.getParameter("id_category");
        //if (categoryIdParam != null) {
        // Obtener recetas de una categoría específica
        ////    CategoryDto categoryDto = categoryService.getAllCategories().get(categoryId);
        //    List<RecipeDto> recipes = recipeService.findRecipesByCategory(categoryId);
        //    request.setAttribute("category", categoryId);
        //    request.setAttribute("recipes", recipes);

        //   RequestDispatcher dispatcher = request.getRequestDispatcher("recipeList.jsp");
        //   dispatcher.forward(request, response);
        // } else {
        // Obtener todas las categorías
        //   List<CategoryDto> categories = categoryService.getAllCategories();
        //     request.setAttribute("categories", categories);

        //   RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
        //  dispatcher.forward(request, response);
    }
}
