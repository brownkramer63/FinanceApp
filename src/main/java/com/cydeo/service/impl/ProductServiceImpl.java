package com.cydeo.service.impl;


import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.ProductDTO;
import com.cydeo.entity.Product;
import com.cydeo.enums.ProductUnit;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil, CompanyService companyService) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
    }

    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;


    @Override
    public ProductDTO findById(Long id) {

        Product  product = productRepository.findById(id).get();
        return mapperUtil.convert(product, new ProductDTO());


    }

    @Override
    public List<ProductDTO> listAllProducts() {
        CompanyDTO companyDTO=companyService.getCompanyByLoggedInUser();
        List<Product>  productList = productRepository.findAllByIsDeletedOrderByCategoryAsc(false);
        List<Product> filteredList=productList.stream()
                .filter(product -> product
                        .getCategory()
                        .getCompany().getId()==(companyDTO.getId()))
                .collect(Collectors.toList());

        //log.info(filteredList(1));
        return filteredList.stream().map(product-> mapperUtil.convert(product, new ProductDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(ProductDTO productDTO) {
        Product product = mapperUtil.convert(productDTO, new Product());
        productRepository.save(product);

    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {

        Product oldProduct = productRepository.findByIdAndIsDeleted(productDTO.getId(), false);
        Product newProduct = mapperUtil.convert(productDTO, new Product());

        newProduct.setId(oldProduct.getId());
        newProduct.setQuantityInStock(oldProduct.getQuantityInStock());

        productRepository.save(newProduct);
        return findById(productDTO.getId());
    }


    @Override
    public void delete(Long id) {
        Product product = productRepository.findByIdAndIsDeleted(id, false);
        product.setIsDeleted(true);
        productRepository.save(product);


    }
    @Override
    public List<ProductUnit> listAllEnums(){
       // List<ProductUnit> productUnits= Arrays.asList(ProductUnit.class.getEnumConstants());
        List<ProductUnit> productUnits= Arrays.asList(ProductUnit.values());
        return productUnits;
    }



}
