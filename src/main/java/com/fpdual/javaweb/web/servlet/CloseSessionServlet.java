package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.UserService;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "CloseSessionServlet", urlPatterns = {"/close-session"})
public class CloseSessionServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService(new FridChefApiClient());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        try {
            UserDto user = (UserDto) req.getSession().getAttribute("sessionUser");

            if (user != null) {

                    req.getSession().setAttribute("sessionUser",null);
                    resp.sendRedirect("/FridChef/home");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
