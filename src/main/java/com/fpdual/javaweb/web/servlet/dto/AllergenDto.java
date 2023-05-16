package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Data
public class AllergenDto {
    private int id;
    private String name;
}
