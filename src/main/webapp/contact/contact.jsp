<%--
  Created by IntelliJ IDEA.
  User: a.carmona.garrido
  Date: 5/26/2023
  Time: 8:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contacto</title>
    <meta charset="UTF-8">
    <title>FridChef</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu|Fredoka One|Amatic SC">
    <link rel="stylesheet" href="./css/style.css">
    <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
</head>
    <body>
        <div class="content">
            <%@ include file="../header/header.jsp" %>
            <div class="contact-form">
                <h2>Tienes algo que contarnos? quieres que añadamos algún nuevo ingrediente?</h2>
                <h2>¡Contacta con nosotros!</h2>
                <form id="contact-form" action="/Fridchef/contact" method="POST" enctype="text/plain">
                    <input name="name" type="text" class="feedback-input" placeholder="Nombre" />
                    <input name="email" type="text" class="feedback-input" placeholder="Email" />
                    <textarea name="text" class="feedback-input" placeholder="comentario"></textarea>
                    <input type="submit" value="Enviar" form="contact-form" onclick="this.form.reset();"/>
                </form>
            </div>
            <%@ include file="../footer/footer.jsp" %>
        </div>
    </body>
</html>
