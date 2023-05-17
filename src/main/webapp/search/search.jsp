<%@ page import="java.util.List" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.RecipeDto" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.IngredientDto" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.AllergenDto" %>
<% List<RecipeDto> recipesList = (List<RecipeDto>) request.getAttribute("recipes");%>
<% List<IngredientDto> ingredients = (List<IngredientDto>) request.getAttribute("IngredientList"); %>
<% List<AllergenDto> allergenDtoList = (List<AllergenDto>) request.getAttribute("AllergenDtoList"); %>
<% List<RecipeDto> recipeSuggestions = (List<RecipeDto>) request.getAttribute("recipeSuggestions"); %>


<html>
<head>
    <meta charset="UTF-8">
    <title>FridChef</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu|Fredoka One|Amatic SC">
    <link rel="stylesheet" href="css/style.css">
    <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
    <script src="js/buscador.js" defer></script>
</head>
<body>
<div class="content">
    <%@ include file="../header/header.jsp" %>
    <div class="contprincipal">
        <div class="buscador">
            <h2>En mi nevera tengo...</h2>

            <label for="add-ingredient">Ingrediente:</label>
            <input list="ingrediente1" id="add-ingredient" name="ingredientes">
            <datalist id="ingrediente1">
                <% for (IngredientDto ingredient : ingredients) { %>
                <option value="<%= ingredient.getName()%>"></option>
                <% } %>
            </datalist>
            <button type="button" id="add-ingredient-button">Agregar</button>
            <p></p>
            Opciones:
            <div class="ingredients-container" id="ingredients-container"></div>

            <button type="button" id="search-button">Buscar</button>

            <form action="search" method="post" id="form">
            </form>
        </div>
        <% if (recipesList.isEmpty()) { %>
        <p>No se han encontrado recetas con estos ingredientes</p>
        <% } else { %>
        <% for (RecipeDto recipe : recipesList) { %>
        <div class="recipes-list">
            <h3><%= recipe.getName() %></h3>
            <% for (IngredientDto ingredient : recipe.getIngredients()) { %>
            <span><%=ingredient.getName()%></span>
            <% } %>
            <p><span>Descripción:</span> <%= recipe.getDescription() %>
            </p>
            <p><span>Dificultad:</span> <%= recipe.getDifficulty() %>
            </p>
            <p><span>Tiempo de preparación:</span> <%= recipe.getTime() %> <%= recipe.getUnit_time() %>
            </p>
        </div>
        <% } %>
        <% } %>
        <p>Con algunos ingredientes más, podrías cocinar todo esto!</p>
        <div class="allergen-list">
            <form action="procesar_opcion" method="post">
                <label for="opciones">Filtra tus recetas por alérgenos:</label>
                <select id="opciones" name="opciones">
                    <% for (AllergenDto allergenDto : allergenDtoList) { %>
                    <option value="<%= allergenDto.getId()%>"><%= allergenDto.getName()%>
                    </option>
                    <% } %>
                </select>
                <button type="submit">Filtrar</button>
            </form>
        </div>
        <% if (recipeSuggestions.isEmpty()) { %>
        <p>No se han encontrado recetas sugeridas</p>
        <% } else { %>
        <% for (RecipeDto recipeSuggestion : recipeSuggestions) { %>

        <div class="recipe-suggestions">
            <h3><%= recipeSuggestion.getName() %>
            </h3>
            <p><span>Descripción:</span> <%= recipeSuggestion.getDescription() %>
            </p>
            <p><span>Dificultad:</span> <%= recipeSuggestion.getDifficulty() %>
            </p>
            <p>
                <span>Tiempo de preparación:</span> <%= recipeSuggestion.getTime() %> <%= recipeSuggestion.getUnit_time() %>
            </p>
        </div>
        <p></p>
        <% } %>
        <% } %>

        <%@ include file="../footer/footer.jsp" %>
    </div>
</div>
</body>
</html>