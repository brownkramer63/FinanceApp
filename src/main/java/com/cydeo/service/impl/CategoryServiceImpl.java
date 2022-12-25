package com.cydeo.service.impl;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.entity.Category;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.service.CategoryService;

//todo annotation @Kadifa
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final MapperUtil mapperUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public CategoryDTO findById(Long Id) {

        Category category = categoryRepository.findById(Id).get();
        return  mapperUtil.convert(category , new CategoryDTO());
    }
}
