<%@ page pageEncoding="UTF-8" %>
<%
    Object userDeleted = request.getAttribute("userDeleted");
    Object userRegistered = request.getAttribute("userRegistered");
%>
<div class="header">

    <div class="menu">
        <a href="/FridChef">Inicio</a>
        <a href="#">Recetas</a>
        <a href="#">Recetas favoritas</a>
        <a href="#">Agregar recetas</a>
        <a href="/FridChef/login">Login</a>
        <a href="/FridChef/register-form">Regístrate</a>
    </div>

    <% if (userDeleted != null) { %>
       <script> alert("El usuario ha sido borrado con éxito"); </script>
    <% } %>

    <% if (userRegistered != null) { %>
        <script> alert("Bienvenido a fridchef"); </script>
    <% } %>


</div>