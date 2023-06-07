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

    /**
     * Método de inicialización del servlet.
     * Crea una instancia de FridChefApiClient para manejar las llamadas a la API.
     * Crea una instancia de UserService para manejar la lógica relacionada con los usuarios.
     * Crea una instancia de SenderEmailService para enviar correos electrónicos.
     * Llama al método init de la clase padre (HttpServlet) pasando el cliente de la API como parámetro.
     */
    @Override
    public void init() {
        FridChefApiClient apiClient = new FridChefApiClient();
        userService = new UserService(apiClient);
        senderEmail = new SenderEmailService(new Properties(), new Properties());
        super.init(apiClient);
    }

    /**
     * Método que maneja las solicitudes GET al servlet.
     * Llena las categorías en la solicitud utilizando el método fillCategories().
     * Redirecciona la solicitud al formulario de registro ("/register/registerForm.jsp").
     *
     * @param req  la solicitud HTTP recibida por el servlet.
     * @param resp la respuesta HTTP que se enviará al cliente.
     * @throws ServletException si ocurre un error durante el manejo de la solicitud.
     * @throws IOException      si ocurre un error de E/S durante el manejo de la solicitud.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        req.getRequestDispatcher("/register/registerForm.jsp").forward(req, resp);

    }

    /**
     * Método que maneja las solicitudes POST al servlet para registrar un usuario.
     * Obtiene los datos del usuario del objeto HttpServletRequest y llama al servicio de usuarios para registrar al usuario.
     * Si el registro es exitoso, se envía un correo de bienvenida al usuario y se redirige a la página de inicio con una bandera indicando el registro exitoso.
     * Si hay algún error durante el proceso de registro, se muestra un mensaje de error en el objeto HttpServletRequest y se redirige al formulario de registro.
     *
     * @param req  La solicitud HTTP.
     * @param resp La respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de E/S.
     */
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

    /**
     * Retorna un objeto UserDto basado en los parámetros de la solicitud HttpServletRequest.
     *
     * @param req La solicitud HttpServletRequest que contiene los parámetros necesarios para construir el objeto UserDto.
     * @return Un objeto UserDto creado a partir de los parámetros de la solicitud.
     */
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