package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.AllergenService;
import com.fpdual.javaweb.service.IngredientService;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.AllergenDto;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Servlet que maneja la búsqueda de recetas en relación con sus ingredientes.
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends ParentServlet {
    private IngredientService ingredientService;
    private AllergenService allergenService;
    private RecipeService recipeService;

    /**
     * Método de inicialización del servlet.
     * Crea instancias de FridChefApiClient, IngredientService, AllergenService y RecipeService para manejar los ingredientes, alérgenos y recetas.
     * Llama al método init de la clase padre (HttpServlet) pasando el cliente de la API como parámetro.
     */
    @Override
    public void init() {
        FridChefApiClient apiClient = new FridChefApiClient();
        ingredientService = new IngredientService(apiClient);
        allergenService = new AllergenService(apiClient);
        recipeService = new RecipeService(apiClient);
        super.init(apiClient);
    }

    /**
     * Método que maneja las peticiones POST del servlet.
     * Realiza la búsqueda de recetas y sugerencias de recetas según los ingredientes proporcionados en la petición.
     * Luego, establece los atributos necesarios en el objeto HttpServletRequest para mostrar los resultados en la vista.
     * Finalmente, redirige la petición a la página de búsqueda de recetas.
     *
     * @param req  El objeto HttpServletRequest que representa la solicitud HTTP.
     * @param resp El objeto HttpServletResponse que representa la respuesta HTTP.
     * @throws ServletException Si ocurre un error durante la ejecución del servlet.
     * @throws IOException      Si ocurre un error de E/S durante la ejecución del servlet.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RecipeDto> recipes = null;
        List<RecipeDto> recipeSuggestions = null;

        try {
            this.fillCategories(req);
            String[] ingredientes = req.getParameterValues("ingredientes[]");

            if (ingredientes == null || ingredientes.length < 3 || ingredientes.length > 6) {


            } else {
                List<String> listaIngredientes = Arrays.asList(ingredientes);
                recipeSuggestions = recipeService.findRecipeSuggestions(listaIngredientes);
                recipes = ingredientService.findByIngredients(listaIngredientes);
            }
        } catch (Exception e) {
            req.getRequestDispatcher("/error/recipenotfound.jsp").forward(req, resp);
        }

        req.setAttribute("recipes", recipes);
        req.setAttribute("recipeSuggestions", recipeSuggestions);

        List<IngredientDto> ingredients =  ingredientService.findAllIngredients();
        req.setAttribute("IngredientList", ingredients);

        List<AllergenDto> allergenDtoList =  allergenService.findAllAllergens();
        req.setAttribute("AllergenDtoList", allergenDtoList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/search/search.jsp");
        dispatcher.forward(req, resp);
    }
}
