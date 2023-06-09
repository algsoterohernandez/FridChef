package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.UserService;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet que maneja las peticiones para cerrar la sesión del usuario.
 */
@WebServlet(name = "CloseSessionServlet", urlPatterns = {"/close-session"})
public class CloseSessionServlet extends ParentServlet {
    private UserService userService;

    /**
     * Método de inicialización del servlet.
     * Crea una instancia de FridChefApiClient y UserService para manejar los usuarios.
     * Llama al método init de la clase padre (HttpServlet) pasando el cliente de la API como parámetro.
     */
    @Override
    public void init() {
        FridChefApiClient apiClient = new FridChefApiClient();
        userService = new UserService(apiClient);
        super.init(apiClient);
    }

    /**
     * Método que maneja las solicitudes GET al servlet.
     * Limpia la sesión del usuario actual y redirige al inicio del sistema.
     *
     * @param req  el objeto HttpServletRequest que contiene la solicitud del cliente
     * @param resp el objeto HttpServletResponse que se utilizará para enviar la respuesta al cliente
     * @throws RuntimeException sí se produce una excepción de tipo IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            this.fillCategories(req);
            UserDto user = (UserDto) req.getSession().getAttribute("sessionUser");

            if (user != null) {
                 req.getSession().setAttribute("sessionUser",null);
            }
            resp.sendRedirect("/FridChef/home");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
