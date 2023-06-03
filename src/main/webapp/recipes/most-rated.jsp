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
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu|Fredoka One|Amatic SC">
    <link rel="stylesheet" href="css/style.css">
    <link rel="shortcut icon" href="/images/logo.jpg" type="image/icon">
    <script src="https://kit.fontawesome.com/b481faf5db.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="content">
    <%@ include file="../header/header.jsp" %>
    <div class="contprincipal">
        <h2>Las recetas mejor valoradas:</h2>

        <% if (!recipes.isEmpty()) { %>
            <%for (RecipeDto recipe : recipes){%>
                <div class="recipes-list">
                    <a href="/FridChef/details-recipe?id=<%= recipe.getId() %>">
                        <div class="recipe-content">
                            <h2><%= recipe.getName() %></h2>
                            <br/>
                            <% for (IngredientRecipeDto ingredient : recipe.getIngredients()) { %>
                            <span><b><%=ingredient.getNameIngredient()%></b></span> ·
                            <% } %>
                            <p><span><b>Descripción:</b></span> <%= recipe.getDescription() %> </p>
                            <p><span><b>Dificultad:</b></span> <%= recipe.getDifficulty() %> · <b>Tiempo de preparación:</b></span> <%= recipe.getTime() %> <%= recipe.getUnitTime() %></p>
                        </div>
                    </a>
                    <div class="image-content">
                        <% if (recipe.getImageBase64() != null) { %>
                        <img src="data:image/jpeg;base64,<%= recipe.getImageBase64() %>">
                        <% } else { %>
                        <p>No hay Imagen</p>
                        <% } %>
                    </div>
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