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
import java.util.List;

@WebServlet(name = "RecipeRequestServlet", urlPatterns = {"/recipe-request"})
public class RecipeRequestServlet extends ParentServlet{

    private RecipeService recipeService;

    @Override
    public void init() {
        FridChefApiClient apiClient =  new FridChefApiClient();
        recipeService = new RecipeService(apiClient);
        super.init(apiClient);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {

        try {

            List<RecipeDto> recipes = recipeService.findByStatusPending();
            req.setAttribute(RecipeStatus.PENDING.getStatus(), recipes);

            req.getRequestDispatcher("/recipes/recipeRequest.jsp").forward(req, resp);

        }  catch (Exception e) {

            System.out.println(e.getMessage());
            req.setAttribute("error", "No se ha podido cargar esta página. Vuelva a intentarlo más tarde.");
            resp.sendRedirect("/FridChef/home");
        }
    }

}