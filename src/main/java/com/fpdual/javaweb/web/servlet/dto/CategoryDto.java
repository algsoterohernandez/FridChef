package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

/**
 * Estructura de Category Dto
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class CategoryDto {
    /**
     * Id de la categoría
     */
    private int id;
    /**
     * Nombre de la categoría
     */
    private String name;

}
