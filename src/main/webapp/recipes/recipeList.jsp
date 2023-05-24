<%@ page import="com.fpdual.javaweb.web.servlet.dto.RecipeDto" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de recetas por categoría</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="content">
    <%@ include file="/header/header.jsp" %>

    <div class="contprincipal">
        <h2>Lista de recetas por categoría</h2>

        <% List<RecipeDto> recipes = (List<RecipeDto>) request.getAttribute("RecipeList"); %>

        <% if (recipes != null && !recipes.isEmpty()) { %>
        <ul>
            <% for (RecipeDto recipe : recipes) { %>
            <li>
                <h3><%= recipe.getName() %></h3>
                <p><%= recipe.getDescription() %></p>
                <p>Categoría: <%= recipe.getIdCategory() %></p>
            </li>
            <% } %>
        </ul>
        <% } else { %>
        <p>No se encontraron recetas para esta categoría.</p>
        <% } %>
    </div>

    <%@ include file="/footer/footer.jsp" %>
</div>
</body>
</html>

