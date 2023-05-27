package com.fpdual.javaweb.web.servlet.dto;

import com.fpdual.javaweb.enums.Rol;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Data

public class UserDto {
    private int id;
    private String name, surname1, surname2, email, password;

    private boolean alreadyExists;
    private List<RolUserDto> rolUserDto;
    private boolean isAdmin;


    public boolean isAdmin() {

        if (rolUserDto != null && rolUserDto.stream().anyMatch(o -> o.getIdRol() == Rol.ADMIN.getUserRol())) {
            isAdmin = true;
        }

        return isAdmin;
    }
}