package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.AllergenService;
import com.fpdual.javaweb.service.IngredientService;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.AllergenDto;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends ParentServlet {
    private IngredientService ingredientService;
    private AllergenService allergenService;
    private RecipeService recipeService;


    @Override
    public void init() {
        FridChefApiClient apiClient = new FridChefApiClient();
        ingredientService = new IngredientService(apiClient);
        allergenService = new AllergenService(apiClient);
        recipeService = new RecipeService(apiClient);
        super.init(apiClient);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RecipeDto> recipes = null;
        List<RecipeDto> recipeSuggestions = null;

        try {
            this.fillCategories(req);
            String[] ingredientes = req.getParameterValues("ingredientes[]");

            if (ingredientes == null || ingredientes.length < 3 || ingredientes.length > 6) {


            } else {
                List<String> listaIngredientes = Arrays.asList(ingredientes);
                recipeSuggestions = recipeService.findRecipeSuggestions(listaIngredientes);
                recipes = ingredientService.findByIngredients(listaIngredientes);
            }
        } catch (Exception e) {
            req.getRequestDispatcher("error/recipenotfound.jsp").forward(req, resp);
        }

        req.setAttribute("recipes", recipes);
        req.setAttribute("recipeSuggestions", recipeSuggestions);

        List<IngredientDto> ingredients =  ingredientService.findAllIngredients();
        req.setAttribute("IngredientList", ingredients);

        List<AllergenDto> allergenDtoList =  allergenService.findAllAllergens();
        req.setAttribute("AllergenDtoList", allergenDtoList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/search/search.jsp");
        dispatcher.forward(req, resp);
    }

}
