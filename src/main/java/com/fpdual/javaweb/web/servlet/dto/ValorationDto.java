package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

/**
 * Estructura de la Valoración Dto
 */
@NoArgsConstructor
@Data
public class ValorationDto {
    /**
     * Id de la valoración
     */
    private int id;
    /**
     * Id de la receta
     */
    private int idRecipe;
    /**
     * Id del usuario
     */
    private int idUser;

    /**
     * Texto del comentario
     */
    private String comment;
    /**
     * Puntuación de la receta
     */
    private double valoration;
}
