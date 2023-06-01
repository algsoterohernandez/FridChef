package com.fpdual.javaweb.exceptions;

/**
 * Excepción personalizada que se lanza cuando se intenta crear un elemento que ya existe.
 * Esta excepción hereda de la clase FridChefException.
 */
public class AlreadyExistsException extends FridChefException {

    /**
     * Constructor de la excepción AlreadyExistsException.
     *
     * @param message el mensaje de error que describe la excepción.
     */
    public AlreadyExistsException(String message) {
        super(message);
    }
}