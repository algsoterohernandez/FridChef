package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.enums.RecipeStatus;
import com.fpdual.javaweb.service.RecipeService;
import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RecipeRequestServlet", urlPatterns = {"/recipe-request"})
public class RecipeRequestServlet extends ParentServlet{

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
     * Método para procesar una solicitud GET al servlet.
     * Obtiene las recetas con estado "Pendiente" utilizando el servicio de recetas.
     * Establece las recetas como atributo en la solicitud.
     * Redirige la solicitud a la página "recipeRequest.jsp" en el directorio "/admin".
     *
     * @param req  la solicitud HTTP recibida.
     * @param resp la respuesta HTTP que se enviará.
     * @throws IOException si ocurre un error de entrada/salida al procesar la solicitud.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        this.fillCategories(req);

        try {

            List<RecipeDto> recipes = recipeService.findByStatusPending();
            req.setAttribute(RecipeStatus.PENDING.getStatus(), recipes);

            req.getRequestDispatcher("/admin/recipeRequest.jsp").forward(req, resp);

        }  catch (Exception e) {

            System.out.println(e.getMessage());
            req.setAttribute("error", "No se ha podido cargar esta página. Vuelva a intentarlo más tarde.");
            resp.sendRedirect("/FridChef/home");
        }
    }

}