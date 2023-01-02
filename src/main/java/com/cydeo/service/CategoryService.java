package com.cydeo.service;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CategoryService{



    CategoryDTO findById(Long Id);
    void save(CategoryDTO categoryDTO);
    void update(CategoryDTO categoryDTO);
    void delete(Long id);
    List<CategoryDTO> listAllCategory();




}
