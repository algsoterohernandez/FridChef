package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Estructura del Item Dto
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class IngredientRecipeDto {
    /**
     * Id en la tabla
     */
    private int id;
    /**
     * Id de la receta
     */
    private int idRecipe;
    /**
     * Id del ingrediente
     */
    private int idIngredient;
    /**
     * Nombre del ingrediente
     */
    private String nameIngredient;
    /**
     * Cantidad del ingrediente
     */
    private float quantity;
    /**
     * Unidad de medida
     */
    private String unit;
    /**
     * Alergenos que puede tener el ingrediente
     */
    private List<AllergenDto> allergens;

    /**
     * Constructor con los parametros:
     *
     * @param idIngredient id del ingrediente
     * @param quantity cantidad de producto
     * @param unit unidad de medida
     */
    public IngredientRecipeDto(String idIngredient, String quantity, String unit) {
        this.idIngredient = Integer.parseInt(idIngredient);
        this.quantity = Float.parseFloat(quantity);
        this.unit = unit;
    }

    /**
     * Creamos el getter de los alergenos para que nos devuelva la lista.
     * Si la lista en nula nos la devolverá vacía.
     * @return lista de alergenos.
     */
    public List<AllergenDto> getAllergens() {
        return this.allergens != null ? this.allergens : new ArrayList<>();
    }
}
