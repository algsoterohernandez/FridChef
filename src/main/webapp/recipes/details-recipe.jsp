<%@ page import="com.fpdual.javaweb.web.servlet.dto.RecipeDto" %>
<%@ page import="com.fpdual.javaweb.enums.RecipeStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% RecipeDto recipe = (RecipeDto) request.getAttribute("recipe");%>
<% Boolean user = (Boolean) request.getAttribute("user");%>
<html>
<head>
    <meta charset="UTF-8">
    <title>FridChef</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu|Fredoka One|Amatic SC">
    <link rel="stylesheet" href="css/style.css">
    <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
    <script src="js/buscador.js" defer></script>
    <script src="js/filter.js" defer></script>
</head>
<body>
<div class="content">
    <%@ include file="../header/header.jsp" %>
    <div class="title"><%= recipe.getName() %>
        <%if (searchUser!= null && searchUser.isAdmin() && recipe.getStatus().equals(RecipeStatus.PENDING.name())) {%>
        <div class="buttons">
            <a href="/FridChef/recipes-accept?id=<%=recipe.getId()%>&status=ACCEPTED">Aceptar</a>
            <a href="/FridChef/recipes-reject?id=<%=recipe.getId()%>&status=DECLINED">Declinar</a>
        </div>
        <% } %>
    </div>

<div class="content">
    <%@ include file="../header/header.jsp" %>
    <% if (user) { %>
    <div class="header">
        <h1><%=recipe.getName()%></h1>
    </div>
    <div class="recipe-details">
        <h2><%=recipe.getName()%></h2>
            <div>
                <h3>Esta receta está valorada con XXXX puntos</h3>
            </div>
        <div>
            <p><%=recipe.getDescription()%></p>
            <p>duración:<%=recipe.getTime()%> <%=recipe.getUnitTime()%></p>
            <p>Fecha de creación: XX/XX/XXXX</p>
        </div>
    </div>
    <div>
        <h3>Valora esta receta:</h3>
        <form>
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
                <label for="description">Comentario: </label><br/>
                <textarea type="textarea" id= "description" name="description" minlength="10" maxlength="500" rows="10" cols="100" placeholder="Escribe los pasos de elaboración..." required></textarea>
            </div>
            <div class="buttons">
                <input type="submit" value="Enviar">
            </div>
        </form>

    </div>
    <% } else { %>
    <p>Oh vaya!</p>
    <%}%>
    </div>
</div>
<%@ include file="../footer/footer.jsp" %>
</body>
</html>
