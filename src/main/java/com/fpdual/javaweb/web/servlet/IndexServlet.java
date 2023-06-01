package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.IngredientService;
import com.fpdual.javaweb.web.servlet.dto.IngredientDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;

/**
 * Servlet que maneja las peticiones de la página de inicio.
 */
@WebServlet(name = "IndexServlet", urlPatterns = {"/home", ""})
public class IndexServlet extends ParentServlet {

    private IngredientService ingredientService;

    /**
     * Método de inicialización del servlet.
     * Crea una instancia de FridChefApiClient y IngredientService para manejar los ingredientes.
     * Llama al método init de la clase padre (HttpServlet) pasando el cliente de la API como parámetro.
     */
    @Override
    public void init() {
        FridChefApiClient apiClient = new FridChefApiClient();
        ingredientService = new IngredientService(apiClient);
        super.init(apiClient);
    }

    /**
     * Método que maneja las peticiones GET para mostrar la página de inicio.
     *
     * @param req  el objeto HttpServletRequest que representa la solicitud HTTP.
     * @param resp el objeto HttpServletResponse que representa la respuesta HTTP.
     * @throws ServletException si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException      si ocurre un error de E/S durante el procesamiento de la solicitud.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        addAttributesToRequestFromUrl(req);


        List<IngredientDto> ingredients =  ingredientService.findAllIngredients();
        req.setAttribute("IngredientList", ingredients);

        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Método privado que agrega atributos adicionales a la solicitud basados en la URL.
     *
     * @param req el objeto HttpServletRequest que representa la solicitud HTTP.
     */
    private void addAttributesToRequestFromUrl(HttpServletRequest req) {
        if(req.getQueryString() != null){
            if(req.getQueryString().contains("userDeleted")){
                req.setAttribute("userDeleted", true);
            }
            if(req.getQueryString().contains("userNotDeleted")){
                req.setAttribute("userNotDeleted", true);
            }
            if(req.getQueryString().contains("userRegistered")){
                req.setAttribute("userRegistered", true);
            }
            if(req.getQueryString().contains("userClosedSession")){
                req.setAttribute("userClosedSession", true);
            }
        }
    }
}