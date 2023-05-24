package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.CategoryService;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.CategoryDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RecipeServlet", urlPatterns = "/recipes")
public class RecipeServlet extends ParentServlet {

    private RecipeService recipeService;
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
    }
}
