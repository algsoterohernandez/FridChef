<%@ page import="com.fpdual.javaweb.web.servlet.dto.RecipeDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% RecipeDto recipe = (RecipeDto) request.getAttribute("recipe");%>
<% Boolean notUser = (Boolean) request.getAttribute("not_user");%>
<html>
<head>
    <meta charset="UTF-8">
    <title>FridChef</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu|Fredoka One|Amatic SC">
    <link rel="stylesheet" href="css/style.css">
    <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
    <script src="js/details-recipe.js" defer></script>
</head>
<body>
<div class="content">
    <% if (!notUser) { %>
    <div class="header">
        <h1><%=recipe.getName()%></h1>
    </div>
    <% }%>

    <div class="menu">
        <a href="#">Enlace de menú 1</a>
        <a href="#">Enlace de menú 2</a>
        <a href="#">Enlace de menú 3</a>
    </div>
    <% if (notUser) { %>
        Registrate!!
    <% } else { %>
        <div class="recipe-details">
        <%-- Aquí puedes mostrar los detalles de la receta --%>
        <h2>Título de la receta</h2>
        <p>Descripción de la receta</p>
        <p>Duración: XX minutos</p>
        <p>Fecha de creación: XX/XX/XXXX</p>
        <%-- Otros detalles de la receta... --%>
    </div>
    <% } %>
</div>

<div class="footer">
    <p>Pie de página</p>
</div>
</body>
</html>
