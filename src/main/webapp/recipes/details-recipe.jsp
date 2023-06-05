<%@ page import="com.fpdual.javaweb.web.servlet.dto.RecipeDto" %>
<%@ page import="com.fpdual.javaweb.enums.RecipeStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.RecipeDto" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.IngredientRecipeDto" %>
<%@ page import="com.fpdual.javaweb.enums.RecipeStatus" %>

<% RecipeDto recipe = (RecipeDto) request.getAttribute("recipe");%>
<% List<ValorationDto> valorations = (List<ValorationDto>) request.getAttribute("valorations");%>
<% Boolean isFavorite = (Boolean) request.getAttribute("isFavorite");%>
<% Boolean valorationCreated = (Boolean) request.getAttribute("valorationCreated");%>

<html>
<head>
    <meta charset="UTF-8">
    <title>FridChef</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu|Fredoka One|Amatic SC">
    <link rel="stylesheet" href="css/style.css">
    <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
    <script src="https://kit.fontawesome.com/b481faf5db.js" crossorigin="anonymous"></script>
    <script src="js/details-recipe.js" defer></script>

</head>
<body>
<div class="content">
    <%@ include file="../header/header.jsp" %>
    <% if(searchUser!= null){%>
    <div class="title"><%= recipe.getName() %>
        <%if (searchUser.isAdmin() && recipe.getStatus().equals(RecipeStatus.PENDING.name())) {%>
        <div class="buttons">
            <a href="/FridChef/recipes-accept?id=<%=recipe.getId()%>&status=ACCEPTED">Aceptar</a>
            <a href="/FridChef/recipes-reject?id=<%=recipe.getId()%>&status=DECLINED">Declinar</a>
        </div>
        <% } %>
    </div>
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
    <div class="like">
        <button id="favoriteButton" recipe="<%= recipe.getId() %>" class="favorite-button  <%= isFavorite ? "is-favorite" : "" %>">
            <span class="heart"></span>
        </button>
    </div>
    <div class="recipe-details">
        <h2><%=recipe.getName()%></h2>
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
        <div>
            <h3>Esta receta está valorada con <%= recipe.getValoration() %> puntos</h3>
        </div>
        <div>
            <p><%=recipe.getDescription()%></p>
            <p>duración:<%=recipe.getTime()%> <%=recipe.getUnitTime()%></p>
            <p>Fecha de creación: <%= recipe.getCreateTime() %> </p>
        </div>
    </div>
    <div>
        <h3>Valora esta receta:</h3>
        <form action="/FridChef/details-recipe?id=<%=recipe.getId()%>" method="post">
            <div>
            <label>Puntuación:</label>
            <input type="radio" id="1" name="valoration" value="1">
            <label for="1">1</label><br>
            <input type="radio" id="2" name="valoration" value="2">
            <label for="2">2</label><br>
            <input type="radio" id="3" name="valoration" value="3">
            <label for="3">3</label><br>
            <input type="radio" id="4" name="valoration" value="4">
            <label for="4">4</label><br>
            <input type="radio" id="5" name="valoration" value="5">
            <label for="5">5</label>
            </div>
            <div>
                <label for="comment">Comentario: </label><br/>
                <textarea type="textarea" id= "comment" name="comment" minlength="10" maxlength="500" rows="10" cols="100" placeholder="Escribe los pasos de elaboración..." required></textarea>
            </div>
            <div class="buttons">
                <input type="submit" value="Enviar">
            </div>
        </form>

        <div class="comments">
            <h3>Comentarios</h3>
            <% for (ValorationDto valoration :valorations) { %>
            <div class="comment">
                <span class="user">User: <%= valoration.getNameUser() %></span>
                <span class="user">Rate: <%= valoration.getValoration() %></span>
                <p>Comment: <%= valoration.getComment()%></p>
            </div>
            <% } %>
        </div>
    </div>
    <% } else { %>
    <p> Recuerde que para visualizar la receta, debe estar registrado como usuario.</p>
    <%}%>
    <%@ include file="../footer/footer.jsp" %>
</div>
 <%if (valorationCreated != null) { %>
    <script>
        <% if (valorationCreated) { %>
            alert('Su valoración se ha enviada correctamente.')
        <% } else { %>
            alert('Se ha producido un error al crear su valoración, intentelo de nuevo.')
        <% }  %>
    </script>
<% } %>

</body>
</html>
