<%@ page import="com.fpdual.javaweb.web.servlet.dto.RecipeDto" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% List<RecipeDto> recipes = (List<RecipeDto>) request.getAttribute("recipes"); %>

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

        <% if (recipes != null && !recipes.isEmpty()) { %>
        <% for (RecipeDto recipe : recipes) { %>
        <div class="recipes-list">
            <a href="/FridChef/category">
                <h3>Ingredientes:</h3>
                <% for (IngredientRecipeDto ingredient : recipe.getIngredients()) { %>
                <span><%=ingredient.getNameIngredient()%></span> ·
                <% } %>
                <p><span>Descripción:</span> <%= recipe.getDescription() %> </p>
                <p><span>Dificultad:</span> <%= recipe.getDifficulty() %></p>
                <p><span>Tiempo de preparación:</span> <%= recipe.getTime() %> <%= recipe.getUnitTime() %></p>
            </a>
        </div>

        <% } %>
        <% } else { %>
        <p>No se encontraron recetas para esta categoría. Busca en otra categoría.</p>
        <% } %>
    </div>

    <%@ include file="/footer/footer.jsp" %>
</div>
</body>
</html>

