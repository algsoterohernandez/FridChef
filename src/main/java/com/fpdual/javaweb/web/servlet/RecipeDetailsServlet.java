package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.exceptions.ExternalErrorException;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.service.UserService;
import com.fpdual.javaweb.service.ValorationService;
import com.fpdual.javaweb.util.Utils;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import com.fpdual.javaweb.web.servlet.dto.ValorationDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RecipeDetailsServlet", urlPatterns = {"/details-recipe"})
public class RecipeDetailsServlet extends ParentServlet {
    private RecipeService recipeService;
    private FridChefApiClient apiClient;
    private ValorationService valorationService;

    /**
     * {@inheritDoc}
     * Método de inicialización del servlet.
     * Crea una instancia de FridChefApiClient, RecipeService y ValorationService para manejar recetas y valoraciones.
     * Llama al método init de la clase padre (HttpServlet) pasando el cliente de la API como parámetro.
     */
    @Override
    public void init() {
        apiClient = new FridChefApiClient();
        recipeService = new RecipeService(apiClient);
        valorationService = new ValorationService(apiClient);
        super.init(apiClient);
    }

    /**
     * Método que maneja las peticiones GET al servlet.
     * Obtiene el parámetro "id" de la URL y verifica si está presente.
     * Si el parámetro está ausente o vacío, establece el código de respuesta como "SC_BAD_REQUEST" (400) y finaliza la ejecución del método.
     * Si el parámetro está presente, intenta convertirlo a un entero y buscar la receta correspondiente en el servicio de recetas.
     * Si no se encuentra la receta, establece el código de respuesta como "SC_NOT_FOUND" (404) y finaliza la ejecución del método.
     * Si se encuentra la receta, se establece como atributo en la solicitud y se redirige al archivo JSP correspondiente para mostrar los detalles de la receta.
     * En caso de producirse una excepción del tipo ExternalErrorException, se imprime el mensaje de error y se establece el código de respuesta como "SC_NOT_FOUND" (404).
     * En caso de producirse una excepción del tipo NumberFormatException al convertir el parámetro "id" a un entero, se establece el código de respuesta como "SC_BAD_REQUEST" (400).
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //se obtiene el parámetro de la url
        String recipeId = req.getParameter("id");
        if (recipeId == null || recipeId.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            int idRecipe = Integer.parseInt(recipeId);
            this.fillCategories(req);

            RecipeDto recipe = recipeService.findRecipeById(idRecipe);

            if (recipe == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            int valorationsLimit = 10;
            List<ValorationDto> valorations = valorationService.findValorations(idRecipe, valorationsLimit);
            UserDto user = (UserDto) req.getSession().getAttribute("sessionUser");

            //Se pasa el atributo "valoration_created" al jsp
            String valorationCreated = (String) req.getParameter("valoration_created");
            if(valorationCreated != null){
                req.setAttribute("valorationCreated", valorationCreated.equals("1"));
            }

            //Se pasan los parámetros del detalle
            req.setAttribute("recipe", recipe);
            req.setAttribute("valorations", valorations);
            req.setAttribute("isFavorite", user != null && user.isFavorite(recipe.getId()));

            //se redirecciona al jsp para mostrar el detalle
            req.getRequestDispatcher("/recipes/details-recipe.jsp").forward(req, resp);

        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }catch (NumberFormatException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * Método que maneja las solicitudes POST del servlet.
     * Obtiene los parámetros necesarios de la solicitud, como el ID de la receta, la valoración y el comentario.
     * Verifica si el ID de la receta es válido y, en caso contrario, devuelve un código de estado de error.
     * Obtiene el usuario de la sesión y registra la valoración de la receta.
     * Crea un objeto ValorationDto con la información de la valoración.
     * Crea la valoración llamando al método createValoration de valorationService.
     * Redirecciona a la página de detalles de la receta después de crear la valoración.
     * En caso de excepción ExternalErrorException, devuelve un código de estado de recurso no encontrado.
     */
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

            // Se crea la valoración
            boolean valorationCreated = valorationService.createValoration(valorationDto);
            String valorationParameter = valorationCreated ? "1" : "0";
            resp.sendRedirect("/FridChef/details-recipe?valoration_created=" + valorationParameter +"&id=" + idRecipe);

        } catch (ExternalErrorException e) {
            System.out.println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
