<%@ page language="java" %>
<%@ page pageEncoding="UTF-8" %>
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
</head>
<body>
    <div class="content">
        <%@ include file="../header/header.jsp" %>
        <% if(searchUser.isAdmin()){%>
            <div class="principal-container">
                <div class="search">

                    <div>
                        <h1>Administración de ingredientes</h1>
                    </div>
                    <div class="add-ingredient">
                        Añadir ingrediente:
                        <label for="add-ingredient">Ingrediente:</label>
                        <input id="add-ingredient" name="add-ingredients"/>
                    </div>

                    <div class="delete-ingredient">
                        Borrar ingredientes:
                        <label for="delete-ingredient">Ingredientes:</label>
                        <input list="ingredient-list" id="delete-ingredient" name="ingredientes">
                        <datalist id="ingredient-list">
                            <% for (IngredientDto ingredient : ingredients) { %>
                            <option value="<%= ingredient.getName()%>"></option>
                            <% } %>
                        </datalist>
                        <button type="button" id="delete-ingredient-button">Borrar</button>
                    </div>
                </div>
            </div>
        <%}%>
        <%@ include file="../footer/footer.jsp" %>
    </div>
</body>
</html>
