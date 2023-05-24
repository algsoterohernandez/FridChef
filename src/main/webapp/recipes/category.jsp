<%@ page import="com.fpdual.javaweb.web.servlet.dto.CategoryDto" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%List<CategoryDto> categoryDtoList = (List<CategoryDto>) request.getAttribute("categories");%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FridChef - Recetas</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="content">
    <div class="dropdown">
        <button class="dropbtn">Recetas</button>
        <div class="dropdown-content">
            <a href="/FridChef/recipe?id_category=1">Aperitivos</a>
            <a href="/FridChef/recipe?id_category=2">Entrantes</a>
            <a href="/FridChef/recipe?id_category=3">Cremas</a>
        </div>
    </div>
</div>

</body>
</html>


