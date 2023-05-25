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
public class RecipeServlet extends ParentServlet {

    private RecipeService recipeService;
    private CategoryService categoryService;

    @Override
    public void init() {
        FridChefApiClient apiClient = new FridChefApiClient();
        recipeService = new RecipeService(apiClient);
        categoryService = new CategoryService(apiClient);
        super.init(apiClient);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        List<CategoryDto> categories = categoryService.getAllCategories();
        req.setAttribute("categoryList", categories);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/header/header.jsp");
        dispatcher.forward(req, resp);

           //String categoryIdParam = req.getParameter("id_category");
         //Obtener recetas de una categoría específica
            //CategoryDto categoryDto = categoryService.getAllCategories().get(Integer.parseInt(categoryIdParam));
           //List<RecipeDto> recipes = recipeService.findRecipesByCategory(categoryDto.getId());
           //req.setAttribute("category", categories);
           //req.setAttribute("recipes", recipes);

           //RequestDispatcher dispatcher1 = req.getRequestDispatcher("recipeList.jsp");
           //dispatcher1.forward(req, resp);

    }
}
