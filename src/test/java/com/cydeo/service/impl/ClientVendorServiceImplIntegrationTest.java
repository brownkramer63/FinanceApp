package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.entity.ClientVendor;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.motherOftests.TestData;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.security.SecurityService;
import com.cydeo.service.CompanyService;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
//@AutoConfigureTestDatabase
class ClientVendorServiceImplIntegrationTest{


    @Autowired
    ClientVendorServiceImpl clientVendorService;

    @Test
    @Transactional
    void findById() {
        // Given
        //data sql is representing our mock data so use the sql data for all of these
        ClientVendorDTO clientVendorDTO = clientVendorService.findById(1L);
        log.info(clientVendorDTO.getClientVendorName());
        assertThat(clientVendorDTO).isNotNull();
        assertThat(clientVendorDTO.getClientVendorName()).isEqualTo("Orange Tech");



    }

    @Test
    @Transactional
    @WithMockUser(username = "manager@greentech.com", password = "Abc1", roles = "Manager")
    void save() {
        ClientVendorDTO testdto= TestData.clientVendorDTO;
        ClientVendorDTO createdClientVendor = clientVendorService.save(testdto);
        assertThat(createdClientVendor).isNotNull();
        assertThat(createdClientVendor.getClientVendorName()).isEqualTo("Test_ClientVendor");
        assertThat(createdClientVendor.getClientVendorType()).isEqualTo(ClientVendorType.CLIENT);


    }

    @Test
    @Transactional
    @WithMockUser(username = "manager@greentech.com", password = "Abc1", roles = "Manager")
    void delete() {
    ClientVendorDTO testdto=TestData.clientVendorDTO;
    testdto.setId(1L);
    clientVendorService.delete(testdto.getId());
    assertThat(testdto).isNotNull();


    }
    @Test
    @WithMockUser(username = "manager@greentech.com", password = "Abc1", roles = "Manager")
    @Transactional
    void update() {
       ClientVendorDTO clientVendorDTO= TestData.clientVendorDTO;
       clientVendorDTO.setId(1L);
       ClientVendorDTO updatedclientVendor= clientVendorService.update(clientVendorDTO);
       assertThat(updatedclientVendor.getId()).isEqualTo(1L);
        assertThat(updatedclientVendor.getClientVendorType()).isEqualTo(ClientVendorType.CLIENT);
    }

    @Test
    @WithMockUser(username = "manager@greentech.com", password = "Abc1", roles = "Manager")
    @Transactional
    void listAllClientVendors() {
        List<ClientVendorDTO> testobj = clientVendorService.listAllClientVendors();
        assertThat(testobj).hasSize(4);
        assertThat(testobj.get(0).getClientVendorName()).isEqualTo("Orange Tech");

    }

    @Test
    @WithMockUser(username = "manager@greentech.com", password = "Abc1", roles = "Manager")
    @Transactional
    void findAllByClientVendorName() {
       List<ClientVendor> clientVendor= clientVendorService.findAllByClientVendorName("Photobug Tech");
       assertThat(clientVendor).hasSize(1);
       assertThat(clientVendor.get(0).getClientVendorType()).isEqualTo(ClientVendorType.VENDOR);
    }

    @Test
    @WithMockUser(username = "manager@greentech.com", password = "Abc1", roles = "Manager")
    @Transactional
    void findAllClients() {
        List<ClientVendorDTO> list=clientVendorService.findAllClients();
        assertThat(list).hasSize(2);
        assertThat(list.get(0).getClientVendorName()).isEqualTo("Orange Tech");
    }

    @Test
    @WithMockUser(username = "manager@greentech.com", password = "Abc1", roles = "Manager")
    @Transactional
    void findAllVendors() {
        List<ClientVendorDTO> list=clientVendorService.findAllVendors();
        assertThat(list).hasSize(2);
        assertThat(list.get(0).getClientVendorName()).isEqualTo("Photobug Tech");
    }
}