package com.cydeo.service.impl;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.entity.Category;
import com.cydeo.entity.Company;
import com.cydeo.entity.Product;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.CategoryService;
import com.cydeo.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CompanyService companyService;


    private final MapperUtil mapperUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, CompanyService companyService, MapperUtil mapperUtil) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.companyService = companyService;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public CategoryDTO findById(Long Id) {

        Category category = categoryRepository.findById(Id).get();
        return  mapperUtil.convert(category , new CategoryDTO());
    }

    @Override
    public void save(CategoryDTO categoryDTO) {

            CompanyDTO companyDTO = companyService.getCompanyByLoggedInUser();
            log.info("cat description : " + companyDTO.getId());
            categoryDTO.setCompany(companyDTO);
            log.info("cat  company : " + categoryDTO.getCompany().getId());
            Category category = mapperUtil.convert(categoryDTO , new Category());
            Company company = mapperUtil.convert(companyDTO, new Company());
            List<Category> list = categoryRepository.findAllByIsDeletedAndCompany(false,company);
            List<String> descList=list.stream().map(Category::getDescription).collect(Collectors.toList());
            // log.info(" cat description : " + descList);
            if(descList.contains(category.getDescription())){
                return;
            }else{
                categoryRepository.save(category);
            }


        }




    @Override
    public void update(CategoryDTO categoryDTO) {//this is find categoryDTO from DB

        CompanyDTO companyDTO = companyService.getCompanyByLoggedInUser();
        categoryDTO.setCompany(companyDTO);
        //Company company = mapperUtil.convert(companyDTO, new Company());

        //Category category = categoryRepository.findByIdAndIsDeletedAndCompany(categoryDTO.getId(),false,company);
        //convert DTO to entity
        Category convertedCategory = mapperUtil.convert(categoryDTO, new Category());
        convertedCategory.setId(categoryDTO.getId());
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
        CompanyDTO companyDTO = companyService.getCompanyByLoggedInUser();
        Company company = mapperUtil.convert(companyDTO, new Company());

        List<Category> listCategory= categoryRepository.findAllByIsDeletedAndCompany(false, company);
        List<CategoryDTO> categoryDTOList = listCategory.stream().map(category-> mapperUtil.convert(category, new CategoryDTO()))
                   .collect(Collectors.toList());
                for (int i = 0, j=0; i < categoryDTOList.size() ; i++ ,j++) {
                    if(!(checkIfCategoryCanBeDeleted(listCategory.get(i)))){
                        (categoryDTOList.get(i)).setHasProduct(true);
                    }

        }
                return categoryDTOList;
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
