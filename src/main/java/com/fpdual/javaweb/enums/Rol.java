package com.fpdual.javaweb.enums;


import lombok.Getter;

/**
 * Enumeración que representa los roles de usuario en el sistema.
 */
public enum Rol {

    ADMIN(1),
    STANDARD(2);

    /**
     * Valor del rol de usuario.
     */
    @Getter
    private final int userRol;

    /**
     * Constructor de la enumeración Rol.
     *
     * @param userRol Valor del rol de usuario.
     */
    Rol(int userRol) {
        this.userRol = userRol;
    }
}