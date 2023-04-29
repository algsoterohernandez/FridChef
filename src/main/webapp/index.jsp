<%@ page language="java" %>
<%@ page import = "com.fpdual.javaweb.persistence.manager.dao.Ingredient"%>
<%@ page import="java.util.List" %>
<% List<Ingredient> ingredients = (List<Ingredient>) request.getAttribute("IngredientList"); %>

<html>
<head>
    <title>Hola Mundo!</title>
</head>
<body>
<h1>FridChef! </h1>

<form action="" method="post" target="_blank">
    <p>En mi nevera tengo...

    </p>
    <select name="IngredientList">
        <option value="">--Seleccione un ingrediente--</option>
        <% for (Ingredient ingredient : ingredients) { %>
        <option value="<%= ingredient.getName() %>"><%= ingredient.getName() %>
        </option>
        <%}%>
    </select>
    <select name="IngredientList">
        <option value="">--Seleccione un ingrediente--</option>
        <% for (Ingredient ingredient : ingredients) { %>
        <option value="<%= ingredient.getName() %>"><%= ingredient.getName() %>
        </option>
        <%}%>
    </select>
    <select name="IngredientList">
        <option value="">--Seleccione un ingrediente--</option>
        <% for (Ingredient ingredient : ingredients) { %>
        <option value="<%= ingredient.getName() %>"><%= ingredient.getName() %>
        </option>
        <%}%>
    </select>
    <input type="submit" value="Añadir mas ingredientes">
    <input type="submit" value="Buscar Recetas">
</form>

</body>
</html>
