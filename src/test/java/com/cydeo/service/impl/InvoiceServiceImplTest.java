package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.enums.InvoiceStatus;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql(value = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
class InvoiceServiceImplTest {

    @Autowired
    InvoiceServiceImpl invoiceService;


    @Test
    @Transactional
    void findById() {
        InvoiceDTO byId = invoiceService.findById(1L);
        log.info("Invoice founded : " + byId.getDate());

        assertThat(byId.getInvoiceNo()).isEqualTo("P-001");
        assertThat(byId.getInvoiceStatus()).isEqualTo(InvoiceStatus.APPROVED);
    }

    @Test
    void listAllInvoicesByType() {
    }

    @Test
    void totalTaxOfInvoice() {
    }

    @Test
    void totalPriceOfInvoice() {
    }

    @Test
    void calculateProfitLoss() {
    }

    @Test
    void listAllInvoice() {
    }

    @Test
    void approve() {
    }

    @Test
    void getNewPurchaseInvoice() {
    }

    @Test
    void getNewSalesInvoice() {
    }

    @Test
    void save() {
    }

    @Test
    void create() {
    }

    @Test
    void existsById() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteByInvoiceId() {
    }

    @Test
    void updateQuantityInStock() {
    }

    @Test
    void updateQuantityAfterApproval() {
    }

    @Test
    void checkIfStockIsEnough() {
    }

    @Test
    void findByInvoiceId() {
    }

    @Test
    void totalCostOfApprovedInvoices() {
    }

    @Test
    void totalSalesOfApprovedInvoices() {
    }

    @Test
    void listAllApprovedInvoices() {
    }
}