
package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.SenderEmailService;
import com.fpdual.javaweb.web.servlet.dto.UserDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

@WebServlet(name = "ContactServlet", urlPatterns = "/contact")
public class ContactServlet extends ParentServlet {

    private SenderEmailService senderEmail;

    @Override
    public void init() {
        FridChefApiClient apiClient = new FridChefApiClient();
        senderEmail = new SenderEmailService(new Properties(), new Properties());
        super.init(apiClient);

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        req.getRequestDispatcher("/contact/contact.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.fillCategories(req);
        try
        {
            //Envio de Email de bienvenida
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
