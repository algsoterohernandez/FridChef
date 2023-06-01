package com.fpdual.javaweb.web.servlet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
/**
 * Estructura de Recepta filtro Dto
 */
@NoArgsConstructor
@Data

public class RecipeFilterDto {
    List<String> Ingredients;
}
