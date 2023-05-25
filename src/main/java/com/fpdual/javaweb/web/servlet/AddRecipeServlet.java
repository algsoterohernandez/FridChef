package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.service.CategoryService;
import com.fpdual.javaweb.service.IngredientService;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@WebServlet(name="AddRecipeServlet", urlPatterns = {"/add-recipes"})
public class AddRecipeServlet extends ParentServlet{
    private RecipeService recipeService;
    private CategoryService categoryService;
    private IngredientService ingredientService;

        @Override
        public void init() {
            FridChefApiClient apiClient = new FridChefApiClient();
            recipeService = new RecipeService(apiClient);
            categoryService = new CategoryService(apiClient);
            ingredientService = new IngredientService(apiClient);
            super.init(apiClient);
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            this.fillCategories(req);
            req.setAttribute("categories", categoryService.getAllCategories());
            req.setAttribute("ingredients", ingredientService.findAllIngredients());
            req.setAttribute("units", ingredientService.getAllUnits());
            req.setAttribute("recipe_created", false);
            req.getRequestDispatcher("/recipes/add-form.jsp").forward(req, resp);

        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            this.fillCategories(req);
            String name = req.getParameter("title");
            String description = req.getParameter("description");
            int difficulty = Integer.parseInt(req.getParameter("difficulty"));
            int time = Integer.parseInt(req.getParameter("time"));
            String unitTime = req.getParameter("unit_time");
            int idCategory = Integer.parseInt(req.getParameter("category"));
            String[] ingredients = req.getParameterValues("ingredient[]");
            String[] quantity = req.getParameterValues("quantity[]");
            String[] unit = req.getParameterValues("unit[]");


            //Se crea una lista de tipo IngredientRecipeDto con stream
            List<IngredientRecipeDto> ingredientsRecipe = IntStream.range(0, ingredients.length)
                    .mapToObj(i -> new IngredientRecipeDto(ingredients[i], quantity[i], unit[i]))
                    .collect(Collectors.toList());

            //Se crea un objeto RecipeDto con los datos del formulario
            RecipeDto recipeDto = new RecipeDto();
            recipeDto.setName(name);
            recipeDto.setDescription(description);
            recipeDto.setDifficulty(difficulty);
            recipeDto.setTime(time);
            recipeDto.setUnitTime(unitTime);
            recipeDto.setIdCategory(idCategory);
            recipeDto.setIngredients(ingredientsRecipe);

            //Se llama al service para crear la receta
            try {
                recipeService.registerRecipe(recipeDto);
            } catch (ExternalErrorException e) {
                throw new RuntimeException(e);
            }

            //Se setean los atributos para la vista
            req.setAttribute("categories", categoryService.getAllCategories());
            req.setAttribute("ingredients", ingredientService.findAllIngredients());
            req.setAttribute("units", ingredientService.getAllUnits());
            req.setAttribute("recipe_created", true);
            req.getRequestDispatcher("/recipes/add-form.jsp").forward(req, resp);
        }
}