<%@ page pageEncoding="UTF-16BE" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>FridChef</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu|Fredoka One|Amatic SC">
        <link rel="stylesheet" href="css/style.css">
        <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
    </head>
    <body>
        <div class="content">
            <%@ include file="../header/header.jsp" %>

            <div class = "main-form">
                <h1>Registrate en Fridchef</h1>
                <form action="/FridChef/register-form" method="POST">
                    <div class="form">
                        <div class="form-input">
                            <label for="name">Nombre:</label></br>
                            <input type="text" id= "name" name="name" minlength="2" maxlength="50" required/>
                        </div>
                        <div class="form-input">
                            <label for="surname1">Primer apellido: </label></br>
                            <input type="text" id= "surname1" name="surname1" minlength="2" maxlength="50" required/>
                        </div>
                        <div class="form-input">
                            <label for="surname2">Segundo apellido: </label></br>
                            <input type="text" id= "surname2" name="surname2" minlength="2" maxlength="50"/>
                        </div>
                        <div class="form-input">
                            <label for="email">Email: </label></br>
                            <input type="email" id= "email" name="email" placeholder="email@email.com" required/>
                        </div>
                        <div class="form-input">
                            <label for="password">Contraseña: </label></br>
                            <input type="password" id= "password" name="password" pattern="[A-Za-z0-9]{8-10}"
                                   title="Introduce una contraseña de 8 a 10 letras o numeros" minlength="8"
                                   maxlength="10" required/>
                        </div>
                        <div class="buttons">
                            <input type="submit" value="Enviar">
                            <input type="reset" value="Borrar">
                        </div>
                    </div>
                </form>
            </div>
            <%@ include file="../footer/footer.jsp" %>
        </div>
        <% if (request.getAttribute("error")!=null) { %>
        <script> alert("El usuario ya existe en nuestro sistema."); </script>
        <% } %>
    </body>
</html>
