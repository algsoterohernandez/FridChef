package com.fpdual.javaweb.web.servlet.dto;


import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Data
public class IngredientDto {

    private int id;
    private String name;
    private List<AllergenDto> allergens;


}