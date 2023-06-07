<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.*" %>
<%@ page import="java.util.*" %>
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
    <script src="https://kit.fontawesome.com/b481faf5db.js" crossorigin="anonymous"></script>
    <script src="js/buscador.js" defer></script>
    <script src="js/filter.js" defer></script>
</head>
<body>
<div class="content">
    <%@ include file="../header/header.jsp" %>
    <div class="contprincipal">
        <div class="search">
            <div class="search-left">

                    <h1>En mi nevera tengo...</h1>
                    <input list="ingrediente1" id="add-ingredient" name="ingredientes"  placeholder="Ingrediente">
                    <datalist id="ingrediente1">
                        <% for (IngredientDto ingredient : ingredients) { %>
                        <option value="<%= ingredient.getName()%>"></option>
                        <% } %>
                    </datalist>
                    <button type="button" id="add-ingredient-button">Agregar</button>
                    <p></p>
                    <div class="ingredients-container" id="ingredients-container"></div>
                    <br/>
                    <button type="button" id="search-button">Buscar</button>
                    <form action="search" method="post" id="form">
                    </form>

            </div>
        </div>
        <% if (recipesList.isEmpty()) { %>
        <p>No se han encontrado recetas con estos ingredientes</p>
        <% } else {
            for (RecipeDto recipe : recipesList) {
        %>
        <div class="recipes-list">
            <a href="/FridChef/details-recipe?id=<%= recipe.getId() %>">
                <div class="recipe-content">
                    <h2><%= recipe.getName() %></h2>
                    <br/>
                    <% for (IngredientRecipeDto ingredient : recipe.getIngredients()) { %>
                    <span><b><%=ingredient.getNameIngredient()%></b></span> ·
                    <% } %>
                    <p><span><b>Descripción:</b></span> <%= recipe.getDescription().substring(0, Math.min(recipe.getDescription().length(), 200)) %> ...</p>
                    <p><span><b>Dificultad:</b></span> <%= recipe.getDifficulty() %> · <b>Tiempo de preparación:</b></span> <%= recipe.getTime() %> <%= recipe.getUnitTime() %></p>
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
        <%
                }
            }
        %>
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
                <option value="<%= allergenDto.getName()%>"><%= allergenDto.getName()%>
                </option>
                <% } %>
            </select>
            <button id="allergen-filter-button" type="submit">Filtrar</button>
        </div>
        <% for (RecipeDto recipeSuggestion : recipeSuggestions) { %>
        <% Set<AllergenDto> recipeAllergens = new HashSet<>();%>
        <div class="recipe-suggestions">
            <div class="recipe-content">
                <a href="/FridChef/details-recipe?id=<%= recipeSuggestion.getId() %>">
                    <h2><%= recipeSuggestion.getName() %></h2>
                    <br/>
                        <% for (IngredientRecipeDto ingredient : recipeSuggestion.getIngredients()) { %>
                    <b><span><%=ingredient.getNameIngredient()%></span> ·</b>
                        <% recipeAllergens.addAll(ingredient.getAllergens());%>
                        <% } %>
                    <p><span><b>Descripción:</b></span> <%= recipeSuggestion.getDescription().substring(0, Math.min(recipeSuggestion.getDescription().length(), 200)) %> ...</p>
                    <p><span><b>Dificultad:</b></span> <%= recipeSuggestion.getDifficulty() %> ·
                        <span><b>Tiempo de preparación:</b></span> <%= recipeSuggestion.getTime() %> <%= recipeSuggestion.getUnitTime() %>
                    </p>
                    <p><b>Alergenos:</b>
                        <% for(AllergenDto allergen : recipeAllergens) {%>
                        <span class="recipe-allergen"><%=allergen.getName()%></span> ·
                        <% } %>
                    </p>
            </div>
            <div class="image-content">
                <% if (recipeSuggestion.getImageBase64() != null) { %>
                <img src="data:image/jpeg;base64,<%= recipeSuggestion.getImageBase64() %>">
                <% } else { %>
                <p>No hay Imagen</p>
                <% } %>
            </div>
        </div>
        </a>
        <p></p>
        <% } %>
        <% } %>
    </div>
    <%@ include file="../footer/footer.jsp" %>
</div>
</body>
</html>