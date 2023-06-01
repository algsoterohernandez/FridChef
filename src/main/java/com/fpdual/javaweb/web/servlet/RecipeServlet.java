package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RecipeServlet", urlPatterns = "/recipes")
public class RecipeServlet extends ParentServlet {

    private RecipeService recipeService;
    private FridChefApiClient apiClient;

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
     * Método que maneja las solicitudes GET al servlet.
     * Llama al método fillCategories de la clase padre (ParentServlet) para llenar el request con las categorías.
     * Obtiene el parámetro "id" de la solicitud para obtener el identificador de la receta.
     * Utiliza el RecipeService para buscar los detalles de la receta utilizando el identificador obtenido.
     * Establece los detalles de la receta como atributo en el request.
     * Redirecciona al recipe.jsp para mostrar los detalles de la receta.
     *
     * @param request  el objeto HttpServletRequest que contiene la solicitud HTTP
     * @param response el objeto HttpServletResponse que contiene la respuesta HTTP
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException      si ocurre un error de E/S
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.fillCategories(request);
        String recipeId = request.getParameter("id");
        RecipeDto recipe = recipeService.findRecipeById(Integer.parseInt(recipeId)); // Obtiene los detalles de la receta

        request.setAttribute("recipe", recipe); // Establece los detalles de la receta como atributo en el request
        request.getRequestDispatcher("/recipes/recipe.jsp").forward(request, response); // Redirecciona al recipe.jsp para mostrar los detalles de la receta
    }
}



