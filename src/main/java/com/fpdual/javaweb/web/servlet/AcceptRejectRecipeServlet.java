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

    @Override
    public void init() {
        FridChefApiClient apiClient =  new FridChefApiClient();
        recipeService = new RecipeService(apiClient);
        super.init(apiClient);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        try {
            int recipeId = Integer.parseInt(req.getParameter("id"));
            String recipeStatus = req.getParameter("status");
            RecipeDto recipe = recipeService.updateRecipeStatus(recipeId, recipeStatus);

            if (recipe.getStatus().equals(RecipeStatus.ACCEPTED.name())) {
                resp.sendRedirect("/FridChef/recipes?id="+ recipeId);
            } else if (recipe.getStatus().equals(RecipeStatus.DECLINED.name())) {
                resp.sendRedirect("/FridChef/home");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            req.getRequestDispatcher("/error/recipenotfound.jsp").forward(req, resp);
        }

    }
}
