package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.service.FavoriteService;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "MostRatedServlet", urlPatterns = {"/most-rated"})
public class MostRatedServlet extends ParentServlet {

    private FridChefApiClient apiClient;
    private RecipeService recipeService;

    @Override
    public void init() {
        apiClient = new FridChefApiClient();
        recipeService = new RecipeService(apiClient);
        super.init(apiClient);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        List<RecipeDto> recipes = new ArrayList<>();
        try {
            int limit = 10;
            recipes = recipeService.findMostRated(limit);
            req.setAttribute("recipes", recipes);
            req.getRequestDispatcher("/recipes/most-rated.jsp").forward(req, resp);

        } catch (ExternalErrorException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}