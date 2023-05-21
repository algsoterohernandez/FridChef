<%@ page pageEncoding="UTF-16" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>FridChef</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
<body>
<div class="login">
    <h1>Accede a Fridchef!!!</h1>
    <form action="/FridChef/login" method="POST">
        <div class="login">
            <div class="login-input">
                <label for="email">Email: </label>
                <input type="email" id="email" name="email" placeholder="email@email.com" required/>
            </div>
            <div class="login-input">
                <label for="password">Contraseña: </label>
                <input type="password" id="password" name="password" pattern="[A-Za-z0-9]{8-10}"
                       title="Introduce una contraseña de 8 a 10 letras o numeros" minlength="8"
                       maxlength="10" required/>
            </div>
            <div class="buttons">
                <input type="submit" value="Enviar">
                <input type="reset" value="Borrar">
            </div>
        </div>
    </form>
    <% if (request.getAttribute("error") != null) { %>
    <div>
        <h3 style="color:red"><%=request.getAttribute("error")%>
        </h3>
    </div>
    <%}%>
</div>
</body>
</html>
