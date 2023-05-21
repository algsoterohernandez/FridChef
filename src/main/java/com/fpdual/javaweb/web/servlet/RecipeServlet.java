package com.fpdual.javaweb.web.servlet;


import com.fpdual.javaweb.service.RecipeService;
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

    @Override
    public void init() {
        recipeService = new RecipeService();
    }

    /*@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RecipeDto> recipes = recipeService.findRecipeByCategory();
        req.setAttribute("RecipeList", recipes);

        RequestDispatcher dispatcher = req.getRequestDispatcher("recipes.jsp");
        dispatcher.forward(req, resp);
    }*/
}

