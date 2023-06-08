package com.fpdual.javaweb.web.dto;

import com.fpdual.javaweb.web.servlet.dto.RecipeDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class RecipeDtoTest {


    @Test
    public void GettersAndSetters(){
        RecipeDto dto = new  RecipeDto();
        dto.setTime(1);
        dto.setCreateTime("");
        dto.setUnitTime("");
        dto.setId(1);
        dto.setStatus("ACCEPTED");
        dto.setImageBase64("");
        dto.setIdCategory(1);
        dto.setValoration(3);
        dto.setDescription("");
        dto.setIngredients(null);

        assertNull(dto.getIngredients());
        assertTrue(dto.getId() == 1);
        assertTrue(dto.getTime() == 1);
        assertTrue(dto.getIdCategory() == 1);
        assertTrue(dto.getValoration() == 3);
        assertTrue(dto.getImageBase64().equals(""));
        assertTrue(dto.getDescription().equals(""));
        assertTrue(dto.getStatus().equals("ACCEPTED"));
        assertTrue(dto.getUnitTime().equals(""));
        assertTrue(dto.getCreateTime().equals(""));

    }

}
