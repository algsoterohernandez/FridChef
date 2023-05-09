package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.service.IngredientService;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;

@WebServlet(name = "IndexServlet", urlPatterns = {"/home", ""})
public class IndexServlet extends HttpServlet {

    private IngredientService ingredientService;

    @Override
    public void init() {
        ingredientService = new IngredientService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<IngredientDto> ingredients =  ingredientService.findAllIngredients();
        req.setAttribute("IngredientList", ingredients);

        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }
}