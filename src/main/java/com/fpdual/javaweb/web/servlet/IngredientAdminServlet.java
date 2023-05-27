
package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.IngredientService;
import com.fpdual.javaweb.service.UserService;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "IngredientAdminServlet", urlPatterns = {"/admin-ingredients"})
public class IngredientAdminServlet extends ParentServlet {
    private UserService userService;
    private IngredientService ingredientService;

    @Override
    public void init() {
        FridChefApiClient apiClient = new FridChefApiClient();
        ingredientService = new IngredientService(apiClient);
        userService = new UserService(apiClient);
        super.init(apiClient);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{

            this.fillCategories(req);

            List<IngredientDto> ingredients =  ingredientService.findAllIngredients();
            req.setAttribute("IngredientList", ingredients);

            req.getRequestDispatcher("/admin/ingredients.jsp").forward(req, resp);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            resp.sendRedirect("/FridChef/home");
        }
    }
}