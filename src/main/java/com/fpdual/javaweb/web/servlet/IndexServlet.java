package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.IngredientService;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;

@WebServlet(name = "IndexServlet", urlPatterns = {"/home", ""})
public class IndexServlet extends ParentServlet {

    private IngredientService ingredientService;
    private FridChefApiClient apiClient;
    @Override
    public void init() {
        apiClient = new FridChefApiClient();
        ingredientService = new IngredientService(apiClient);
        super.init(apiClient);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        addAttributesToRequestFromUrl(req);


        List<IngredientDto> ingredients =  ingredientService.findAllIngredients();
        req.setAttribute("IngredientList", ingredients);

        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }

    private void addAttributesToRequestFromUrl(HttpServletRequest req) {
        if(req.getQueryString() != null){
            if(req.getQueryString().contains("userDeleted")){
                req.setAttribute("userDeleted", true);
            }
            if(req.getQueryString().contains("userNotDeleted")){
                req.setAttribute("userNotDeleted", true);
            }
            if(req.getQueryString().contains("userRegistered")){
                req.setAttribute("userRegistered", true);
            }
            if(req.getQueryString().contains("userClosedSession")){
                req.setAttribute("userClosedSession", true);
            }
        }
    }
}