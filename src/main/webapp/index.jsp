<%@ page language="java" %>
<%@ page import="com.fpdual.javaweb.persistence.dao.Ingredient" %>
<%@ page import="java.util.List" %>
<% List<Ingredient> ingredients = (List<Ingredient>) request.getAttribute("IngredientList"); %>

<html>
<head>
    <title>Hola Mundo!</title>
</head>
<body>
<h1>FridChef! </h1>

<form action="" method="post" target="_blank">
    <p>En mi nevera tengo...</p>
    <label for="ingrediente1">Ingrediente 1:</label>
    <input list="ingrediente1" id="ingrediente-1" name="frutas">
    <datalist id="ingrediente1">
        <% for (Ingredient ingredient : ingredients) { %>
        <option value="<%= ingredient.getId()%>" label="<%= ingredient.getName()%>"></option>
                <% } %>
    </datalist>

    <input type="submit" value="Añadir">
</form>

</body>
</html>
