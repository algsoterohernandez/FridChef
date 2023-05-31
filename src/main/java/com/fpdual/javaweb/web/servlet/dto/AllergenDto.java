package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

/**
 * Estructura del Alergeno Dto
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class AllergenDto {
    /**
     * Id del alergeno
     */
    private int id;
    /**
     * Nombre del alergeno
     */
    private String name;
}
