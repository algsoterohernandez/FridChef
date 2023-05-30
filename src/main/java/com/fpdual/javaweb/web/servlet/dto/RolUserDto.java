package com.fpdual.javaweb.web.servlet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Estructura del Rol Dto
 *
 * Creamos un contructor vacío
 */
@NoArgsConstructor
/**
 * Genera los metodos: getter, setter, toString, equals, hashCode y canEqual
 */
@Data

public class RolUserDto {
    /**
     * Id del usuario e Id del Rol
     */
    private int idUser, idRol;

}