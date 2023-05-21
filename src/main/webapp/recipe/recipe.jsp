<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.fpdual.javaweb.web.servlet.dto.RecipeDto" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Listado de Recetas</title>
</head>
<body>
<h1>Listado de Recetas</h1>

<table>
    <tr>
        <th>Nombre</th>
        <th>Descripción</th>
        <th>Ingredientes</th>
        <th>Preparación</th>
        <th>Dificultad</th>
        <th>Imagen</th>
        <th>Categoria</th>
        <th>Unidades</th>


    </tr>
    <%-- Aquí puedes agregar código Java para obtener los datos de las recetas --%>
    <%
        // Supongamos que tienes una lista de objetos Receta en tu controlador
        List<RecipeDto> listaRecetas = obtenerListaRecetas();

        for (RecipeDto receta : listaRecetas) {
    %>
    <tr>
        <td><%= receta.getName() %></td>
        <td><%= receta.getDescription() %></td>
        <td><%= receta.getId() %></td>
        <td><%= receta.getCreate_time() %></td>
        <td><%= receta.getImage() %></td>
        <td><%= receta.getDifficulty() %></td>
        <td><%= receta.getId_category() %></td>
        <td><%= receta.getUnit_time() %></td>

    </tr>
    <%
        }
    %>
</table>
<%-- Ejemplo de método para obtener la lista de recetas --%>

</body>
</html>