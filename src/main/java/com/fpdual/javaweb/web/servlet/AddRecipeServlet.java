package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.CategoryService;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="AddRecipeServlet", urlPatterns = {"/add-recipes"})
public class AddRecipeServlet extends HttpServlet{
    private RecipeService recipeService;
    private CategoryService categoryService;

        @Override
        public void init() {
            FridChefApiClient apiClient = new FridChefApiClient();
            recipeService = new RecipeService(apiClient);
            categoryService = new CategoryService(apiClient);
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            req.setAttribute("categories", categoryService.getAllCategories());
            req.getRequestDispatcher("/recipes/add-form.jsp").forward(req, resp);

        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        }
}
