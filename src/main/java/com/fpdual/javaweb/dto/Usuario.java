package com.fpdual.javaweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
    private int  id_usuario;
    private String nombre, apellido1, apellido2, email, contrasenya;

}
