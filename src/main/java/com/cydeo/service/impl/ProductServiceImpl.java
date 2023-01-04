package com.cydeo.service.impl;


import com.cydeo.dto.CategoryDTO;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.ProductDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.Product;
import com.cydeo.enums.ProductUnit;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
    }

    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;


    @Override
    public ProductDTO findById(Long id) {

        Product  product = productRepository.findById(id).get();
        return mapperUtil.convert(product, new ProductDTO());


    }

    @Override
    public List<ProductDTO> listAllProducts() {
        List<Product>  productList = productRepository.findAllByIsDeletedOrderByCategoryAsc(false);
        return productList.stream().map(product-> mapperUtil.convert(product, new ProductDTO()))
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
