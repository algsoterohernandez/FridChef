package com.fpdual.javaweb.enums;

import lombok.Getter;

/**
 * Enumeración que representa el estado de una receta.
 */
public enum RecipeStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    DECLINED("DECLINED");

    /**
     * Texto de estado de la receta.
     */
    @Getter
    private final String status;

    /**
     * Constructor de RecipeStatus.
     *
     * @param status El estado de la receta
     */
    RecipeStatus(String status) {
        this.status = status;
    }


    /**
     * Devuelve la instancia del enum RecipeStatus correspondiente al estado proporcionado.
     *
     * @param status El estado de la receta en forma de cadena
     * @return La instancia de RecipeStatus correspondiente al estado proporcionado
     * @throws IllegalArgumentException si se proporciona un estado no válido
     */
    public static RecipeStatus fromString(String status) {
        if (status == null) {
            return PENDING;
        }

        for (RecipeStatus recipeStatus : RecipeStatus.values()) {
            if (recipeStatus.getStatus().equalsIgnoreCase(status)) {
                return recipeStatus;
            }
        }
        // Lanza una excepción si se proporciona un estado no válido
        throw new IllegalArgumentException("Invalid RecipeStatus: " + status);
    }
}
