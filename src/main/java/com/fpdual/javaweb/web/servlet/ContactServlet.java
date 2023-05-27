
package com.fpdual.javaweb.web.servlet;

import com.fpdual.javaweb.client.FridChefApiClient;
import com.fpdual.javaweb.service.SenderEmailService;
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

        //Envio de Email de bienvenida
        String from = "fridcheffpdual@gmail.com";
        String to = "fridcheffpdual@gmail.com";
        String subject = "";

        String content = "";

        senderEmail.sendEmail(from, to, subject, content);

        req.getRequestDispatcher("/contact/contact.jsp").forward(req, resp);

    }
}
