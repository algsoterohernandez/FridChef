package com.fpdual.javaweb;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet(name = "ServletFormulario", urlPatterns = {"/servlet-register-form"})
public class ServletFormulario extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Ver como crear un componente y pintar jsp en servlet en lugar de html5
        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        writer.println("<body>");
        writer.println("<h2>Parametro Conocido: Nombre --> " + req.getParameter("nombre") + "</h2>");
        Enumeration<String> parameters = req.getParameterNames();
        while (parameters.hasMoreElements()) {
            String name = parameters.nextElement();
            writer.println("<h2>Parametro No Conocido: " + name + " --> " + req.getParameter(name) + "</h2>");
        }
        writer.println("</body>");
        writer.println("</html>");
    }
}