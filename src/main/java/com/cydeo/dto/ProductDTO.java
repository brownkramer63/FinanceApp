package com.cydeo.dto;

import com.cydeo.enums.ProductUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private  Long id ;

    private int lowLimitAlert;
    private String name;
    private ProductUnit productUnit;
    private int qtyInStock;
    private CategoryDTO category;
}
