<html>
    <head>
        <meta charset="UTF-8">
        <title>FridChef</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Amatic SC|Jost">
        <link rel="stylesheet" href="css/style.css">
        <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
        <script src="https://kit.fontawesome.com/b481faf5db.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <div class="content">
            <%@ include file="../header/header.jsp" %>
                <div class="principal-container">
                <div class="login-content">
                    <h1>Únete a la comunidad</h1>
                    <form action="/FridChef/login" method="POST">
                        <div class="login">
                            <div class="login-input">
                                <p><label for="email">Email: </label></p>
                                <p><input type="email" id="email" name="email" placeholder="email@email.com" required/></p>
                                <p><label for="password">Contraseña: </label></p>
                                <p>
                                    <input type="password" id="password" name="password" pattern="[A-Za-z0-9]{8-10}"
                                           title="Introduce una contraseña de 8 a 10 letras o numeros" minlength="8"
                                           maxlength="10" required/>
                                </p>
                            </div>
                        </div>
                        <% if (request.getAttribute("error") != null) { %>
                        <div>
                            <h3 style="color:brown"><%=request.getAttribute("error")%>
                            </h3>
                        </div>
                        <%}%>
                        <div class="buttons">
                            <input type="submit" value="Enviar">
                            <input type="reset" value="Borrar">
                        </div>
                    </form>
                </div>
                </div>
            <%@ include file="../footer/footer.jsp" %>
        </div>
    </body>
</html>
