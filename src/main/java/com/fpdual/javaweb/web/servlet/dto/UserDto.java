package com.fpdual.javaweb.web.servlet.dto;

import lombok.Data;


@Data

public class UserDto {
    private int id;
    private String name, surname1, surname2, email, password;

    private boolean alreadyExists;

    public UserDto() {

    }

}