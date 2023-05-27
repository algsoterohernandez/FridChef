<%@ page import="com.fpdual.javaweb.web.servlet.dto.UserDto" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.*" %>
<%@ page import="java.util.List" %>
<%@ page pageEncoding="UTF-8" %>

<%
    Object userDeleted = request.getAttribute("userDeleted");
    Object userNotDeleted = request.getAttribute("userNotDeleted");
    Object userClosedSession = request.getAttribute("userClosedSession");
    Object userRegistered = request.getAttribute("userRegistered");
    UserDto searchUser = (UserDto) request.getSession().getAttribute("sessionUser");
    List<CategoryDto> categoryList = (List<CategoryDto>) request.getAttribute("categoryList");
%>

<div class="header">
    <div class="top-header">
        <div class="logo">
            <img src="./images/logo.jpg" alt="logo de FridChef">
        </div>
        <div class="user-session">
            <% if (searchUser != null) { %>
            <a href="#" class="dropbtn"><%= searchUser.getName() %>
            </a>
            <div class="user-container">
                <li><a href="#">Recetas Favoritas</a></li>
                <%if (searchUser.isAdmin()) {%>
                <li><a href="/FridChef/recipe-request">Solicitudes</a></li>
                <% } %>
                <li><a href="/FridChef/close-session">Cerrar Sesión</a></li>
                <li><a href="/FridChef/unregister">Darse de baja</a></li>
            </div>
            <% } else { %>
            <div class="login">
                <a href="/FridChef/login">Login</a>
                <a href="/FridChef/register-form">Regístrate</a>
            </div>
            <% } %>
        </div>
    </div>
    <div class="menu">
        <a href="/FridChef">Inicio</a>
        <div class="dropdown">
            <a href="#">Recetas</a>
            <% if (categoryList != null) { %>
            <div class="dropdown-content">
                <% for (CategoryDto category : categoryList) {%>
                <a href="/FridChef/category?id_category=<%= category.getId()%>"><%= category.getName()%>
                </a>
                <% } %>
            </div>
            <% } %>
        </div>
        <a href="/FridChef/add-recipes">Agregar recetas</a>
        <a href="./contacto/contacto.jsp">Contacto</a>

    </div>
</div>

<% if (userDeleted != null) { %>
<script> alert("El usuario ha sido borrado con éxito"); </script>
<% } else if (userNotDeleted != null) {%>
<script> alert("Ha ocurrido un error pruebe en otro momento."); </script>
<%} %>

<% if (userClosedSession != null) { %>
<script> alert("¡Hasta la próxima!"); </script>
<% } %>

<% if (userRegistered != null) { %>
<script> alert("¡Bienvenido a fridchef!"); </script>
<% } %>