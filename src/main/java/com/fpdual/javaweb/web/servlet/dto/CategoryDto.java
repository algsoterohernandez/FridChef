package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

/**
 * Estructura de CategoryDto
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class CategoryDto {
    private int id;
    private String name;

}
