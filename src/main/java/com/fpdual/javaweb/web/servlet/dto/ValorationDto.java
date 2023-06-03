package com.fpdual.javaweb.web.servlet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Estructura de la Valoración Dto
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
    private Date createTime;


}