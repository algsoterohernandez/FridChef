package com.fpdual.javaweb.enums;


import lombok.Getter;

public enum Rol {
    ADMIN(1),
    STANDARD (2);


    @Getter
    private final int userRol;

    Rol(int userRol) {
        this.userRol = userRol;
    }
}