<%@ page import="com.fpdual.javaweb.web.servlet.dto.CategoryDto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.IngredientRecipeDto" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.IngredientDto" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.ItemDto" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% ArrayList<CategoryDto> categories = (ArrayList<CategoryDto>) request.getAttribute("categories");%>
<% ArrayList<IngredientDto> ingredients = (ArrayList<IngredientDto>) request.getAttribute("ingredients");%>
<% ArrayList<ItemDto> units = (ArrayList<ItemDto>) request.getAttribute("units");%>
<% Boolean recipeCreated = (Boolean) request.getAttribute("recipe_created");%>
<!DOCTYPE html>
<html>
    <head>
      <meta charset="UTF-8">
      <title>Agregar recetas FridChef</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Amatic SC|Jost">
      <link rel="stylesheet" href="css/style.css">
      <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
      <script src="https://kit.fontawesome.com/b481faf5db.js" crossorigin="anonymous"></script>
      <script src="js/add-form.js" defer></script>
      <script src="https://cdn.tiny.cloud/1/cgglq0fhrr2su9nfa8klypftb0itcslkhxnr3j4ygjs2r0s7/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>
    </head>

    <body>
        <div class="content">
            <%@ include file="../header/header.jsp" %>
            <div class="principal-container">
                <%if(searchUser != null){%>
                <div class ="main-form">
                    <h1>¡Crea tu receta ahora!</h1>
                    <form action="/FridChef/add-recipes" method="POST" enctype="multipart/form-data" onsubmit="return validateForm()">

                        <label for="title">Titulo:</label>
                        <input type="text" id= "title" name="title" minlength="2" maxlength="50" required/>
                        <br/><br/>

                        <label for="description">Elaboración: </label><br/>
                        <textarea type="textarea" id="description" name="description" minlength="10" maxlength="5000" rows="10" cols="100" placeholder="Escribe los pasos de elaboración..."></textarea>
                        <br/><br/>

                        <label for="time">duración:</label>
                        <input type="number" id="time" name="time" min="0" required/>
                        <select id="unit_time" name="unit_time" required>
                          <option value="h">h</option>
                          <option value="min">min</option>
                        </select>
                        <br/><br/>

                        <label>Dificultad:</label>
                        <div class="dificultad-form">
                          <input type="radio" id="1" name="difficulty" value="1" required>
                          <label for="1">Muy fácil</label>
                        </div>
                        <div class="dificultad-form">
                          <input type="radio" id="2" name="difficulty" value="2" required>
                          <label for="2">Fácil</label>
                        </div>
                        <div class="dificultad-form">
                            <input type="radio" id="3" name="difficulty" value="3" required>
                            <label for="3">Normal</label>
                        </div>
                        <div class="dificultad-form">
                          <input type="radio" id="4" name="difficulty" value="4" required>
                          <label for="4">Dificil</label>
                        </div>
                        <div class="dificultad-form">
                          <input type="radio" id="5" name="difficulty" value="5" required>
                          <label for="5">Muy dificil</label>
                        </div>
                        <label for="category">Categoría:</label>
                        <select id="category" name="category" required>
                        <% for (CategoryDto category : categories) { %>
                            <option value="<%=category.getId() %>"><%= category.getName() %></option>
                        <% } %>
                        </select>
                        <br/><br/>

                        <label>A continuación agregue los ingredientes utilizados en esta receta: </label>
                        <select id="ingredient" name="ingredient" required>
                        <% for (IngredientDto ingredient : ingredients) { %>
                            <option value="<%=ingredient.getId() %>"><%= ingredient.getName()%></option>
                        <% } %>
                        </select>
                        <input type="number" id="quantity" name="quantity" placeholder="Indique la cantidad aquí" required>

                        <select id="unit" name="unit" required>
                            <% for (ItemDto unit : units) { %>
                                <option value="<%=unit.getId() %>"><%= unit.getName() %></option>
                            <% } %>
                        </select>
                        <button id="add-ingredient">Agregar</button><br>
                        <div class="ingredients-container" id="ingredients-container"></div>
                        <br/>

                        <label for="image">Agrega una imagen:</label>
                        <input accept="image/png, imagen/jpeg" type="file" id="image" name="image" required/>
                        <br/>

                        <div class="buttons">
                            <input type="submit" value="Enviar">
                            <input type="reset" value="Borrar">
                        </div>
                    </form>
                </div>
                <%}else{%>
                <p>Oh vaya! Parece que aún no has entrado en tu sesión para poder crear una receta. Inicia sesión para tener acceso a todos los servicios de nuestra web!</p>
                <%}%>
            </div>
            <%@ include file="../footer/footer.jsp" %>
        </div>

        <% if (recipeCreated) { %>
        <script>
          alert('Receta creada correctamente, esperando a ser revisada y aprobada')
        </script>
        <% } %>

        <script>
            tinymce.init({
                selector: 'textarea',
                height: 300, // Altura del editor
                menubar: false, // Desactivar la barra de menú
                plugins: 'lists',
                toolbar: 'undo redo | bold italic underline | bullist numlist | align ',
                // Otras opciones de configuración que desees agregar
            });
        </script>
    </body>
</html>




