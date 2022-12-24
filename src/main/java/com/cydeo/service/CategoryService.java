package com.cydeo.service;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryService{



    CategoryDTO findById(Long Id);



}
