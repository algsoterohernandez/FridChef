package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;
import java.util.List;
/**
 * Estructura de Recepta filtro Dto
 */
@NoArgsConstructor
@Data

public class RecipeFilterDto {
    List<String> Ingredients;
}
