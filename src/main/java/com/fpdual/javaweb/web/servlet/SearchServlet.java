package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.service.IngredientService;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {

    private IngredientService ingredientService;

    @Override
    public void init() {
        ingredientService = new IngredientService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RecipeDto> recipes = null;

        try {
            String[] ingredientes = req.getParameterValues("ingredientes[]");

            if (ingredientes == null || ingredientes.length < 3 || ingredientes.length > 6) {


            } else {
                List<String> listaIngredientes = Arrays.asList(ingredientes);

                recipes = ingredientService.findByIngredients(listaIngredientes);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            req.setAttribute("error", "Ha ocurrido un error");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }

        req.setAttribute("recipes", recipes);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/search/search.jsp");
        dispatcher.forward(req, resp);
    }

}
