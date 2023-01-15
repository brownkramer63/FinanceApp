package com.cydeo.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Long id;
    @NotBlank
    @Size(max = 100, min = 2)
    private String description;
    private CompanyDTO company;
    private boolean hasProduct;


}
