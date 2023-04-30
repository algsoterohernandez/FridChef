package com.fpdual.javaweb.web.servlet;


import com.fpdual.javaweb.persistence.connector.MySQLConnector;
import com.fpdual.javaweb.persistence.manager.UserManager;
import com.fpdual.javaweb.service.UserService;
import com.fpdual.javaweb.web.servlet.dto.UserDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "RegisterFormServlet", urlPatterns = {"/servlet-register-form"})
public class RegisterFormServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService(new MySQLConnector(), new UserManager());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/register/registerForm.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            UserDto user = getUserFromRequest(req);

            UserDto createdUser = userService.registerUser(user);
            if(createdUser == null || createdUser.getId()==0 )
            {
                req.setAttribute("error","No se ha podido crear el usuario. Vuelva a intentarlo más tarde.");
            }
            else if (createdUser.isAlreadyExists())
            {
                req.setAttribute("error","El usuario ya existe en nuestro sistema.");
            }
            else
            {
                req.setAttribute("success",true);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            req.setAttribute("error","No se ha podido crear el usuario. Vuelva a intentarlo más tarde.");
        }
        req.getRequestDispatcher("/register/registerForm.jsp").forward(req, resp);
    }

    private UserDto getUserFromRequest(HttpServletRequest req) {
        UserDto userDto = new UserDto();

        userDto.setName(req.getParameter("name"));
        userDto.setSurname1(req.getParameter("surname1"));
        userDto.setSurname2(req.getParameter("surname2"));
        userDto.setEmail(req.getParameter("email"));
        userDto.setPassword(req.getParameter("password"));

        userDto.encryptPassword();

        return userDto;
    }
}