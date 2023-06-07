package com.fpdual.javaweb.web.servlet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Estructura de FavoriteDto
 */
@Data

@AllArgsConstructor
@NoArgsConstructor

public class FavoriteDto {
    private int id;
    private int idRecipe;
    private int idUser;
    private String createTime;
}
