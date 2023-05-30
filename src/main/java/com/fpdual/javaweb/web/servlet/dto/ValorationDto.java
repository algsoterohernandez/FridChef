package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;


@NoArgsConstructor
@Data
public class ValorationDto {
    private int id;
    private int idRecipe;
    private int idUser;
    private String comment;
    private double valoration;
}
