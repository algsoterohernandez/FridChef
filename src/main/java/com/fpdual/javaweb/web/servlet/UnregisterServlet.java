package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.persistence.connector.MySQLConnector;
import com.fpdual.javaweb.persistence.manager.UserManager;
import com.fpdual.javaweb.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UnregisterServlet", urlPatterns = {"/unregister"})
public class UnregisterServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService(new MySQLConnector(), new UserManager());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){

        try {
            /* Estas lineas junto con la String email se tendran que reajustar en el login
            Usuario usuario = (Usuario) req.getSession().getAttribute("usuarioSesion");

            if(usuario!=null){
                resp.sendRedirect("/AplicativoWeb/comun/homePage.jsp");*/

            String email = req.getParameter("email");
            boolean deleted = userService.unregisterUser(email);
            if(deleted){
                resp.sendRedirect("/login/login.jsp");
            }/*else{
                Pending
            }*/

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}