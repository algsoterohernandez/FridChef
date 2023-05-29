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
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Amatic SC">
    <link rel="stylesheet" href="./css/style.css">
    <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
</head>
    <body>
        <div class="content">
            <%@ include file="../header/header.jsp" %>
            <div class="feedbak-content">
                <div class="contact-form-left">
                    <form id="contact-form" action="fridcheffpdual@gmail.com" method="POST" enctype="text/plain">
                        <input name="name" type="text" class="feedback-input" placeholder="Nombre" />
                        <input name="email" type="text" class="feedback-input" placeholder="Email" />
                        <textarea name="text" class="feedback-input" placeholder="comentario"></textarea>
                        <input type="submit" value="Enviar" form="contact-form" onclick="this.form.reset();"/>
                    </form>
                </div>
                <div class="contact-form-rigth">
                    <h1>¡Contacta con nosotros!</h1>
                    <p>Tienes algo que contarnos? quieres que añadamos algún nuevo ingrediente?</p>
                </div>
            </div>
            <%@ include file="../footer/footer.jsp" %>
        </div>
    </body>
</html>
