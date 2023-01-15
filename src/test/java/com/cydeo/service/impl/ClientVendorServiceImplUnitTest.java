package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.entity.ClientVendor;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.security.SecurityService;
import com.cydeo.security.SecurityServiceImpl;
import com.cydeo.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientVendorServiceImplUnitTest {

    @InjectMocks
    ClientVendorServiceImpl clientVendorService;

    @Mock
    ClientVendorRepository clientVendorRepository;

    @Mock
    MapperUtil mapperUtil;

     @Mock
     SecurityService securityService;

     @Mock
    InvoiceRepository invoiceRepository;

     @Mock
    CompanyService companyService;


    @Test
    @DisplayName("find clientVendor by id test")
    void findClientVendorByIdTest() {
        //stubbing
     //Given both parts of method in serviceimpl we are trying to test
        when(clientVendorRepository.findById(anyLong())).thenReturn(Optional.of(new ClientVendor()));
       when(mapperUtil.convert(any(ClientVendor.class),any(ClientVendorDTO.class))).thenReturn(new ClientVendorDTO());

     //when   we are running the method we want to test with the parameters it takes
        ClientVendorDTO clientVendorDTO= clientVendorService.findById(anyLong());

        //then
        //since we have two parts to this method make sure we run them in order
        InOrder inOrder = inOrder(clientVendorRepository, mapperUtil);
        inOrder.verify(clientVendorRepository).findById(anyLong());
        inOrder.verify(mapperUtil).convert(any(ClientVendor.class),any(ClientVendorDTO.class));

        //then we have our assertions
        assertNotNull(clientVendorDTO);

    }

    @Test
    @DisplayName("save clientVendor")
    void saveClientVendorTest() {
    }

    @Test
    @DisplayName("delete clientVendor")
    void deleteClientVendorTest() {
    }

    @Test
    @DisplayName("update clientVendor")
    void updateClientVendorTest() {
    }

    @Test
    @DisplayName("list all clientVendors")
    void listAllClientVendorsTest() {
    }

    @Test
    @DisplayName("find all by clientVendor names")
    void findAllByClientVendorNameTest() {
    }

    @Test
    @DisplayName("find all clients")
    void findAllClientsTest() {
    }

    @Test
    @DisplayName("find all vendors")
    void findAllVendorsTest() {
    }
}