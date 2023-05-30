package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

/**
 * Estructura de Category Dto
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

public class CategoryDto {
    /**
     * Id de la categoría
     */
    private int id;
    /**
     * Nombre de la categoría
     */
    private String name;

}
