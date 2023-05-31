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


@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends ParentServlet {
    private UserService userService;
    @Override
    public void init() {
        FridChefApiClient apiClient =  new FridChefApiClient();
        userService = new UserService(apiClient);
        super.init(apiClient);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        req.getRequestDispatcher("/login/login.jsp").forward(req, resp);
    }

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