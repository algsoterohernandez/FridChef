package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;
import java.util.List;
/**
 * Estructura de RecepipeFilterDto
 */
@NoArgsConstructor
@Data

public class RecipeFilterDto {
    List<String> Ingredients;
}
