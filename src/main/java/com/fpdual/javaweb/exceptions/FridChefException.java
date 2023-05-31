package com.fpdual.javaweb.exceptions;

/**
 * Excepción personalizada para el sistema FridChef.
 * Esta clase extiende la clase base Exception y proporciona una excepción específica para el sistema FridChef.
 */
public class FridChefException extends Exception {

    /**
     * Crea una nueva instancia de FridChefException con el mensaje de error especificado.
     *
     * @param message el mensaje de error que describe la excepción
     */
    public FridChefException(String message) {
        super(message);
    }
}

