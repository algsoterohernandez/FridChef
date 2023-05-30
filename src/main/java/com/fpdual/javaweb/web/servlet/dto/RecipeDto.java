package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

import java.util.List;

/**
 * Estructura del Receta Dto
 *
 *
 * Creamos un contructor vacío
 */
@NoArgsConstructor
/**
 * Creamos un contructor con todas las propiedades
 */
@AllArgsConstructor
/**
 * Genera los metodos: getter, setter, toString, equals, hashCode y canEqual
 */
@Data
/**
 * Para la configuración de metodos y devolución de la instacia final del objeto
 */
@Builder

public class RecipeDto {

    /**
     * Id de la receta
     */
    private int id;
    /**
     * Nombre de la receta
     */
    private String name;
    /**
     * Descripción de la receta
     */
    private String description;
    /**
     * Dificultad de la receta
     */
    private int difficulty;
    /**
     * Tiempo que relleva realizar la receta
     */
    private int time;
    /**
     * Unidad de tiempo
     */
    private String unitTime;
    /**
     * Id de la categoría de la receta
     */
    private int idCategory;
    /**
     * Fecha de creación de la receta
     */
    private String createTime;
    /**
     * Imagen de la receta
     */
    private String imageBase64;
    /**
     * Lista de ingredientes
     */
    private List<IngredientRecipeDto> ingredients;
    /**
     * Estado de publicación de la receta
     */
    private String status;

}