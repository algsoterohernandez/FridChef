<%@ page language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.IngredientDto" %>
<% List<IngredientDto> ingredients = (List<IngredientDto>) request.getAttribute("IngredientList"); %>

<html lang="es">
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
            <%@ include file="header/header.jsp" %>

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
            </div>

            <%@ include file="footer/footer.jsp" %>
        </div>
    </body>
</html>
