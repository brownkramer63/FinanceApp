package com.cydeo.controller;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.entity.Category;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryController {

    public CategoryController(CategoryService categoryService, MapperUtil mapperUtil) {
        this.categoryService = categoryService;
        this.mapperUtil = mapperUtil;
    }

    private final CategoryService categoryService;
    private final MapperUtil mapperUtil;





    @GetMapping("/create")
    public String createCategory(Model model){

        model.addAttribute("newCategory", new CategoryDTO());

        return "/category/category-create";
    }

    @PostMapping("/create")
    public String insertCategory(@ModelAttribute("newCategory") CategoryDTO categoryDTO,  Model model){

        categoryService.save(categoryDTO);

        model.addAttribute("category",categoryService.listAllCategory());



        return "/category/category-list";
    }







}
