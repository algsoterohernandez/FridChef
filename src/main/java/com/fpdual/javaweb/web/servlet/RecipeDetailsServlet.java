package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.service.ValorationService;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import com.fpdual.javaweb.web.servlet.dto.ValorationDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RecipeDetailsServlet", urlPatterns = {"/details-recipe"})
public class RecipeDetailsServlet extends ParentServlet {
    private RecipeService recipeService;
    private FridChefApiClient apiClient;
    private ValorationService valorationService;
    @Override
    public void init() {
        apiClient = new FridChefApiClient();
        recipeService = new RecipeService(apiClient);
        valorationService = new ValorationService(apiClient);
        super.init(apiClient);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //se obtiene el parámetro de la url
        String recipeId = req.getParameter("id");
        if (recipeId == null || recipeId.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            this.fillCategories(req);
            int idRecipe = Integer.parseInt(recipeId);

            RecipeDto recipe = recipeService.findRecipe(idRecipe);

            if (recipe == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            req.setAttribute("recipe", recipe);
            double valoration = valorationService.getAverageRating(idRecipe);
            req.setAttribute("valoration", valoration);
            req.getRequestDispatcher("/recipes/details-recipe.jsp").forward(req, resp);

        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener parámetros necesarios
        String recipeId = req.getParameter("id");
        int idRecipe = Integer.parseInt(recipeId);

        // Verificar si el ID de la receta es válido
        if (recipeId == null || recipeId.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            // Obtener la receta y verificar si existe
            this.fillCategories(req);
            RecipeDto recipe = recipeService.findRecipe(idRecipe);

            if (recipe == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Obtener el usuario de la sesión
            UserDto user = (UserDto) req.getSession().getAttribute("sessionUser");

            // Registrar la valoración de la receta
            String valorationSt = req.getParameter("valoration");
            int valoration =Integer.parseInt(valorationSt);
            String comment= req.getParameter("comment");

            ValorationDto valorationDto = new ValorationDto();
            valorationDto.setIdRecipe(idRecipe);
            valorationDto.setIdUser(user.getId());
            valorationDto.setValoration(valoration);
            valorationDto.setComment(comment);

            // Crear la valoración
            valorationService.createValoration(valorationDto);

            // Redireccionar a la página de detalles de la receta
            resp.sendRedirect("/FridChef/details-recipe?id=" + idRecipe);
            } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
