package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;
/**
 * Estructura del Item Dto
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class ItemDto {
    /**
     * Id del item
     */
    private String id;
    /**
     * Nombre del item
     */
    private String name;
}
