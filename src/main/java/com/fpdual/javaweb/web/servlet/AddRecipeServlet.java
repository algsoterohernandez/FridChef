package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.CategoryService;
import com.fpdual.javaweb.service.IngredientService;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.IngredientRecipeDto;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="AddRecipeServlet", urlPatterns = {"/add-recipes"})
public class AddRecipeServlet extends HttpServlet{
    private RecipeService recipeService;
    private CategoryService categoryService;
    private IngredientService ingredientService;

        @Override
        public void init() {
            FridChefApiClient apiClient = new FridChefApiClient();
            recipeService = new RecipeService(apiClient);
            categoryService = new CategoryService(apiClient);
            ingredientService = new IngredientService(apiClient);

        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            req.setAttribute("categories", categoryService.getAllCategories());
            req.setAttribute("ingredients", ingredientService.findAllIngredients());
            req.setAttribute("units", ingredientService.getAllIngredientUnits());
            req.setAttribute("recipe_created", false);
            req.getRequestDispatcher("/recipes/add-form.jsp").forward(req, resp);

        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String name = req.getParameter("title");
            String description = req.getParameter("description");
            int difficulty = Integer.parseInt(req.getParameter("difficulty"));
            int time = Integer.parseInt(req.getParameter("time"));
            String unitTime = req.getParameter("unit_time");
            int idCategory = Integer.parseInt(req.getParameter("category"));
            String[] ingredients = req.getParameterValues("ingredient[]");
            String[] unit = req.getParameterValues("unit[]");
            String[] quantity = req.getParameterValues("quantity[]");

            List<IngredientRecipeDto> ingredientsRecipe = new ArrayList<>();

            for(int i = 0; i<ingredients.length;i++){
                ingredientsRecipe.add(new IngredientRecipeDto(ingredients[i], unit[i], quantity[i]));
            }

            RecipeDto recipeDto = new RecipeDto();
            recipeDto.setName(name);
            recipeDto.setDescription(description);
            recipeDto.setDifficulty(difficulty);
            recipeDto.setTime(time);
            recipeDto.setUnitTime(unitTime);
            recipeDto.setIdCategory(idCategory);
            recipeDto.setIngredientsRecipe(ingredientsRecipe);

            recipeService.createRecipe(recipeDto);

            req.setAttribute("categories", categoryService.getAllCategories());
            req.setAttribute("ingredients", ingredientService.findAllIngredients());
            req.setAttribute("units", ingredientService.getAllIngredientUnits());
            req.setAttribute("recipe_created", true);
            req.getRequestDispatcher("/recipes/add-form.jsp").forward(req, resp);
        }
}
