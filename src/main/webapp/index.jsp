<%@ page language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.IngredientDto" %>
<% List<IngredientDto> ingredients = (List< IngredientDto>) request.getAttribute("IngredientList"); %>

<html>
<head>
    <meta charset="UTF-8">
    <title>FridChef</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
    <script src="js/buscador.js" defer></script>
</head>
<body>
<header>
    <nav>
        <ul>
            <li><a href="#">Inicio</a></li>
            <li><a href="#">Recetas</a></li>
            <li><a href="#">recetas favoritas</a></li>
            <li><a href="#">Agregar recetas</a></li>
            <li><a href="#">login</a></li>
        </ul>
    </nav>
</header>

<h1>FridChef</h1>

<p>En mi nevera tengo...</p>

<form action="" method="post" target="_blank">
</form>

<p></p>

<form>
 <label for="add-ingredient">Ingrediente:</label>
    <input list="ingrediente1" id="add-ingredient" name="ingredientes">
    <datalist id="ingrediente1">
        <% for (IngredientDto ingredient : ingredients) { %>
        <option value="<%= ingredient.getName()%>"></option>
        <% } %>
    </datalist>
    <button type="button" id="add-ingredient-button">Agregar</button>
<p></p>
    Opciones:
    <div id="ingredients-container"></div>

    <button type="button" id="search-button">Buscar</button>
</form>
</body>
</html>
