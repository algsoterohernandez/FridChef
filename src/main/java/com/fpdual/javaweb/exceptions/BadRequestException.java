package com.fpdual.javaweb.exceptions;

/**
 * Clase que representa una excepción personalizada llamada BadRequestException.
 * Esta excepción es utilizada para indicar errores externos en la aplicación FridChef.
 * Hereda de la clase FridChefException.
 */
public class BadRequestException extends FridChefException {

    /**
     * Constructor de la clase ExternalErrorException.
     * Crea una nueva instancia de ExternalErrorException con el mensaje de error especificado.
     * @param message El mensaje de error que describe la excepción.
     */
    public BadRequestException(String message) {
        super(message);
    }
}