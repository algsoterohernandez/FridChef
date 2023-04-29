package com.fpdual.javaweb.web.servlet;


import com.fpdual.javaweb.persistence.connector.MySQLConnector;
import com.fpdual.javaweb.persistence.manager.UserManager;
import com.fpdual.javaweb.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "ServletFormulario", urlPatterns = {"/servlet-register-form"})
public class RegisterFormServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService=new UserService(new MySQLConnector(), new UserManager());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}