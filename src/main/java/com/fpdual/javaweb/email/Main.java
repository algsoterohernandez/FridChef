package com.fpdual.javaweb.email;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        new Sender().send("fridcheffpdual@gmail.com", "ansuncg@gmail.com", "Hola =D", "<h2>¡Desde FRIDCHEF te damos la bienvenida!<h2>"+
        "<p>Enhorabuena, el registro se ha completado con éxito. Ahora puedes INICIAR SESIÓN con tu correo electrónico y comenzar a cocinar!");
    }
}