package com.cydeo.service.impl;

import com.cydeo.dto.*;
import com.cydeo.entity.Address;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.enums.CompanyStatus;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        //given
        ClientVendorDTO clientVendorDTO = new ClientVendorDTO(1L,"Test Company","1-847-707-1126","https://my.cydeo.com/apps",ClientVendorType.VENDOR,new AddressDTO(),null); //may need to add companynot null to both lines here
        ClientVendor clientVendor= new ClientVendor("Test Company","1-847-707-1126","https://my.cydeo.com/apps",ClientVendorType.VENDOR,new Address(),null);
        clientVendor.setId(1L);
        //need logged in user as well
        UserDTO userloggedin= new UserDTO(1L,"user@gmail.com","Abc1","Abc1","User","User","1-847-707-1126",new RoleDTO(),new CompanyDTO(),false);
        //we will need a company for save method
        Company company = new Company("Name","1-847-707-1126","https://my.cydeo.com/apps", CompanyStatus.ACTIVE,new Address());
        CompanyDTO companyDTO= new CompanyDTO(1L,"Company","1-847-707-1126","https://my.cydeo.com/apps",new AddressDTO(),CompanyStatus.ACTIVE);
        clientVendor.setCompany(company);
        clientVendorDTO.setCompany(companyDTO);

        when(companyService.getCompanyByLoggedInUser()).thenReturn(companyDTO);
        when(mapperUtil.convert(any(ClientVendorDTO.class),any(ClientVendor.class))).thenReturn(clientVendor);
        when(securityService.getLoggedInUser()).thenReturn(userloggedin);
          when(mapperUtil.convert(any(CompanyDTO.class),any(Company.class))).thenReturn(company);

        //the when action part
        clientVendorService.save(clientVendorDTO);

        //then in order
        InOrder inOrder= inOrder(companyService, mapperUtil, securityService, clientVendorRepository);
        inOrder.verify(companyService.getCompanyByLoggedInUser());
        inOrder.verify(mapperUtil.convert(any(ClientVendorDTO.class),any(ClientVendor.class)));
        inOrder.verify(securityService.getLoggedInUser());
        inOrder.verify(mapperUtil.convert(any(ClientVendor.class),any(ClientVendorDTO.class)));

        //Assertions
        assertEquals(clientVendor.getCompany(),company);
        assertEquals(clientVendor.getClientVendorName(),"Test Company");

    }

    @Test
    @DisplayName("delete clientVendor")
    void deleteClientVendorTest() throws IllegalAccessException {
        //given
       ClientVendor clientVendor= new ClientVendor();
       clientVendor.setIsDeleted(false);
       when(clientVendorRepository.findById(anyLong())).thenReturn(Optional.of(clientVendor)
       );
       //when
        clientVendorService.delete(anyLong());

        //then
        InOrder inOrder= inOrder(clientVendorRepository);

        inOrder.verify(clientVendorRepository).findById(anyLong());

        //assertions
        Assertions.assertTrue(clientVendor.getIsDeleted());
    }

    @Test
    @DisplayName("update clientVendor")
    void updateClientVendorTest() {
//        Optional<ClientVendor> clientVendor = clientVendorRepository.findById(clientVendorDTO.getId());
//        ClientVendor updatedClientVendor = mapperUtil.convert(clientVendorDTO, new ClientVendor()); //convert what we got into new client vendor
//        if (clientVendor.isPresent()) { //build whole new clientvendor
//            updatedClientVendor.setId(clientVendor.get().getId());
//            updatedClientVendor.setCompany(mapperUtil.convert(securityService.getLoggedInUser().getCompany(),new Company()));
//            updatedClientVendor.setWebsite(clientVendorDTO.getWebsite());
//            updatedClientVendor.setClientVendorType(clientVendorDTO.getClientVendorType());
//            updatedClientVendor.setAddress(mapperUtil.convert(clientVendorDTO.getAddress(), new Address()));
//            clientVendorRepository.save(updatedClientVendor);
//        return mapperUtil.convert(updatedClientVendor, new ClientVendorDTO());
        //given
        ClientVendor clientVendor= new ClientVendor("Test Company","1-847-707-1126","https://my.cydeo.com/apps",ClientVendorType.VENDOR,new Address(),null);
        clientVendor.setId(1L);
        when(clientVendorRepository.findById(anyLong())).thenReturn(Optional.of(new ClientVendor()));
        when(securityService.getLoggedInUser()).thenReturn(new UserDTO(1L,"user@gmail.com","Abc1","Abc1","User","User","1-847-707-1126",new RoleDTO(),new CompanyDTO(),false));
        //then add company
        when(mapperUtil.convert(any(ClientVendorDTO.class),any(ClientVendor.class)));
        when(clientVendorRepository.save(any(ClientVendor.class)));
        //when
        ClientVendorDTO clientVendorDTO = new ClientVendorDTO(1L,"Test Company","1-847-707-1126","https://my.cydeo.com/apps",ClientVendorType.VENDOR,new AddressDTO(),null); //may need to add companynot null to both lines here
        CompanyDTO companyDTO= new CompanyDTO(1L,"Company","1-847-707-1126","https://my.cydeo.com/apps",new AddressDTO(),CompanyStatus.ACTIVE);
        clientVendorDTO.setCompany(companyDTO);
        clientVendorService.save(clientVendorDTO);
        //then
        InOrder inOrder= inOrder(clientVendorRepository,securityService,mapperUtil);
        inOrder.verify(clientVendorRepository.findById(anyLong()));
        inOrder.verify(securityService.getLoggedInUser());
        inOrder.verify(mapperUtil.convert(any(ClientVendorDTO.class),any(ClientVendor.class)));
        inOrder.verify(clientVendorRepository).save(clientVendor);


    }

    @Test
    @DisplayName("list all clientVendors")
    void listAllClientVendorsTest() {
        ClientVendorDTO clientVendorDTO = new ClientVendorDTO(1L,"Test Company","1-847-707-1126","https://my.cydeo.com/apps",ClientVendorType.VENDOR,new AddressDTO(),new CompanyDTO(1L,"Company","1-847-707-1126","https://my.cydeo.com/apps",new AddressDTO(),CompanyStatus.ACTIVE)); //may need to add companynot null to both lines here
        ClientVendorDTO clientVendorDTO2 = new ClientVendorDTO(1L,"Test Company","1-847-707-1126","https://my.cydeo.com/apps",ClientVendorType.VENDOR,new AddressDTO(),new CompanyDTO(1L,"Company","1-847-707-1126","https://my.cydeo.com/apps",new AddressDTO(),CompanyStatus.ACTIVE)); //may need to add companynot null to both lines here
        ClientVendorDTO clientVendorDTO3 = new ClientVendorDTO(1L,"Test Company","1-847-707-1126","https://my.cydeo.com/apps",ClientVendorType.VENDOR,new AddressDTO(),new CompanyDTO(1L,"Company","1-847-707-1126","https://my.cydeo.com/apps",new AddressDTO(),CompanyStatus.ACTIVE)); //may need to add companynot null to both lines here

        when(clientVendorService.listAllClientVendors()).thenReturn(Arrays.asList(clientVendorDTO,clientVendorDTO2,clientVendorDTO3));

        when(securityService.getLoggedInUser()).thenReturn(new UserDTO(1L,"user@gmail.com","Abc1","Abc1","User","User","1-847-707-1126",new RoleDTO(),new CompanyDTO(),false));

//        when(mapperUtil.convert(an)) dont think i need this

        //then
        InOrder inOrder= inOrder(clientVendorService,securityService);

        inOrder.verify(clientVendorService).listAllClientVendors();
        inOrder.verify(securityService).getLoggedInUser();
    }

    @Test
    @DisplayName("find all by clientVendor names")
    void findAllByClientVendorNameTest() {
//        public List<ClientVendor> findAllByClientVendorName(String name) {
//            return clientVendorRepository.findAll().stream().filter(clientVendor -> clientVendor.getClientVendorName().equals(name)).collect(Collectors.toList());
        ClientVendor clientVendor= new ClientVendor("Test Company","1-847-707-1126","https://my.cydeo.com/apps",ClientVendorType.VENDOR,new Address(),null);
        clientVendor.setId(1L);
        ClientVendor clientVendor2= new ClientVendor("Test Company","1-847-707-1126","https://my.cydeo.com/apps",ClientVendorType.VENDOR,new Address(),null);
        clientVendor2.setId(2L);
        when(clientVendorService.findAllByClientVendorName(anyString())).thenReturn(Arrays.asList(clientVendor,clientVendor2));

        //add assertion
    }

    @Test
    @DisplayName("find all clients")
    void findAllClientsTest() {
        ClientVendorDTO clientVendorDTO2 = new ClientVendorDTO(1L,"Test Company","1-847-707-1126","https://my.cydeo.com/apps",ClientVendorType.CLIENT,new AddressDTO(),new CompanyDTO(1L,"Company","1-847-707-1126","https://my.cydeo.com/apps",new AddressDTO(),CompanyStatus.ACTIVE)); //may need to add companynot null to both lines here
        ClientVendor clientVendor= new ClientVendor("Test Company","1-847-707-1126","https://my.cydeo.com/apps",ClientVendorType.VENDOR,new Address(),new Company("Name","1-847-707-1126","https://my.cydeo.com/apps", CompanyStatus.ACTIVE,new Address()));
        ClientVendor clientVendor2= new ClientVendor("Test Company","1-847-707-1126","https://my.cydeo.com/apps",ClientVendorType.VENDOR,new Address(),new Company("Name","1-847-707-1126","https://my.cydeo.com/apps", CompanyStatus.ACTIVE,new Address()));

        clientVendor.setId(1L);
        clientVendor2.setId(2L);
        when(clientVendorRepository.findAll()).thenReturn(Arrays.asList(clientVendor,clientVendor2));

        List<ClientVendorDTO> list=clientVendorService.findAllClients();
        InOrder inOrder= inOrder(clientVendorRepository);

        inOrder.verify(clientVendorRepository).findAll();

//        assertion which to use here
    }

    @Test
    @DisplayName("find all vendors")
    void findAllVendorsTest() {
        ClientVendorDTO clientVendorDTO = new ClientVendorDTO(1L,"Test Company","1-847-707-1126","https://my.cydeo.com/apps",ClientVendorType.VENDOR,new AddressDTO(),new CompanyDTO(1L,"Company","1-847-707-1126","https://my.cydeo.com/apps",new AddressDTO(),CompanyStatus.ACTIVE)); //may need to add companynot null to both lines here
        ClientVendorDTO clientVendorDTO2 = new ClientVendorDTO(1L,"Test Company","1-847-707-1126","https://my.cydeo.com/apps",ClientVendorType.VENDOR,new AddressDTO(),new CompanyDTO(1L,"Company","1-847-707-1126","https://my.cydeo.com/apps",new AddressDTO(),CompanyStatus.ACTIVE)); //may need to add companynot null to both lines here

        when(clientVendorService.findAllVendors()).thenReturn(Arrays.asList(clientVendorDTO,clientVendorDTO2));

        List<ClientVendorDTO> list=clientVendorService.findAllVendors();
        InOrder inOrder= inOrder(clientVendorService);

        inOrder.verify(clientVendorService).findAllVendors();

        assertEquals(list.size(),1);

    }

//    @Test
//    @DisplayName("save clientVendor")
//    void saveClientVendorTest2() {
//        // given
//        ClientVendorDTO clientVendorDTO = new ClientVendorDTO(1L,"Test Company","1-847-707-1126","https://my.cydeo.com/apps",ClientVendorType.VENDOR,new AddressDTO(),null);
//        ClientVendor clientVendor = new ClientVendor("Test Company","1-847-707-1126","https://my.cydeo.com/apps",ClientVendorType.VENDOR,new Address(),null);
//        clientVendor.setId(1L);
//        UserDTO userloggedin= new UserDTO(1L,"user@gmail.com","Abc1","Abc1","User","User","1-847-707-1126",new RoleDTO(),new CompanyDTO(),false);
//        when(securityService.getLoggedInUser()).thenReturn(userloggedin);
//        when(mapperUtil.convert(any(ClientVendorDTO.class),any(ClientVendor.class))).thenReturn(clientVendor);
//        when(clientVendorRepository.save(clientVendor)).thenReturn(clientVendor);
//        when(mapperUtil.convert(any(ClientVendor.class),any(ClientVendorDTO.class))).thenReturn(clientVendorDTO);
//
//        // when
//        ClientVendorDTO savedClientVendorDTO = clientVendorService.save(clientVendorDTO);
//
//        // then
//        InOrder inOrder = inOrder(securityService, mapperUtil, clientVendorRepository);
//        inOrder.verify(securityService).getLoggedInUser();
//        inOrder.verify(mapperUtil).convert(clientVendorDTO,ClientVendor.class);
//        inOrder.verify(clientVendorRepository).save(clientVendor);
//        inOrder.verify(mapperUtil).convert(clientVendor,ClientVendorDTO.class);
//        assertNotNull(savedClientVendorDTO);
//        assertEquals(clientVendorDTO, savedClientVendorDTO);
//    }
}