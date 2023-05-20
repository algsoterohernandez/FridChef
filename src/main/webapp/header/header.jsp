<%@ page import="com.fpdual.javaweb.web.servlet.dto.UserDto" %>
<%@ page pageEncoding="UTF-8" %>
<%
    Object userDeleted = request.getAttribute("userDeleted");
    Object userNotDeleted = request.getAttribute("userNotDeleted");
    Object userRegistered = request.getAttribute("userRegistered");
    UserDto searchUser = (UserDto) request.getSession().getAttribute("sessionUser");
%>
<div class="header">
    <div class="top-nav">
        <img src="./images/logo.png" height="15%" width="40%">
        <div class="userSession">
            <% if (searchUser!=null) { %>
            <h2><%= searchUser.getName() %></h2>
            <a href="/FridChef/unregister">Darse de baja</a>
            <% } else{ %>
            <a href="/FridChef/login">Login</a>
            <a href="/FridChef/register-form">Regístrate</a>
            <% } %>
        </div>
    </div>
    <div class="menu">
        <a href="/FridChef">Inicio</a>
        <a href="#">Recetas</a>
        <a href="#">Recetas favoritas</a>
        <a href="#">Agregar recetas</a>
    </div>
</div>

    <% if (userDeleted != null) { %>
       <script> alert("El usuario ha sido borrado con éxito"); </script>
    <% } else if(userNotDeleted != null){%>
        <script> alert("Ha ocurrido un error pruebe en otro momento."); </script>
    <%} %>

    <% if (userRegistered != null) { %>
        <script> alert("Bienvenido a fridchef"); </script>
    <% } %>

