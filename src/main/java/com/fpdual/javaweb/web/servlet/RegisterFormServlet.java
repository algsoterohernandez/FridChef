package com.fpdual.javaweb.web.servlet;


import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.SenderEmailService;
import com.fpdual.javaweb.service.UserService;
import com.fpdual.javaweb.util.Utils;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.Properties;


@WebServlet(name = "RegisterFormServlet", urlPatterns = {"/register-form"})
public class RegisterFormServlet extends ParentServlet {
    private UserService userService;
    private SenderEmailService senderEmail;
    @Override
    public void init() {
        FridChefApiClient apiClient = new FridChefApiClient();
        userService = new UserService(apiClient);
        senderEmail = new SenderEmailService(new Properties(), new Properties());
        super.init(apiClient);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        req.getRequestDispatcher("/register/registerForm.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean succeeded = false;

        try {
            this.fillCategories(req);

            UserDto user = getUserFromRequest(req);

            UserDto createdUser = userService.registerUser(user);


            if (createdUser != null && createdUser.isAlreadyExists()) {

                req.setAttribute("error", "El usuario ya existe en nuestro sistema.");

            } else if (createdUser == null || createdUser.getId() == 0) {

                req.setAttribute("error", "No se ha podido crear el usuario. Vuelva a intentarlo más tarde.");

            } else {

                //Envio de Email de bienvenida
                String from = "fridcheffpdual@gmail.com";
                String to = createdUser.getEmail();
                String subject = "¡Desde FRIDCHEF te damos la bienvenida!";

                String content = "<h3>¡¡¡Ya eres un FridChefer!!!<h3>" +
                        "<div><p>Enhorabuena, el registro se ha completado con éxito. Ahora puedes INICIAR SESIÓN con " +
                        "tu correo electrónico y contraseña</div>" + "<h3>¡Comienza la aventura en la cocina!<h3>" ;


                senderEmail.sendEmail(from, to, subject, content);


                resp.sendRedirect("/FridChef/home?userRegistered=true");

                succeeded = true;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            req.setAttribute("error", "No se ha podido crear el usuario. Vuelva a intentarlo más tarde.");
        }

        if(!succeeded){
            req.getRequestDispatcher("/register/registerForm.jsp").forward(req, resp);
        }
    }

    private UserDto getUserFromRequest(HttpServletRequest req) {

        UserDto userDto = new UserDto();
        userDto.setName(req.getParameter("name"));
        userDto.setSurname1(req.getParameter("surname1"));
        userDto.setSurname2(req.getParameter("surname2"));
        userDto.setEmail(req.getParameter("email"));
        userDto.setPassword(Utils.encryptPassword(req.getParameter("password")));


        return userDto;
    }
}