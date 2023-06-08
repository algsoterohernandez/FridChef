<%@ page import="java.util.ArrayList" %>
<%@ page import="com.fpdual.javaweb.service.RecipeService" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.RecipeDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% List<RecipeDto> recipes = (List<RecipeDto>) request.getAttribute("recipes"); %>

<html>
    <head>
        <meta charset="UTF-8">
        <title>FridChef</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Amatic SC|Jost">
        <link rel="stylesheet" href="css/style.css">
        <link rel="shortcut icon" href="/images/logo.jpg" type="image/icon">
        <script src="https://kit.fontawesome.com/b481faf5db.js" crossorigin="anonymous"></script>
    </head>
    <body>
    <div class="content">
        <%@ include file="../header/header.jsp" %>
        <div class="contprincipal">
            <h2>Top 10 recetas mejor valoradas</h2>
            <br/>
            <% if (!recipes.isEmpty()) { %>
                <%for (RecipeDto recipe : recipes){%>
                    <br/>
                    <div class="recipes-list">
                        <a href="/FridChef/details-recipe?id=<%= recipe.getId() %>">
                            <div class="recipe-content">
                                <div class="ranking-style">
                                    <h2><%= recipe.getName() %></h2>
                                    <p><i class="fa-solid fa-star"></i> <%= recipe.getValoration()%></p>
                                </div>
                                <% for (IngredientRecipeDto ingredient : recipe.getIngredients()) { %>
                                <span><b><%=ingredient.getNameIngredient()%> · </b></span>
                                <% } %>
                                <p><span><b>Descripción:</b></span> <%= recipe.getDescription().substring(0, Math.min(recipe.getDescription().length(), 200)) %> ...</p>
                                <p><span><b>Dificultad:</b></span> <%
                                    int difficulty = recipe.getDifficulty();
                                    if (difficulty == 1) {
                                %>
                                    Muy baja
                                    <%
                                    } else if (difficulty == 2) {
                                    %>
                                    Baja
                                    <%
                                    } else if (difficulty == 3) {
                                    %>
                                    Normal
                                    <%
                                    } else if (difficulty == 4) {
                                    %>
                                    Alta
                                    <%
                                    } else if (difficulty == 5) {
                                    %>
                                    Muy Alta
                                    <%
                                        }
                                    %> | <b>Tiempo de preparación:</b></span> <%= recipe.getTime() %> <%= recipe.getUnitTime() %></p>
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
                <%}%>
                <%}else{%>
                    <p>No hay recetas en este momento disponibles</p>
                <%}%>
            </div>
            <%@ include file="../footer/footer.jsp" %>
        </div>
    </body>
</html>