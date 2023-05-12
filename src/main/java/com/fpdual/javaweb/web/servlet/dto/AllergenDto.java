package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class AllergenDto {
    private int id;
    private String name;
}
