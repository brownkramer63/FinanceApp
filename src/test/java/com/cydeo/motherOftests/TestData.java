package com.cydeo.motherOftests;

import com.cydeo.dto.*;
import com.cydeo.enums.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

@AutoConfigureTestDatabase
@SqlGroup({
        @Sql(value = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestData {


    public static UserDTO userDTO;
    public static CompanyDTO companyDTO;
    public static RoleDTO roleDTO;
    public static CategoryDTO categoryDTO;
    public static ClientVendorDTO clientVendorDTO;
    public static ProductDTO productDTO;
    public static InvoiceProductDTO invoiceProductDTO;
    public static InvoiceDTO invoiceDTO;


    static {
        //todo add other roles if need
        roleDTO = RoleDTO.builder()
                .description("Manager").build();

        userDTO = UserDTO.builder()
                .id(1L)
                .firstname("John")
                .lastname("Mike")
                .phone("+1 (111) 111-1111")
                .password("Abc1")
                .confirmPassword("Abc1")
                .role(new RoleDTO(1L, "Manager"))
                .isOnlyAdmin(false)
                .company(companyDTO)
                .build();

        companyDTO = CompanyDTO.builder()
                .title("Test_Company")
                .website("www.test.com")
                .id(1L)
                .phone("+1 (111) 111-1111")
                .companyStatus(CompanyStatus.ACTIVE)
                .address(new AddressDTO())
                .build();

        categoryDTO = CategoryDTO.builder()
                .company(companyDTO)
                .description("Test_Category")
                .build();

        productDTO = ProductDTO.builder()
                .category(categoryDTO)
                .productUnit(ProductUnit.PCS)
                .name("Test_Product")
                .quantityInStock(10)
                .lowLimitAlert(5)
                .build();

        clientVendorDTO = ClientVendorDTO.builder()
                .clientVendorType(ClientVendorType.CLIENT)
                .clientVendorName("Test_ClientVendor")
                .address(new AddressDTO())
                .website("https://www.test.com")
                .phone("+1 (111) 111-1111")
                .build();
        invoiceProductDTO = InvoiceProductDTO.builder()
                .product(productDTO)
                .price(BigDecimal.TEN)
                .tax(10)
                .quantity(10)
                .invoice(invoiceDTO)
                .build();

        invoiceDTO = InvoiceDTO.builder()
                .invoiceNo("T-001")
                .clientVendor(clientVendorDTO)
                .invoiceStatus(InvoiceStatus.APPROVED)
                .invoiceType(InvoiceType.SALES)
                .date(LocalDate.of(2022, 01, 01))
                .companyDTO(companyDTO)
                .invoiceProducts(Collections.emptyList())
                .price(BigDecimal.valueOf(1000))
                .tax(10)
                .total(BigDecimal.TEN.multiply(BigDecimal.valueOf(1000)))
                .build();


    }


}

