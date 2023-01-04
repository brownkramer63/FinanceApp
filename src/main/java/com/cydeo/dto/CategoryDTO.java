package com.cydeo.dto;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    @NotBlank
    @Size(max = 100, min = 2)
    private String description;
    private CompanyDTO company;
    private boolean hasProduct;


}
