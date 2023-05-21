package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private String nameIngredient;
    private int idIngredient;
    private float quantity;
    private String unit;
    private List<AllergenDto> allergens;

    public IngredientRecipeDto(String idIngredient, String quantity, String unit){
        this.idIngredient = Integer.parseInt(idIngredient);
        this.quantity = Float.parseFloat(quantity);
        this.unit = unit;
    }

    public List<AllergenDto> getAllergens() {
        return this.allergens != null ? this.allergens : new ArrayList<>();
    }
}
