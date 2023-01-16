package com.cydeo.service.impl.integration;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.ProductDTO;
import com.cydeo.motherOftests.TestData;
import com.cydeo.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ProductServiceImplTest extends TestData {

    @Autowired
    ProductServiceImpl productService;


    @Test
    @DisplayName("Given valid id expect valid productDto ")
    @Transactional
    void given_validId_expect_valid_productDto() {
        ProductDTO byId = productService.findById(1L);
        log.info("product found: " + byId.getName());
        assertThat(byId.getName()).isEqualTo("HP Elite 800G1 Desktop Computer Package");
    }

    @Test
    @Transactional
    @WithMockUser(username = "manager@greentech.com", password = "Abc1", roles = "Manager")
    void listAllProducts() {

        List<ProductDTO> productDTOS = productService.listAllProducts();
        assertThat(productDTOS.size()).isEqualTo(4);


    }

    @Test
    @Transactional
    @WithMockUser(username = "manager@greentech.com", password = "Abc1", roles = "Manager")
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void listAllEnums() {
    }
}