package com.cydeo.service.impl;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.entity.Category;
import com.cydeo.entity.Product;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    private final MapperUtil mapperUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, MapperUtil mapperUtil) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public CategoryDTO findById(Long Id) {

        Category category = categoryRepository.findById(Id).get();
        return  mapperUtil.convert(category , new CategoryDTO());
    }

    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = mapperUtil.convert(categoryDTO , new Category());
        categoryRepository.save(category);

    }

    @Override
    public void update(CategoryDTO categoryDTO) {//this is find categoryDTO from DB

        Optional<Category> category = categoryRepository.findById(categoryDTO.getId());
              //convert DTO to entity
         Category convertedCategory = mapperUtil.convert(categoryDTO, new Category());
         convertedCategory.setId(category.get().getId());
         categoryRepository.save(convertedCategory);


    }

    @Override
    public void delete(Long id) {

        Optional<Category> foundCategory=categoryRepository.findById(id);
        boolean result = checkIfCategoryCanBeDeleted(foundCategory.get());
        if(result ) {
            foundCategory.get().setIsDeleted(true);
            categoryRepository.save(foundCategory.get());
        }else{
            CategoryDTO categoryDTO=mapperUtil.convert(foundCategory.get(), new CategoryDTO());
            categoryDTO.setHasProduct(true);
        }


    }

    @Override
    public List<CategoryDTO> listAllCategory() {
            List<Category> listCategory= categoryRepository.findAllByIsDeleted(false);
             return listCategory.stream().map(category-> mapperUtil.convert(category, new CategoryDTO()))
                     .collect(Collectors.toList());

    }

    private boolean checkIfCategoryCanBeDeleted(Category category) {
        List<Product> productList = productRepository.findProductByCategory(category);
        if (productList.size() == 0) {
            return true;
        } else {
            return false;


        }


    }
}
