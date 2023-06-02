<%--
  Created by IntelliJ IDEA.
  User: a.carmona.garrido
  Date: 5/26/2023
  Time: 8:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Object success = request.getAttribute("success");
    Object error = request.getAttribute("error");
%>

<html>
<head>
    <title>Contacto</title>
    <meta charset="UTF-8">
    <title>FridChef</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Amatic SC">
    <link rel="stylesheet" href="./css/style.css">
    <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
    <script src="https://kit.fontawesome.com/b481faf5db.js" crossorigin="anonymous"></script>
</head>
    <body>
        <div class="content">
            <%@ include file="../header/header.jsp" %>
            <div class="principal-container">
            <div class="feedback-content">
                <div class="contact-form-left">
                    <form id="contact-form" action="/FridChef/contact" method="POST">
                        <input id="name-contact" name="name" type="text" class="feedback-input" placeholder="Nombre" required/>
                        <input id="email-contact" name="email" type="email" class="feedback-input" placeholder="Email" required/>
                        <textarea id="text-contact" name="text" class="feedback-input" placeholder="Comentario" required></textarea>
                        <input type="submit" value="Enviar" form="contact-form"/>

                    </form>
                    <% if(success != null) { %>
                        <div class="email-ok">
                            <span>Mensaje enviado</span>
                        </div>
                    <% }else if(error != null){ %>
                    <div class="email-ko">
                        <span style="color: red;"><%= (String) error %></span>
                    </div>
                    <% } %>
                </div>
                <div class="contact-form-rigth">
                    <h1>¡Contacta con nosotros!</h1>
                    <p>Tienes algo que contarnos? quieres que añadamos algún nuevo ingrediente?</p>
                </div>
            </div>
            </div>
            <%@ include file="../footer/footer.jsp" %>
        </div>
    </body>
</html>
