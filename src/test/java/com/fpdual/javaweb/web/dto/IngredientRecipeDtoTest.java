package com.fpdual.javaweb.web.dto;

import com.fpdual.javaweb.enums.Unit;
import com.fpdual.javaweb.web.servlet.dto.IngredientRecipeDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class IngredientRecipeDtoTest {


    @Test
    public void GettersAndSetters(){
        IngredientRecipeDto dto = new  IngredientRecipeDto();
        dto.setAllergens(null);
        dto.setIdIngredient(1);
        dto.setUnit(Unit.MG.name());
        dto.setQuantity(3);
        dto.setNameIngredient("");
        assertTrue(dto.getAllergens().isEmpty());
        assertTrue(dto.getIdIngredient() == 1);
        assertTrue(dto.getUnit() == Unit.MG.name());
        assertTrue(dto.getQuantity() == 3);
        assertTrue(dto.getNameIngredient() == "");
    }

}
