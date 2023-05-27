package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.enums.RecipeStatus;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AcceptRejectRecipeServlet", urlPatterns = {"/recipes-accept", "/recipes-reject"})
public class AcceptRejectRecipeServlet extends ParentServlet {

    private RecipeService recipeService;
    private FridChefApiClient apiClient;

    @Override
    public void init() {
        apiClient =  new FridChefApiClient();
        recipeService = new RecipeService(apiClient);
        super.init(apiClient);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            int recipeId = Integer.parseInt(request.getParameter("id"));
            String recipeStatus = request.getParameter("status");
            RecipeDto recipe = recipeService.updateRecipeStatus(recipeId, recipeStatus);

            if (recipe.getStatus().equals(RecipeStatus.ACCEPTED.name())) {
                response.sendRedirect("/FridChef/recipes?id="+ recipeId);

            } else if (recipe.getStatus().equals(RecipeStatus.DECLINED.name())) {
                response.sendRedirect("/FridChef/home");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            request.getRequestDispatcher("/error/recipenotfound.jsp").forward(request, response);
        }

    }
}
