<%@ page language="java" %>

<%@ page import="com.fpdual.javaweb.persistence.dao.IngredientDao" %>

<%@ page import="java.util.List" %>
<% List<IngredientDao> ingredients = (List<IngredientDao>) request.getAttribute("IngredientList"); %>

<html>
<head>
    <title>Hola Mundo!</title>
    <script src="js/buscador.js" defer></script>
</head>
<body>
<h1>FridChef! </h1>

<p>En mi nevera tengo...</p>

<form action="" method="post" target="_blank">
</form>

<p></p>

<form>
 <label for="add-ingredient">Ingrediente:</label>
    <input list="ingrediente1" id="add-ingredient" name="ingredientes">
    <datalist id="ingrediente1">
        <% for (IngredientDao ingredient : ingredients) { %>
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
