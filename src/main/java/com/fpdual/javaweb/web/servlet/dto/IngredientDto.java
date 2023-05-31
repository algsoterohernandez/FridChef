package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

import java.util.List;

/**
 * Estructura del Ingrediente Dto
 *
 */
 @NoArgsConstructor
 @AllArgsConstructor
 @Data
 @Builder

public class IngredientDto {

    /**
     * Id del ingrediente
     */
    private int id;
    /**
     * Nombre del ingrediente
     */
    private String name;
    /**
     * Lista de alergenos relacionados al ingrediente
     */
    private List<AllergenDto> allergens;
    /**
     * Existe el ingrediente en la base de datos
     */
    private boolean alreadyExists;

}