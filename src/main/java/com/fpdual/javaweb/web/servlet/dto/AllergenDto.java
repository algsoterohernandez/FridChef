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
    private int id;
    private String name;
}
