package com.fpdual.javaweb.web.servlet.dto;

import com.fpdual.javaweb.enums.Rol;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Estructura del Usuario Dto
 */
@NoArgsConstructor
@Data

public class UserDto {
    /**
     * Id del usuario
     */
    private int id;
    /**
     * Nombre, apellidos, email y contraseña del usuario
     */
    private String name, surname1, surname2, email, password;
    /**
     * Indica si el usuario ya existe en nuestra base de datos
     */
    private boolean alreadyExists;
    /**
     * Lista de roles que dispone el usuario
     */
    private List<RolUserDto> rolUserDto;
    /**
     * Controlar si el usuario tiene el rol de admin
     */
    private boolean isAdmin;


    /**
     * Este metodo sirve para controlar si el usuario ya existe en nuestra base de datos
     * @return si exite o no con un booleano
     */
    public boolean isAdmin() {

        if (rolUserDto != null && rolUserDto.stream().anyMatch(o -> o.getIdRol() == Rol.ADMIN.getUserRol())) {
            isAdmin = true;
        }

        return isAdmin;
    }
}