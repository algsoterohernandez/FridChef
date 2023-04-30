<%@ page pageEncoding="UTF-16" %>
<html>
    <body>
        <div class = "main-form">
            <h1>Registrate en Fridchef</h1>
            <form action="/FridChef/servlet-register-form" method="POST">
                <div class="form">
                    <div class="form-input">
                        <label for="name">Nombre:</label>
                        <input type="text" id= "name" name="name" minlength="2" maxlength="50" required/>
                    </div>
                    <div class="form-input">
                        <label for="surname1">Primer apellido: </label>
                        <input type="text" id= "surname1" name="surname1" minlength="2" maxlength="50" required/>
                    </div>
                    <div class="form-input">
                        <label for="surname2">Segundo apellido: </label>
                        <input type="text" id= "surname2" name="surname2" minlength="2" maxlength="50"/>
                    </div>
                    <div class="form-input">
                        <label for="email">Email: </label>
                        <input type="email" id= "email" name="email" placeholder="email@email.com" required/>
                    </div>
                    <div class="form-input">
                        <label for="password">Contraseña: </label>
                        <input type="password" id= "password" name="password" pattern="[A-Za-z0-9]{8-10}"
                               title="Introduce una contraseña de 8 a 10 letras o numeros" minlength="8"
                               maxlength="10" required/>
                    </div>
                    <div class="buttons">
                        <input type="submit" value="Enviar" formtarget="_blank">
                        <input type="reset" value="Borrar">
                    </div>
                </div>
            </form>

            <% if(request.getAttribute("success")!=null){ %>
                <div>
                    <h3>Registro realizado con exito</h3>
                    <div class="buttons">
                        <a class="button" href="/servlet-register-form"> Login </a>
                    </div>
                </div>
            <% } else if(request.getAttribute("error")!=null){ %>
                <div>
                    <h3 style="color:red"><%=request.getAttribute("error")%></h3>
                </div>
            <%}%>
        </div>
    </body>
</html>