package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

/**
 * Estructura de la Valoración Dto
 */
@NoArgsConstructor
@Data
public class ValorationDto {
    private int id;
    private int idRecipe;
    private int idUser;
    private String comment;
    private double valoration;
}