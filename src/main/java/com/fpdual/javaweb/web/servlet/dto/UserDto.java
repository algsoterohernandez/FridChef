package com.fpdual.javaweb.web.servlet.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder

public class UserDto {
    private int id;
    private String name, surname1, surname2, email, password;


    public UserDto() {
    }


}