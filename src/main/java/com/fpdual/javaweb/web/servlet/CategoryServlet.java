package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet que maneja las peticiones relacionadas con las categorías de recetas.
 */
@WebServlet(name = "CategoryServlet", urlPatterns = "/category")
public class CategoryServlet extends ParentServlet {

    private RecipeService recipeService;

    /**
     * Método de inicialización del servlet.
     * Crea una instancia de FridChefApiClient y RecipeService para manejar las recetas.
     * Llama al método init de la clase padre (HttpServlet) pasando el cliente de la API como parámetro.
     */
    @Override
    public void init() {
        FridChefApiClient apiClient = new FridChefApiClient();
        recipeService = new RecipeService(apiClient);
        super.init(apiClient);
    }

    /**
     * Método HTTP GET que maneja las solicitudes GET al servlet.
     * Rellena las categorías en la solicitud.
     * Obtiene el parámetro "id_category" de la solicitud para determinar si se debe mostrar una categoría específica de recetas.
     * Si se proporciona el parámetro "id_category", se obtienen las recetas de esa categoría y se envían a la vista.
     * Si no se proporciona el parámetro "id_category", se redirige a la página de error "recipenotfound.jsp".
     * Se utiliza un objeto RequestDispatcher para redirigir la solicitud a la vista correspondiente.
     *
     * @param req  el objeto HttpServletRequest que contiene la solicitud HTTP
     * @param resp el objeto HttpServletResponse que contiene la respuesta HTTP
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException      si ocurre un error de E/S
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        String categoryIdParam = req.getParameter("id_category");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/header/header.jsp");

        if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
            //Obtener recetas de una categoría específica
            int idCategory = Integer.parseInt(categoryIdParam);
            List<RecipeDto> recipes = recipeService.findAllRecipesByCategoryId(idCategory);
            req.setAttribute("category", idCategory);
            req.setAttribute("recipes", recipes);

            dispatcher = req.getRequestDispatcher("/recipes/category.jsp");
        } else {
            req.getRequestDispatcher("error/recipenotfound.jsp").forward(req, resp);

        }
        dispatcher.forward(req, resp);
    }
}