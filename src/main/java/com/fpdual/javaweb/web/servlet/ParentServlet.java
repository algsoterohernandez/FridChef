package com.fpdual.javaweb.web.servlet;
import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.CategoryService;
import com.fpdual.javaweb.web.servlet.dto.CategoryDto;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Clase base para los servlets de la aplicación.
 * Extiende HttpServlet para proporcionar funcionalidad común.
 */
public class ParentServlet  extends HttpServlet {
    protected CategoryService categoryService;

    /**
     * Método de inicialización del servlet.
     *
     * @param apiClient el cliente de la API para realizar las llamadas al backend.
     */
    public void init(FridChefApiClient apiClient) {
        categoryService = new CategoryService(apiClient);
    }

    /**
     * Método que llena la lista de categorías y agrega el atributo correspondiente a la solicitud.
     *
     * @param req el objeto HttpServletRequest que representa la solicitud HTTP.
     * @return el objeto HttpServletRequest con el atributo de la lista de categorías agregado.
     */
    protected HttpServletRequest fillCategories(HttpServletRequest req) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        req.setAttribute("categoryList", categories);
        return req;
    }
}