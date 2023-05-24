<%@ page import="com.fpdual.javaweb.web.servlet.dto.CategoryDto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.IngredientRecipeDto" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.IngredientDto" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.ItemDto" %><%--
  Created by IntelliJ IDEA.
  User: alba.lima.garcia
  Date: 5/17/2023
  Time: 12:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% ArrayList<CategoryDto> categories = (ArrayList<CategoryDto>) request.getAttribute("categories");%>
<% ArrayList<IngredientDto> ingredients = (ArrayList<IngredientDto>) request.getAttribute("ingredients");%>
<% ArrayList<ItemDto> units = (ArrayList<ItemDto>) request.getAttribute("units");%>
<% Boolean recipeCreated = (Boolean) request.getAttribute("recipe_created");%>

<html>
<head>
  <meta charset="UTF-8">
  <title>Agregar recetas FridChef</title>
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu|Fredoka One|Amatic SC">
  <link rel="stylesheet" href="css/style.css">
  <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
  <script src="js/add-form.js" defer></script>
</head>
<body>
<div class="content">
  <%@ include file="../header/header.jsp" %>
  <div class = "main-form">
    <h1>¡Crea tu receta ahora!</h1>
    <form action="/FridChef/add-recipes" method="POST">
      <div class="form-input">
        <label for="title">Titulo:</label>
        <input type="text" id= "title" name="title" minlength="2" maxlength="50" required/>
      </div><br>
      <div class="form-input">
        <label for="description">Elaboración: </label><br/>
        <textarea type="textarea" id= "description" name="description" minlength="10" maxlength="500" rows="10" cols="100" placeholder="Escribe los pasos de elaboración..." required></textarea>
      </div><br>
      <div class="form-input">
        <label for="time">duración:</label>
        <input type="number" id="time" name="time" min="0"/>
        <select id="unit_time" name="unit_time">
          <option value="h">h</option>
          <option value="min">min</option>
        </select>
      </div><br>
      <div class="form-input">
        <label>Dificultad:</label><br>
        <input type="radio" id="1" name="difficulty" value="1">
        <label for="1">Muy fácil</label><br>
        <input type="radio" id="2" name="difficulty" value="2">
        <label for="2">Fácil</label><br>
        <input type="radio" id="3" name="difficulty" value="3">
        <label for="3">Normal</label><br>
        <input type="radio" id="4" name="difficulty" value="4">
        <label for="4">Dificil</label><br>
        <input type="radio" id="5" name="difficulty" value="5">
        <label for="5">Muy dificil</label>
      </div><br>
      <div>
        <select id="category" name="category">
          <% for (CategoryDto category : categories) { %>
          <option value="<%=category.getId() %>"><%= category.getName() %></option>
          <% } %>
        </select>
      </div><br><br>
      <div>
        <label>A continuación agregue los ingredientes utilizados en esta receta: </label><br>

        <select id="ingredient" name="ingredient">
          <% for (IngredientDto ingredient : ingredients) { %>
          <option value="<%=ingredient.getId() %>"><%= ingredient.getName()%></option>
          <% } %>
        </select>

        <input type="text" id="quantity" name="quantity" placeholder="Indique la cantidad aquí">

        <select id="unit" name="unit">
          <% for (ItemDto unit : units) { %>
            <option value="<%=unit.getId() %>"><%= unit.getName() %></option>
          <% } %>
        </select>
        <button id="add-ingredient">Agregar</button><br>
        <div class="ingredients-container" id="ingredients-container"></div>
      </div><br>
      <div>
        <label for="image">Agrega una imagen:</label>
        <input accept="image/png, imagen/jpeg" type="file" id="image" name="image">
      </div><br>
      <div class="buttons">
        <input type="submit" value="Enviar">
        <input type="reset" value="Borrar">
      </div>
  </form>
</div>
<%@ include file="../footer/footer.jsp" %>

<% if (recipeCreated) { %>

  <script>
    alert('Receta creada correctamente, esperando a ser revisada y aprobada ')
  </script>
<% } %>
</div>
</body>
</html>
