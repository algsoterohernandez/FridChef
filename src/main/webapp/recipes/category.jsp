<%@ page import="com.fpdual.javaweb.web.servlet.dto.RecipeDto" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% List<RecipeDto> recipes = (List<RecipeDto>) request.getAttribute("recipes"); %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Lista de recetas por categoría</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Amatic SC|Jost">
        <script src="https://kit.fontawesome.com/b481faf5db.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="content">
            <%@ include file="/header/header.jsp" %>
            <div class="contprincipal">
                <h2>Lista de recetas por categoría</h2>
                <br/>
                <% if (recipes != null && !recipes.isEmpty()) { %>
                    <% for (RecipeDto recipe : recipes) { %>
                        <br/>
                        <div class="recipes-list">
                            <a href="/FridChef/details-recipe?id=<%= recipe.getId() %>">
                                <div class="recipe-content">
                                    <h2><%= recipe.getName() %></h2>
                                    <br/>
                                    <% for (IngredientRecipeDto ingredient : recipe.getIngredients()) { %>
                                    <span><b><%=ingredient.getNameIngredient()%></b></span> ·
                                    <% } %>
                                    <p><span><b>Descripción:</b></span> <%= recipe.getDescription().substring(0, Math.min(recipe.getDescription().length(), 200)) %> ...</p>
                                    <p><span><b>Dificultad:</b></span> <%= recipe.getDifficulty() %>· <span><b>Tiempo de preparación:</b></span> <%= recipe.getTime() %> <%= recipe.getUnitTime() %></p>
                                </div>
                                <div class="image-content">
                                    <% if (recipe.getImageBase64() != null) { %>
                                    <img src="data:image/jpeg;base64,<%= recipe.getImageBase64() %>">
                                    <% } else { %>
                                    <p>No hay Imagen</p>
                                    <% } %>
                                </div>
                            </a>
                        </div>
                    <% } %>
                <% } else { %>
                    <div class="principal-container">
                        <p>No se encontraron recetas para esta categoría. Busca en otra categoría.</p>
                    </div>
                <% } %>
            </div>
            <%@ include file="/footer/footer.jsp" %>
        </div>
    </body>
</html>

