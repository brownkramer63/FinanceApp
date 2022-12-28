package com.cydeo.converter;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.service.CategoryService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoConverter implements Converter<String, CategoryDTO> {

    private final CategoryService categoryService;

    public CategoryDtoConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Override
    public CategoryDTO convert(String source) {
        if (source == null || source.equals("")) {
            return null;
        }

        return categoryService.findById(Long.parseLong(source));

    }
}
