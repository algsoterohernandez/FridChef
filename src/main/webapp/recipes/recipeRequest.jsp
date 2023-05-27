<%@ page import="com.fpdual.javaweb.web.servlet.dto.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fpdual.javaweb.enums.RecipeStatus" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%List<RecipeDto> recipesPending = (List<RecipeDto>)request.getAttribute(RecipeStatus.PENDING.getStatus());%>

<html>
    <head>
        <meta charset="UTF-8">
        <title>FridChef</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu|Fredoka One|Amatic SC">
        <link rel="stylesheet" href="css/style.css">
        <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
    </head>
    <body>
        <div class="content">
            <div class="title">Recetas creadas por usuarios:</div>
            <div>
                <%@ include file="../header/header.jsp" %>
                <% for (RecipeDto recipe : recipesPending) { %>
                <li><a href="#" class="redirect"><%=recipe.getName()%></a>
                    <span><%=recipe.getCreateTime().substring(0, 10)%> </span>
                </li>
                <% } %>
            </div>
            <%@ include file="../footer/footer.jsp" %>
        </div>
    </body>
</html>