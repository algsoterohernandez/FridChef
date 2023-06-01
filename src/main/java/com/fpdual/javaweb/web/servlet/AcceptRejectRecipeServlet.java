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

/**
 * Servlet que maneja las peticiones para aceptar o rechazar recetas.
 */
@WebServlet(name = "AcceptRejectRecipeServlet", urlPatterns = {"/recipes-accept", "/recipes-reject"})
public class AcceptRejectRecipeServlet extends ParentServlet {

    private RecipeService recipeService;

    /**
     * Método de inicialización del servlet.
     * Crea una instancia de FridChefApiClient y RecipeService para manejar las recetas.
     * Llama al método init de la clase padre (HttpServlet) pasando el cliente de la API como parámetro.
     */
    @Override
    public void init() {
        FridChefApiClient apiClient =  new FridChefApiClient();
        recipeService = new RecipeService(apiClient);
        super.init(apiClient);
    }

    /**
     * Método que maneja las solicitudes HTTP GET al servlet.
     * Llena las categorías en la solicitud y realiza la actualización del estado de una receta.
     * Obtiene el ID de la receta y su estado de los parámetros de la solicitud.
     * Llama al método updateRecipeStatus de RecipeService para actualizar el estado de la receta.
     * Redirige la respuesta según el estado actualizado de la receta.
     * En caso de excepción, muestra un mensaje de error y redirige a una página de error.
     *
     * @param req  la solicitud HTTP recibida
     * @param resp la respuesta HTTP a enviar
     * @throws ServletException si se produce un error en el servlet
     * @throws IOException      si se produce un error de E/S al manejar la solicitud o la respuesta
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        try {
            int recipeId = Integer.parseInt(req.getParameter("id"));
            String recipeStatus = req.getParameter("status");

            RecipeDto recipe = recipeService.updateRecipeStatus(recipeId, recipeStatus);

            if (recipe.getStatus().equals(RecipeStatus.ACCEPTED.name())) {
                resp.sendRedirect("/FridChef/details-recipe?id="+ recipeId);

            } else if (recipe.getStatus().equals(RecipeStatus.DECLINED.name())) {
                resp.sendRedirect("/FridChef/home");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            req.getRequestDispatcher("/error/recipenotfound.jsp").forward(req, resp);
        }
    }
}
