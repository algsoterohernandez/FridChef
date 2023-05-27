package com.fpdual.javaweb.enums;

import lombok.Getter;

public enum RecipeStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    DECLINED("DECLINED");
    @Getter
    private final String status;

    RecipeStatus(String status) {
        this.status = status;
    }


    // Devuelve la instancia del enum RecipeStatus correspondiente al estado proporcionado
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
