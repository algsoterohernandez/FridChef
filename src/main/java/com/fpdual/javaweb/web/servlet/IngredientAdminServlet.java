
package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.IngredientService;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "IngredientAdminServlet", urlPatterns = {"/admin-ingredients"})
public class IngredientAdminServlet extends ParentServlet {
    private IngredientService ingredientService;

    @Override
    public void init() {
        FridChefApiClient apiClient = new FridChefApiClient();
        ingredientService = new IngredientService(apiClient);
        super.init(apiClient);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            this.fillCategories(req);

            List<IngredientDto> ingredients = ingredientService.findAllIngredients();
            req.setAttribute("IngredientList", ingredients);

            req.getRequestDispatcher("/admin/ingredients.jsp").forward(req, resp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            resp.sendRedirect("/FridChef/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean succeeded = false;

            this.fillCategories(req);


            if (req.getParameter("method-type").equals("add")) {

                AddIngredient(req, resp);

            } else if (req.getParameter("method-type").equals("delete")) {

                DeleteIngredient(req, resp);

            }
        List<IngredientDto> ingredients = ingredientService.findAllIngredients();
        req.setAttribute("IngredientList", ingredients);

        req.getRequestDispatcher("/admin/ingredients.jsp").forward(req, resp);
    }




    private void AddIngredient(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String ingredientName = req.getParameter("ingredient-name");

            IngredientDto createdIngredientDto = ingredientService.createIngredient(ingredientName);

            if (createdIngredientDto != null && createdIngredientDto.isAlreadyExists()) {

                req.setAttribute("error-add", "El ingrediente ya existe en nuestro sistema.");

            } else if (createdIngredientDto == null || createdIngredientDto.getId() == 0) {

                req.setAttribute("error-add", "No se ha podido crear el ingrediente. Vuelva a intentarlo más tarde.");

            } else {
                req.setAttribute("add-success", true);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            req.setAttribute("error-add", "No se ha podido efectuar la acción. Vuelva a intentarlo más tarde.");
        }


    }

    private void DeleteIngredient(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try
        {
            String idParam = req.getParameter("ingredient-id");
            int ingredientId = Integer.parseInt(idParam);

            boolean deleteIngredientDto = ingredientService.deleteIngredient(ingredientId);

            if (!deleteIngredientDto) {
                req.setAttribute("error-delete", "No se ha podido borrar el ingrediente.");
            } else {
                req.setAttribute("delete-success", true);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            req.setAttribute("error-delete", "No se ha podido efectuar la acción. Vuelva a intentarlo más tarde.");
        }


    }


}