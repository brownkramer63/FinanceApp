package com.cydeo.service.impl;


import com.cydeo.dto.ProductDTO;
import com.cydeo.entity.Product;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public List<ProductDTO> listAllProducts() {
        return null;
    }

    @Override
    public void save(ProductDTO productDTO) {
        Product product = mapperUtil.convert(productDTO, new Product() );
        productRepository.save(product);


    }

    @Override
    public void update(ProductDTO dto) {

    }

    @Override
    public void delete(Long id) {

    }
}
