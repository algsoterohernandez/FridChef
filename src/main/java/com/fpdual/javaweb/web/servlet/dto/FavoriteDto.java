package com.fpdual.javaweb.web.servlet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Genera los metodos: getter, setter, toString, equals, hashCode y canEqual
 */
@Data

@AllArgsConstructor
@NoArgsConstructor

public class FavoriteDto {
    private int id;
    private int idRecipe;
    private int idUser;
    private String createTime;

    public FavoriteDto(int idRecipe, int idUser) {
        this.idRecipe = idRecipe;
        this.idUser = idUser;
    }
}
