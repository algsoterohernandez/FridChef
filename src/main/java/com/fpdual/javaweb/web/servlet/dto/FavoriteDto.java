package com.fpdual.javaweb.web.servlet.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Genera los metodos: getter, setter, toString, equals, hashCode y canEqual
 */
@Data
/**
 * Para la configuración de metodos y devolución de la instacia final del objeto
 */
@Builder

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
