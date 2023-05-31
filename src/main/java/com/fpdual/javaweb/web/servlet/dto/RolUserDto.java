package com.fpdual.javaweb.web.servlet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Estructura del Rol Dto
 */
@NoArgsConstructor
@Data

public class RolUserDto {
    /**
     * Id del usuario e Id del Rol
     */
    private int idUser, idRol;

}