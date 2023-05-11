<%@ page import="java.util.List" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.RecipeDto" %>
<%
    List<RecipeDto> recipesList = (List< RecipeDto>) request.getAttribute("recipes"); %>
<html>
<head>
    <title>Lista de recetas</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>Nombre</th>
        <th>Descripción</th>
        <th>Dificultad</th>
        <th>Tiempo de preparación</th>

<%--        <th>Image</th>--%>
    </tr>
    </thead>
    <tbody>
    <% for (RecipeDto recipe : recipesList) { %>
    <tr>
        <td><%= recipe.getName() %></td>
        <td><%= recipe.getDescription() %></td>
        <td><%= recipe.getDifficulty() %></td>
        <td><%= recipe.getTime() %> <%= recipe.getUnit_time() %></td>

<%--        <td><img src="data:image/jpeg;base64,<%= recipe.getImage() %>" /></td>--%>
    </tr>
    <% } %>
    </tbody>
</table>
</body>
</html>