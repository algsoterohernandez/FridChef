<%@ page import="com.fpdual.javaweb.web.servlet.dto.RecipeDto" %>
<%@ page import="com.fpdual.javaweb.enums.RecipeStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.RecipeDto" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.IngredientRecipeDto" %>
<%@ page import="com.fpdual.javaweb.enums.RecipeStatus" %>

<% CategoryDto category = (CategoryDto) request.getAttribute("category");%>
<% RecipeDto recipe = (RecipeDto) request.getAttribute("recipe");%>
<% List<ValorationDto> valorations = (List<ValorationDto>) request.getAttribute("valorations");%>
<% Boolean isFavorite = (Boolean) request.getAttribute("isFavorite");%>
<% Boolean valorationCreated = (Boolean) request.getAttribute("valorationCreated");%>

<html>
<head>
    <meta charset="UTF-8">
    <title>FridChef</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Amatic SC|Jost">
    <link rel="stylesheet" href="css/style.css">
    <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
    <script src="https://kit.fontawesome.com/b481faf5db.js" crossorigin="anonymous"></script>
    <script src="js/details-recipe.js" defer></script>

</head>
    <body>
        <div class="content">
            <%@ include file="../header/header.jsp" %>
            <% if(searchUser!= null){%>
                <div class="principal-container">
                    <div class="main-form">
                        <div class="title"><h1><%= recipe.getName() %></h1></div>
                        <%if (searchUser.isAdmin() && recipe.getStatus().equals(RecipeStatus.PENDING.name())) {%>
                            <div class="buttons">
                                <a href="/FridChef/recipes-accept?id=<%=recipe.getId()%>&status=ACCEPTED">Aceptar</a>
                                <a href="/FridChef/recipes-reject?id=<%=recipe.getId()%>&status=DECLINED">Declinar</a>
                            </div>
                        <% } %>
                        <%if (recipe.getStatus().equals(RecipeStatus.ACCEPTED.name())){%>
                            <div class="like">
                                <button id="favoriteButton" recipe="<%= recipe.getId() %>" class="favorite-button  <%= isFavorite ? "is-favorite" : "" %>">
                                    <span class="heart"></span>
                                </button>
                            </div>
                        <% } %>

                        <div class="image-position">
                            <div clas="text-description">
                                <% if(category != null) { %>
                                    <div class="category">
                                        <h2>Categoría</h2>
                                        <span><%=category.getName()%></span>
                                    </div>
                                <% } %>
                                <div class="ingredients">
                                    <h3>Ingredientes</h3>
                                    <% for (IngredientRecipeDto ingredient : recipe.getIngredients()) { %>
                                    <span><%=ingredient.getNameIngredient()%></span> ·
                                    <% } %>
                                </div>
                                <div class="difficulty"><h3>Dificultad: <%= recipe.getDifficulty() %></h3></div>
                                <div class="duration"><h3>Duración: <%=recipe.getTime()%> <%=recipe.getUnitTime()%></h3></div>
                            </div>
                            <div class="empty-image-div"></div>
                            <div class="text-image">
                                <div class="image-recipe">
                                    <% if (recipe.getImageBase64() != null) { %>
                                    <img src="data:image/jpeg;base64,<%= recipe.getImageBase64() %>">
                                    <% } else { %>
                                    <span>No hay Imagen</span>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                        <div class="description-date">
                            <div class="description">
                                <h2>Elaboración</h2>
                                <span><%= recipe.getDescription() %></span>
                            </div>
                             <div class="recipe-date">
                                <span>Fecha de creación: <%= recipe.getCreateTime().substring(0, 10) %> </span>
                            </div>
                        </div>
                        <div class="opinion">
                            <%if (recipe.getStatus().equals(RecipeStatus.ACCEPTED.name())){%>
                                <div class="average">
                                    <h3>Esta receta está valorada con <%= recipe.getValoration() %> puntos</h3>
                                </div>
                                <div class="valoration">
                                     <form action="/FridChef/details-recipe?id=<%=recipe.getId()%>" method="post">
                                         <div class="valoration-rate">
                                              <div class="valoration-title">
                                                  <h3>Valora esta receta:</h3>
                                              </div>
                                             <div class="valoration-fieldset">
                                                  <fieldset class="valoration-fieldset">
                                                     <span class="star-cb-group">
                                                         <input type="radio" id="rating-5" name="valoration" value="5" /><label for="rating-5">5</label>
                                                         <input type="radio" id="rating-4" name="valoration" value="4" /><label for="rating-4">4</label>
                                                         <input type="radio" id="rating-3" name="valoration" value="3" /><label for="rating-3">3</label>
                                                         <input type="radio" id="rating-2" name="valoration" value="2" /><label for="rating-2">2</label>
                                                         <input type="radio" id="rating-1" name="valoration" value="1" /><label for="rating-1">1</label>
                                                         <input type="radio" id="rating-0" name="valoration" value="0" class="star-cb-clear" /><label for="rating-0">0</label>
                                                     </span>
                                                 </fieldset>
                                             </div>
                                         </div>
                                         <div>
                                             <label for="comment">Comentario: </label><br/>
                                             <textarea type="textarea" id= "comment" name="comment" minlength="10" maxlength="500" rows="10" cols="100" placeholder="Escribe los pasos de elaboración..." required></textarea>
                                         </div>
                                         <div class="buttons">
                                             <input type="submit" value="Enviar">
                                         </div>
                                     </form>
                                    <script>
                                        var logID = 'log', log = $('<div id="'+logID+'"></div>');
                                        $('body').append(log);
                                        $('[type*="radio"]').change(function () {
                                            var me = $(this);
                                            log.html(me.attr('value'));
                                        });
                                    </script>
                                </div>
                                    <%if (valorationCreated != null) { %>
                                        <script>
                                            <% if (valorationCreated) { %>
                                                alert('Su valoración se ha enviada correctamente.')
                                            <% } else { %>
                                                alert('Se ha producido un error al crear su valoración, intentelo de nuevo.')
                                            <% } %>
                                        </script>
                                    <% } %>
                                <div class="comments">
                                    <div class="comments-title">
                                        <h3>Comentarios</h3>
                                    </div>
                                    <% for (ValorationDto valoration :valorations) { %>
                                    <div class="comment">
                                        <span class="title-comment">Usuario: </span>
                                        <span class="content-comment"><%= valoration.getNameUser() %></span>
                                        <span class="title-comment">Valoración: </span>
                                        <span class="content-comment"><%= valoration.getValoration() %></span>
                                        <br>
                                        <span class="title-comment"> Comentario: </span>
                                        <span class="content-comment"> <%= valoration.getComment()%></span>
                                    </div>
                                    <hr/>
                                    <% } %>
                                </div>
                            <% } %>
                        </div>
                    </div>
                </div>
            <% } else { %>
            <div class="principal-container">
                <p> Recuerde que para visualizar la receta, debe estar registrado como usuario.</p>
            </div>
            <%}%>
             <%@ include file="../footer/footer.jsp" %>
        </div>
    </body>
</html>