package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.entity.Company;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.motherOftests.TestData;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.UserService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
class CompanyServiceImplTest {

    @Autowired
    CompanyServiceImpl companyService;
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserServiceImpl userService;

    @Test
    @Transactional
    void findById() {
        CompanyDTO companyDTO1 = companyService.findById(1L);
        log.info("Company found : " + companyDTO1.getTitle());

        assertThat(companyDTO1.getCompanyStatus()).isEqualTo(CompanyStatus.ACTIVE);
        assertThat(companyDTO1.getTitle()).isEqualTo("CYDEO");
    }

    @Test
    @Transactional
    void listAllCompanies() {
        List<CompanyDTO> companyDTOS = companyService.listAllCompanies();
        log.info("Company size is: " + companyDTOS.size());

        assertThat(companyDTOS).hasSize(4);
    }

    @Test
    @Transactional
    void listAllCompaniesOrderByStatusAndTitle() {
        List<CompanyDTO> companyDTOS = companyService.listAllCompaniesOrderByStatusAndTitle();
        log.info("Company size is: " + companyDTOS.size());

        assertThat(companyDTOS).hasSize(3);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin@greentech.com", password = "Abc1", roles = "Admin")
    void save() {

        CompanyDTO testCompany = TestData.companyDTO;
        CompanyDTO saved = companyService.save(testCompany);

        assertThat(saved).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Test_Company");

    }


    @Test
    @Transactional
//    @ValueSource(longs = {1L, 2L, 3L, 4L})
    void activateDeactivateCompanyStatus(Long id) {

//        CompanyDTO companyDTO = TestData.companyDTO;
//        Mockito.when(companyService.findById(1L)).thenReturn(companyDTO);
        CompanyDTO companyDTO = companyService.findById(1L);
//        log.info("Company status current: " + companyDTO.getCompanyStatus());

        companyService.activateDeactivateCompanyStatus(1L);
        assertThat(companyDTO.getCompanyStatus()).isEqualTo(CompanyStatus.ACTIVE);
//        Optional<Company> company = companyRepository.findById(id);
//        log.info("Company status after: " + company.get().getCompanyStatus());

    }

    @ParameterizedTest
    @Transactional
    @ValueSource(longs = {1L, 2L, 3L, 4L})
    void update(Long id) {

//        Company company = companyRepository.findById(id).get();
//        log.info("Current object in DB: " + company.getAddress().getAddressLine1());

        CompanyDTO updatedDTO = TestData.companyDTO;
        updatedDTO.setAddress(TestData.addressDTO);
        CompanyDTO updated = companyService.update(updatedDTO, id);
//        assertThat(company.getAddress().getAddressLine1()).isEqualTo("Some address");
        assertThat(updated.getTitle()).isEqualTo("Test_Company");
//        log.info("Updated object: " + companyRepository.findById(id).get().getAddress().getAddressLine1());
    }

    @ParameterizedTest
    @Transactional
    @ValueSource(strings = {"root@cydeo.com", "admin@greentech.com", "manager@greentech.com", "employee@greentech.com"})
    void getCompaniesByLoggedInUserForRoot() {

    }

    @Test
    void getCompanyByLoggedInUser() {
    }

    @Test
    void titleAlreadyExists() {
    }
}