package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

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
    private String unitTime;
    private int idCategory;
    private String createTime;
    private String imageBase64;
    private List<IngredientRecipeDto> ingredients;
    private String status;

}