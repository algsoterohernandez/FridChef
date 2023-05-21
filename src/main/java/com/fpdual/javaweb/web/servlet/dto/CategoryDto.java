package com.fpdual.javaweb.web.servlet.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Data
public class CategoryDto {
    private int id;
    private String name;

    public CategoryDto(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
