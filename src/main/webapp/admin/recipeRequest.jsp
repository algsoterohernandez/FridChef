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
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Amatic SC">        <link rel="stylesheet" href="css/style.css">
        <link rel="shortcut icon" href="images/logo.jpg" type="image/icon">
    </head>
    <body>
        <div class="content">
            <%@ include file="../header/header.jsp" %>
            <% if(searchUser.isAdmin()){%>
                <div class="title">Recetas creadas por usuarios:</div>
                <% for (RecipeDto recipe : recipesPending) { %>
                <li><a href="/FridChef/recipes?id=<%=recipe.getId()%>" class="redirect"><%=recipe.getName()%></a>
                    <span><%=recipe.getCreateTime().substring(0, 10)%> </span>
                </li>
                <% } %>
            <% } %>
            <%@ include file="../footer/footer.jsp" %>
        </div>
    </body>
</html>