package com.fpdual.javaweb.web.servlet.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class RecipeFilterDto {

    List<String> Ingredients;

    public RecipeFilterDto() {
        this.Ingredients =new ArrayList<>();
    }
}
