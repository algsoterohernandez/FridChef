package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Data
public class RecipeDto {
    private int id;
    private String name;
    private String description;
    private int difficulty;
    private int time;
    private String unit_time;
    private int id_category;
    private Date create_time;
    private Blob image;
    private List<IngredientDto> ingredients;
}