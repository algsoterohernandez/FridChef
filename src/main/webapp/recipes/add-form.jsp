<%@ page import="com.fpdual.javaweb.web.servlet.dto.CategoryDto" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: alba.lima.garcia
  Date: 5/17/2023
  Time: 12:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% ArrayList<CategoryDto> categories = (ArrayList<CategoryDto>) request.getAttribute("categories");%>

<html>
<head>
  <title>Agregar recetas FridChef</title>
</head>
<body>
<div class="content">
  <%@ include file="../header/header.jsp" %>

  <div class = "main-form">
    <h1>¡Crea tu receta ahora!</h1>
    <form action="/FridChef/addRecipe-form" method="POST">
      <input class="form">
      <div class="form-input">
        <label for="title">Titulo:</label>
        <input type="text" id= "title" name="title" minlength="2" maxlength="50" required/>
      </div>
      <div class="form-input">
        <label for="description">Descripción: </label>
        <input type="text" id= "description" name="description" minlength="2" maxlength="500" required/>
      </div>
      <div>
        <label for="time_h">duración:</label>
        <input type="number" id="time_h" name="time_h" min="0" max="24" />h
        <input type="number" id="time_m" name="time_m" min="0" max="60" />min
      </div>
      <div class="form-input">
        <label>Dificultad:</label>
        <input type="radio" id="very easy" name="difficulty" value="very easy">
        <label for="very easy">1</label>
        <input type="radio" id="easy" name="difficulty" value="easy">
        <label for="easy">2</label>
        <input type="radio" id="medium" name="difficulty" value="medium">
        <label for="very easy">3</label>
        <input type="radio" id="hard" name="difficulty" value="hard">
        <label for="easy">4</label>
        <input type="radio" id="very hard" name="difficulty" value="very hard">
        <label for="very easy">5</label>
      </div>
      <div>
        <select id="category" name="categoria">
          <% for (CategoryDto category : categories) { %>
          <option value="<%=category.getId() %>"><%= category.getName() %></option>
          <% } %>
        </select>
      </div>

      <div class="form-input">
        <label for="surname2">Segundo apellido: </label>
        <input type="text" id= "surname2" name="surname2" minlength="2" maxlength="50"/>
      </div>

  </div>
  </form>
</div>
<%@ include file="../footer/footer.jsp" %>
</div>

</body>
</html>
