package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.UserService;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "UnregisterServlet", urlPatterns = {"/unregister"})
public class UnregisterServlet extends ParentServlet {
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
     * Método para manejar las peticiones GET al servlet.
     * Se encarga de eliminar el usuario registrado actualmente.
     *
     * @param req  La solicitud HTTP recibida.
     * @param resp La respuesta HTTP que se enviará.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        try {
            this.fillCategories(req);
            UserDto user = (UserDto) req.getSession().getAttribute("sessionUser");

            if (user != null) {

                boolean deleted = userService.unregisterUser(user.getEmail());
                if (deleted) {
                    req.getSession().setAttribute("sessionUser",null);
                    resp.sendRedirect("/FridChef/home?userDeleted=true");
                }
            }else{
                resp.sendRedirect("/FridChef/home?userNotDeleted=true");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}