package com.cydeo.service;

import com.cydeo.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {


    ProductDTO findById(Long id);
    List<ProductDTO> listAllProducts();
    void save(ProductDTO dto);
    void update(ProductDTO dto);
    void delete(Long id);


}
