package com.cydeo.service;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.dto.CompanyDTO;

import java.util.List;


public interface CategoryService {


    CategoryDTO findById(Long Id);

    CategoryDTO save(CategoryDTO categoryDTO);

    CategoryDTO update(CategoryDTO categoryDTO);

    void delete(Long id);

    List<CategoryDTO> listAllCategory();

    boolean isCategoryExist(CategoryDTO categoryDTO, CompanyDTO companyDTO);


}
