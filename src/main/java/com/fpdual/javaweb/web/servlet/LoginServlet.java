package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.UserService;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;


@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService(new FridChefApiClient());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/login/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto searchUser;
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            searchUser = userService.findUser(email, userService.encryptPassword(password));

            if (searchUser == null || searchUser.getEmail() == null) {
                req.setAttribute("error", "Email o contraseña incorrecto.");
                req.getRequestDispatcher("/login/login.jsp").forward(req, resp);
            } else {
                req.setAttribute("success", true);

                req.getSession().setMaxInactiveInterval(60);
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