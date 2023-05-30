package com.fpdual.javaweb.web.servlet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
/**
 * Estructura de Recepta filtro Dto
 *
 * Creamos un contructor vacío
 */
@NoArgsConstructor
/**
 * Genera los metodos: getter, setter, toString, equals, hashCode y canEqual
 */
@Data

public class RecipeFilterDto {
    /**
     * Lista de ingredientes
     */
    List<String> Ingredients;
}
