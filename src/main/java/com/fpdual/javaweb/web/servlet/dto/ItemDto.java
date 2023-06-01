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
    private String id;
    private String name;
}
