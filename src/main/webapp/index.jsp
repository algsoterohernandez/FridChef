<%@ page language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.IngredientDto" %>
<% List<IngredientDto> ingredients = (List<IngredientDto>) request.getAttribute("IngredientList"); %>

<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>FridChef</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Amatic SC">
        <script src="https://kit.fontawesome.com/b481faf5db.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="css/style.css">
        <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
        <script src="js/buscador.js" defer></script>
    </head>
    <body>
        <div class="content">
            <%@ include file="header/header.jsp" %>
                <div class="principal-container">
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
                    <div class="search-right">
                        <div class="chef-image-right"><img src="./images/chef.png"/></div>
                    </div>
                </div>
            <%@ include file="footer/footer.jsp" %>
        </div>
    </body>
</html>
