package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.UserService;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(name = "CloseSessionServlet", urlPatterns = {"/close-session"})
public class CloseSessionServlet extends ParentServlet {
    private UserService userService;
    @Override
    public void init() {
        FridChefApiClient apiClient = new FridChefApiClient();
        userService = new UserService(apiClient);
        super.init(apiClient);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        this.fillCategories(req);
        try {
            this.fillCategories(req);
            UserDto user = (UserDto) req.getSession().getAttribute("sessionUser");

            if (user != null) {

                    req.getSession().setAttribute("sessionUser",null);
                    resp.sendRedirect("/FridChef/home");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
