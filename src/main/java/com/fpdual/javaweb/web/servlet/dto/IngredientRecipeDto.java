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

    public IngredientRecipeDto(String idIngredient, String quantity, String unit){
        this.idIngredient = Integer.parseInt(idIngredient);
        this.quantity = Float.parseFloat(quantity);
        this.unit = unit;
    }
}
