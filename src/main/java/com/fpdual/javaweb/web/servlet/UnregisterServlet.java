package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.UserService;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "UnregisterServlet", urlPatterns = {"/unregister"})
public class UnregisterServlet extends HttpServlet {
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

                boolean deleted = userService.unregisterUser(user.getEmail());
                if (deleted) {
                    resp.sendRedirect("/FridChef/home");
                }else{
                   // Pending ventana alert
                }
            }
            else{
                //El usuario no esta logueado
            }

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }