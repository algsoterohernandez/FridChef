package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Data
public class IngredientRecipeDto {
    private int id;
    private int idRecipe;
    private int idIngredient;
    private float quantity;
    private String unit;
}
