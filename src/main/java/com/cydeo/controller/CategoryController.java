package com.cydeo.controller;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.entity.Address;
import com.cydeo.entity.Category;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
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

        return "category/category-create";
    }


    @PostMapping("/create")
    public String insertCategory(@ModelAttribute("newCategory") CategoryDTO categoryDTO){
        categoryService.save(categoryDTO);

        return "redirect:/categories/list";

    }

    @GetMapping("/list")
    public String listCategory(Model model){
        model.addAttribute("categories",categoryService.listAllCategory());

        return "category/category-list";
    }


    @GetMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") Long id, Model model) {

        model.addAttribute("category", categoryService.findById(id));

        return "category/category-update";

    }

    @PostMapping("/update/{id}")
    public String updateCategory(@ModelAttribute("newCategory") CategoryDTO categoryDTO) {

        categoryService.update(categoryDTO);

        return "redirect:/categories/list";

    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable ("id") Long id){
        categoryService.delete(id);
        return "redirect:/categories/list";

    }


}







