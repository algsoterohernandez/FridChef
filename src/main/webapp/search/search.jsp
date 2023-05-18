<%@ page import="java.util.List" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.RecipeDto" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.IngredientDto" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.AllergenDto" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
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
    <script src="js/filter.js" defer></script>
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
                    <h2><%= recipe.getName() %></h2>
                    <h3>Ingredientes:</h3>
                    <% for (IngredientDto ingredient : recipe.getIngredients()) { %>
                        <span><%=ingredient.getName()%></span> ·
                    <% } %>
                    <p><span>Descripción:</span> <%= recipe.getDescription() %> </p>
                    <p><span>Dificultad:</span> <%= recipe.getDifficulty() %></p>
                    <p><span>Tiempo de preparación:</span> <%= recipe.getTime() %> <%= recipe.getUnit_time() %></p>
                </div>
            <% } %>
        <% } %>
        <% if (recipeSuggestions.isEmpty()) { %>
            <p>¡Prueba con otros ingredientes!</p>
        <% } else { %>
            <div class="allergen-list">
                <span>Con algunos ingredientes más, podrías cocinar todo esto!</span>
                <p></p>
                <label for="allergen-filter-options">Mostrar recetas que no contengan:</label>
                <select id="allergen-filter-options">
                    <option value="none">Selecciona alérgeno</option>
                    <% for (AllergenDto allergenDto : allergenDtoList) { %>
                    <option value="<%= allergenDto.getName()%>"><%= allergenDto.getName()%></option>
                    <% } %>
                </select>
                <button id="allergen-filter-button" type="submit">Filtrar</button>
            </div>
            <% for (RecipeDto recipeSuggestion : recipeSuggestions) { %>
                <% Set<AllergenDto> recipeAllergens = new HashSet<>();%>
                <div class="recipe-suggestions">
                    <h2><%= recipeSuggestion.getName() %></h2>

                    <h3>Ingredientes:</h3>
                    <% for (IngredientDto ingredient : recipeSuggestion.getIngredients()) { %>
                        <span><%=ingredient.getName()%></span> ·

                        <% for (AllergenDto allergen : ingredient.getAllergens()) {
                            recipeAllergens.add(allergen);
                        }%>
                    <% } %>

                    <p><%= recipeSuggestion.getDescription() %></p>

                    <p><span>Dificultad:</span> <%= recipeSuggestion.getDifficulty() %> · <span>Tiempo de preparación:</span> <%= recipeSuggestion.getTime() %> <%= recipeSuggestion.getUnit_time() %></p>

                    <h3>Alergenos:</h3>
                    <% for(AllergenDto allergen : recipeAllergens) {%>
                        <span class="recipe-allergen"><%=allergen.getName()%></span> ·
                    <% } %>
                </div>
                <p></p>
            <% } %>
        <% } %>

        <%@ include file="../footer/footer.jsp" %>
    </div>
</div>
</body>
</html>