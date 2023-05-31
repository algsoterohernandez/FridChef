package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

import java.util.List;

/**
 * Estructura del Ingrediente Dto
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

public class IngredientDto {

    /**
     * Id del ingrediente
     */
    private int id;
    /**
     * Nombre del ingrediente
     */
    private String name;
    /**
     * Lista de alergenos relacionados al ingrediente
     */
    private List<AllergenDto> allergens;
    /**
     * Existe el ingrediente en la base de datos
     */
    private boolean alreadyExists;

}