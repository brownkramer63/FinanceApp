package com.cydeo.service.impl;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.dto.InvoiceDTO;
import com.cydeo.enums.InvoiceStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Slf4j
@AutoConfigureTestDatabase
@SqlGroup({ @Sql(value = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
class CategoryServiceImplTest {

    @Autowired
    CategoryServiceImpl categoryService;

    @Test
    void findById() {


    }

    @Test
    void save() {

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void listAllCategory() {
    }

    @Test
    void isCategoryExist() {
    }
}