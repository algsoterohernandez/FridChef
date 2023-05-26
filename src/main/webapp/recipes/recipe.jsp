<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.RecipeDto" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.IngredientRecipeDto" %>
<% RecipeDto recipe = (RecipeDto) request.getAttribute("recipe"); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FridChef</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu|Fredoka One|Amatic SC">
    <link rel="stylesheet" href="css/style.css">
    <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
    <script src="js/buscador.js" defer></script>
    <script src="js/filter.js" defer></script>
</head>
<body>
<div class="content">
    <%@ include file="../header/header.jsp" %>
    <h1><%= recipe.getName() %></h1>
    <div class="image">
        <h2>Imagen</h2>
        <div class="image-content">
            <% if (recipe.getImageBase64() != null) { %>
            <img src="data:image/jpeg;base64,<%= recipe.getImageBase64() %>">
            <% } else { %>
            <p>No hay Imagen</p>
            <% } %>
        </div>
    </div>
    <div class="ingredients">
        <h3>Ingredientes</h3>
        <% for (IngredientRecipeDto ingredient : recipe.getIngredients()) { %>
        <span><%=ingredient.getNameIngredient()%></span> ·
        <% } %>
    </div>
    <div class="recipe-details">
        <div class="description">
            <h2>Elaboración</h2>
            <p><%= recipe.getDescription() %></p>
        </div>

        <div class="difficulty">
            <h2>Dificultad</h2>
            <p><%= recipe.getDifficulty() %></p>
        </div>
        <div class="category">
            <h2>Categoria</h2>
            <p><%= recipe.getIdCategory() %></p>
        </div>
        <div class="unit-time">
            <h2>Unidades</h2>
            <p><%= recipe.getUnitTime() %></p>
        </div>
    </div>

    <%@ include file="../footer/footer.jsp" %>
</div>
</body>
</html>