package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.UserService;
import com.fpdual.javaweb.util.Utils;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet que maneja las operaciones de inicio de sesión.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends ParentServlet {
    private UserService userService;

    /**
     * Método de inicialización del servlet.
     * Crea una instancia de FridChefApiClient y UserService para manejar los usuarios.
     * Llama al método init de la clase padre (HttpServlet) pasando el cliente de la API como parámetro.
     */
    @Override
    public void init() {
        FridChefApiClient apiClient =  new FridChefApiClient();
        userService = new UserService(apiClient);
        super.init(apiClient);
    }

    /**
     * Método que maneja las peticiones GET al servlet.
     * Llena las categorías en la solicitud utilizando el método fillCategories().
     * Redirige la solicitud a la página JSP de inicio de sesión.
     *
     * @param req  el objeto HttpServletRequest que contiene la solicitud HTTP
     * @param resp el objeto HttpServletResponse que contiene la respuesta HTTP
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException      si ocurre un error de E/S
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        req.getRequestDispatcher("/login/login.jsp").forward(req, resp);
    }

    /**
     * Método que maneja las peticiones HTTP POST enviadas al servlet de login.
     * Recibe la información del usuario (email y contraseña) y realiza la autenticación.
     * Si el usuario es autenticado correctamente, se establece la sesión y se redirige al inicio.
     * En caso contrario, se muestra un mensaje de error y se redirige de nuevo a la página de login.
     *
     * @param req  El objeto HttpServletRequest que contiene la solicitud HTTP.
     * @param resp El objeto HttpServletResponse que se utilizará para enviar la respuesta HTTP.
     * @throws ServletException Si ocurre un error durante el procesamiento del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida durante el procesamiento del servlet.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDto searchUser;
        try {
            this.fillCategories(req);
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String passwordMD5 = Utils.encryptPassword(password);
            searchUser = userService.findUser(email, passwordMD5);

            if (searchUser == null || searchUser.getEmail() == null) {
                req.setAttribute("error", "Email o contraseña incorrecto.");
                req.getRequestDispatcher("/login/login.jsp").forward(req, resp);

            } else {
                req.setAttribute("success", true);
                req.getSession().setMaxInactiveInterval(1800);
                req.getSession().setAttribute("sessionUser", searchUser);

                resp.sendRedirect("/FridChef/home");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            req.setAttribute("error", "No se ha podido encontrar el usuario. Vuelva a intentarlo más tarde.");
            req.getRequestDispatcher("/login/login.jsp").forward(req, resp);
        }
    }
}