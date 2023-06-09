package com.fpdual.javaweb.web.servlet.dto;

import com.fpdual.javaweb.enums.Rol;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Estructura del UsuarioDto
 */
@NoArgsConstructor
@Data

public class UserDto {
    private int id;
    private String name, surname1, surname2, email, password;
    private boolean alreadyExists;
    private List<RolUserDto> rolUserDto;
    private boolean isAdmin;

    private List<Integer>favoriteList;


    /**
     * Este metodo sirve para controlar si el usuario ya existe en nuestra base de datos
     * @return si existe o no con un booleano
     */
    public boolean isAdmin() {

        if (rolUserDto != null && rolUserDto.stream().anyMatch(o -> o.getIdRol() == Rol.ADMIN.getUserRol())) {
            isAdmin = true;
        }

        return isAdmin;
    }

    public boolean isFavorite(int idRecipe) {
        return favoriteList != null && favoriteList.indexOf(idRecipe) > -1;
    }
}