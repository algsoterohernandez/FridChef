package com.fpdual.javaweb.exceptions;

/**
 * Clase que representa una excepción personalizada llamada ExternalErrorException.
 * Esta excepción es utilizada para indicar errores externos en la aplicación FridChef.
 * Hereda de la clase FridChefException.
 */
public class ExternalErrorException extends FridChefException {

    /**
     * Constructor de la clase ExternalErrorException.
     * Crea una nueva instancia de ExternalErrorException con el mensaje de error especificado.
     * @param message El mensaje de error que describe la excepción.
     */
    public ExternalErrorException(String message) {
        super(message); // Llama al constructor de la clase padre (FridChefException) pasando el mensaje de error
    }
}