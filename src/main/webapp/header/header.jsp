<%@ page import="com.fpdual.javaweb.web.servlet.dto.UserDto" %>
<%@ page pageEncoding="UTF-8" %>
<%
    Object userDeleted = request.getAttribute("userDeleted");
    Object userNotDeleted = request.getAttribute("userNotDeleted");
    Object userClosedSession = request.getAttribute("userClosedSession");
    Object userRegistered = request.getAttribute("userRegistered");
    UserDto searchUser = (UserDto) request.getSession().getAttribute("sessionUser");
%>
<div class="header">
    <div class="top-header">
        <div class="logo">
            <img src="./images/logo.jpg" alt="logo de FridChef">
        </div>
        <div class="user-session">
            <% if (searchUser!=null) { %>
            <a href="#" class="dropbtn"><%= searchUser.getName() %></a>
            <div class="user-container">
                <li><a href="#">Recetas Favoritas</a></li>
                <li><a href="/FridChef/close-session">Cerrar Sesión</a></li>
                <li><a href="/FridChef/unregister">Darse de baja</a></li>
            </div>
            <% } else { %>
            <a href="/FridChef/login">Login</a>
            <a href="/FridChef/register-form">Regístrate</a>
            <% } %>
        </div>
    </div>
    <div class="menu">
        <a href="/FridChef">Inicio</a>
        <a href="#">Recetas</a>
        <a href="#">Recetas favoritas</a>
        <a href="/FridChef/add-recipes">Agregar recetas</a>
        <a href="/FridChef/login">Login</a>
        <a href="/FridChef/register-form">Regístrate</a>
    </div>
</div>

    <% if (userDeleted != null) { %>
       <script> alert("El usuario ha sido borrado con éxito"); </script>
    <% } else if(userNotDeleted != null){%>
        <script> alert("Ha ocurrido un error pruebe en otro momento."); </script>
    <%} %>

    <% if (userClosedSession != null) { %>
    <script> alert("¡Hasta la próxima!"); </script>
    <% } %>

    <% if (userRegistered != null) { %>
        <script> alert("¡Bienvenido a fridchef!"); </script>
    <% } %>