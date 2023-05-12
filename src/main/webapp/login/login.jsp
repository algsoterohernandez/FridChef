<%@ page pageEncoding="UTF-16" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>FridChef</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu|Fredoka One|Amatic SC">
    <link rel="stylesheet" href="css/pruebaasun.css">
    <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
    <script src="js/buscador.js" defer></script>
</head>
<body>
<div class="content">
    <div class="login">
        <div class="cabecera">
            <h1>FridChef</h1>
        </div>
        <div class="menu">
            <a href="/FridChef">Inicio</a>
            <a href="#">Recetas</a>
            <a href="#">recetas favoritas</a>
            <a href="#">Agregar recetas</a>
            <a href="/login">login</a>
        </div>
        <div class="contprincipal">
            <h1>Accede a Fridchef!!!</h1>
            <form action="/FridChef/login" method="POST">
                <div class="login">
                    <div class="login-input">
                        <p><label for="email">Email: </label></p>
                        <p><input type="email" id="email" name="email" placeholder="email@email.com" required/></p>
                    </div>

                    <p><label for="password">Contraseña: </label></p>
                    <p>
                        <input type="password" id="password" name="password" pattern="[A-Za-z0-9]{8-10}"
                               title="Introduce una contraseña de 8 a 10 letras o numeros" minlength="8"
                               maxlength="10" required/>
                    </p>
                </div>
                <div class="buttons">
                    <input type="submit" value="Enviar">
                    <input type="reset" value="Borrar">
                </div>
        </div>
        </form>
    </div>

    <% if (request.getAttribute("error") != null) { %>
    <div>
        <h3 style="color:red"><%=request.getAttribute("error")%>
        </h3>
    </div>
    <%}%>
</div>
<div class="pie">
    <table class="table-pie">
        <tr>
            <td>
                <a href="https://instagram.com" target="_blank"><i class="fa-brands fa-instagram"></i></a>
            </td>
            <td>
                <a href="https://twitter.com" target="_blank"><i class="fa-brands fa-square-twitter"></i></a>
            </td>
            <td>
                <a href="https://facebook.com" target="_blank"><i class="fa-brands fa-facebook"></i></a>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                ¡Síguenos!
            </td>
        </tr>
    </table>
</div>
</div>
</body>
</html>
