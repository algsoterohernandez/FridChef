package com.fpdual.javaweb.web.servlet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Estructura de la ValorationDto
 */
@NoArgsConstructor
@Data
public class ValorationDto {
    private int id;
    private int idRecipe;
    private int idUser;
    private String nameUser;
    private String comment;
    private double valoration;
    private String createTime;


}