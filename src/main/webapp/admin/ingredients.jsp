<%@ page language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.IngredientDto" %>
<%
    List<IngredientDto> ingredients = (List<IngredientDto>) request.getAttribute("IngredientList");
    Object addSucceded = request.getAttribute("add-success");
    Object errorAdd = request.getAttribute("error-add");
    Object deleteSucceded = request.getAttribute("delete-success");
    Object errorDelete = request.getAttribute("error-delete");
%>

<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>FridChef</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu|Fredoka+One|Amatic+SC">
    <link rel="stylesheet" href="css/style.css">
    <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
</head>
<body>
<div class="content">
    <%@ include file="../header/header.jsp" %>
    <% if(searchUser.isAdmin()) { %>
    <div class="principal-container">
        <div class="search">
            <div>
                <h1>Administración de ingredientes</h1>
            </div>
            <div class="add-ingredient">
                Añadir ingrediente:
                <form id="add-ingredient-form" action="/FridChef/admin-ingredients" method="POST">
                    <label for="ingredient-name">Ingrediente:</label>
                    <input id="ingredient-name" name="ingredient-name"/>
                    <input type="hidden" id="method-type-add" name="method-type" value="add"/>
                    <div class="buttons">
                        <input type="submit" value="Añadir"/>
                    </div>
                </form>
                <% if(addSucceded != null){ %>
                    <div class="message-succeded">
                        <span>Ingrediente añadido</span>
                    </div>
                <% }else if(errorAdd != null){%>
                    <div class="message-error">
                        <span><%= (String) errorAdd %></span>
                    </div>
                <% } %>
            </div>
            <div class="delete-ingredient">
                Borrar ingredientes:
                <form id="delete-ingredient-form" action="/FridChef/admin-ingredients" method="POST">
                    <input list="ingredient-list" class="input-ingredient" placeholder="Selecciona un ingrediente">
                    <input type="hidden" id="idHidden" name="ingredient-id"/>
                    <datalist id="ingredient-list">
                        <% for (IngredientDto ingredient : ingredients) { %>
                        <option value="<%= ingredient.getName() %>">
                            <input type="hidden" class="nestedInput" value="<%= ingredient.getId() %>">
                        </option>
                        <% } %>
                    </datalist>
                    <input type="hidden" id="method-type-delete" name="method-type" value="delete"/>
                    <div class="buttons">
                        <input type="submit" value="Borrar"/>
                    </div>
                    <script>
                        // Get the hidden input field and the text input field
                        var idOcultoInput = document.querySelector('#idHidden');
                        var ingredientInput = document.querySelector('.input-ingredient');

                        // Listen for the input event on the text input field
                        ingredientInput.addEventListener('input', function() {
                            // Find the selected option based on the entered text
                            var selectedOption = Array.from(document.querySelectorAll('#ingredient-list option'))
                                .find(option => option.value ===ingredientInput.value);

                            if (selectedOption) {
                                var nestedInput = selectedOption.querySelector('.nestedInput');

                                if (nestedInput) {
                                    idOcultoInput.value = nestedInput.value;
                                }
                            }
                            else {
                                    // Reset the value of the hidden input field if no option is selected
                                    idOcultoInput.value = '';
                                }
                            });
                    </script>
                </form>
                <% if(deleteSucceded != null){ %>
                <div class="message-succeded">
                    <span>Ingrediente borrado</span>
                </div>
                <% }else if(errorDelete != null){ %>
                <div class="message-error">
                    <span><%= (String) errorDelete %></span>
                </div>
                <% } %>
            </div>
        </div>
    </div>
    <% } %>
    <%@ include file="../footer/footer.jsp" %>
</div>
</body>
</html>