package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Este servlet maneja las solicitudes para mostrar las recetas mejor valoradas.
 *
 */
@WebServlet(name = "MostRatedServlet", urlPatterns = {"/most-rated"})
public class MostRatedServlet extends ParentServlet {

    private FridChefApiClient apiClient;
    private RecipeService recipeService;

    /**
     * Método de inicialización del servlet.
     * Crea una instancia de FridChefApiClient y RecipeService para manejar las recetas.
     * Llama al método init de la clase padre (HttpServlet) pasando el cliente de la API como parámetro.
     */
    @Override
    public void init() {
        apiClient = new FridChefApiClient();
        recipeService = new RecipeService(apiClient);
        super.init(apiClient);
    }

    /**
     * Método que maneja las peticiones GET al servlet.
     * Llena las categorías necesarias en la solicitud.
     * Obtiene las recetas mejor valoradas del servicio de recetas.
     * Establece las recetas como atributo en la solicitud y redirige al archivo JSP correspondiente para mostrar las recetas mejor valoradas.
     * En caso de producirse una excepción del tipo ExternalErrorException, establece el código de respuesta como "SC_BAD_REQUEST" (400).
     *
     * @param req HttpServletRequest: El objeto de solicitud HTTP.
     * @param resp HttpServletResponse: El objeto de respuesta HTTP.
     * @throws ServletException si ocurre un error en el servlet.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        List<RecipeDto> recipes;
        try {
            int limit = 10;
            recipes = recipeService.findMostRated(limit);
            req.setAttribute("recipes", recipes);
            req.getRequestDispatcher("/recipes/most-rated.jsp").forward(req, resp);

        } catch (ExternalErrorException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}