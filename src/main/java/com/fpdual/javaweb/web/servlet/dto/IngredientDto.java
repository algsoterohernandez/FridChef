package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

import java.util.List;

/**
 * Estructura del IngredienteDto
 *
 */
 @NoArgsConstructor
 @AllArgsConstructor
 @Data
 @Builder

public class IngredientDto {
    private int id;
    private String name;
    private List<AllergenDto> allergens;
    private boolean alreadyExists;

}