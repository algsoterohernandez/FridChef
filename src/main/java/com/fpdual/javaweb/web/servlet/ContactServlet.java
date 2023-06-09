
package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.SenderEmailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Properties;

/**
 * Servlet que maneja las peticiones relacionadas con el formulario de contacto.
 */
@WebServlet(name = "ContactServlet", urlPatterns = "/contact")
public class ContactServlet extends ParentServlet {

    private SenderEmailService senderEmail;

    /**
     * Método de inicialización del servlet.
     * Crea una instancia de FridChefApiClient y SenderEmailService para manejar el envío de emails.
     * Llama al método init de la clase padre (HttpServlet) pasando el cliente de la API como parámetro.
     */
    @Override
    public void init() {
        FridChefApiClient apiClient = new FridChefApiClient();
        senderEmail = new SenderEmailService(new Properties(), new Properties());
        super.init(apiClient);

    }

    /**
     * Método que maneja las solicitudes GET enviadas al servlet.
     * Rellena las categorías en la solicitud HttpServletRequest utilizando el método fillCategories().
     * Luego, redirige la solicitud a la página "/contact/contact.jsp" para mostrar el formulario de contacto.
     *
     * @param req  la solicitud HttpServletRequest enviada por el cliente
     * @param resp la respuesta HttpServletResponse que se enviará al cliente
     * @throws ServletException sí ocurre algún error durante el procesamiento del servlet
     * @throws IOException      sí ocurre algún error de E/S durante el procesamiento del servlet
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        req.getRequestDispatcher("/contact/contact.jsp").forward(req, resp);
    }

    /**
     * Método que maneja las solicitudes POST del servlet.
     * Llena las categorías en la solicitud HttpServletRequest.
     * Realiza el envío de un correo electrónico de bienvenida.
     *
     * @param req La solicitud HttpServletRequest recibida.
     * @param resp La respuesta HttpServletResponse a enviar.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        try
        {
            //Email de bienvenida
            String from = req.getParameter("email");
            String to = "fridcheffpdual@gmail.com";
            String subject = req.getParameter("name");

            String content = req.getParameter("text");

            boolean success = senderEmail.sendEmail(from, to, subject, content);
            if(success)
            {
                req.setAttribute("success", "true");
            }
            else
            {
                req.setAttribute("error", "Formulario inválido.");
            }

        }catch(Exception ex){
            req.setAttribute("error", "Formulario inválido.");
        }

        req.getRequestDispatcher("/contact/contact.jsp").forward(req, resp);

    }
}