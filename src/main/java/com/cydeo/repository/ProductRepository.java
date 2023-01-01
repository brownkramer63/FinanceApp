package com.cydeo.repository;

import com.cydeo.entity.Product;
import com.cydeo.enums.ProductUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByIsDeletedOrderByCategoryAsc(boolean deleted);




    Product findByIdAndIsDeleted(Long id, Boolean deleted);

}
