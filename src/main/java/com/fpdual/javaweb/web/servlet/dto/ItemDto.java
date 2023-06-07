package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;
/**
 * Estructura del ItemDto
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class ItemDto {
    private String id;
    private String name;
}
