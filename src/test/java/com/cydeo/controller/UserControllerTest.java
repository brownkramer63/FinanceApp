package com.cydeo.controller;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import com.cydeo.security.SecurityConfig;
import com.cydeo.service.CompanyService;
import com.cydeo.service.UserService;
import com.cydeo.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
//ﬁ@ActiveProfiles("data.sql")
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserServiceImpl userServiceImplMock;
    @Autowired
    CompanyService companyService;
    @Autowired
    UserController userController;
    @Autowired
    MapperUtil mapperUtil;
    static User user;
    static UserDTO userDTO;

    //    static UserDTO rootUser;
    static UserDTO adminGreenTech1;
    static UserDTO adminGreenTech2;
//    static UserDTO manager;
//    static UserDTO employee;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setFirstname("Chris");
        user.setLastname("Brown");
        user.setPassword("Abc1");
        user.setEnabled(true);
        Role role = new Role();
        role.setDescription("Admin");
        Company company = new Company();
        company.setTitle("BlueTech");
        user.setCompany(user.getCompany());

        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(1L);
        companyDTO.setTitle("BlueTech");
        userDTO = new UserDTO();
        userDTO.setId(null);
        userDTO.setFirstname("Chris");
        userDTO.setLastname("Brown");
        userDTO.setUsername("admin@bluetech.com");
        userDTO.setPassword("Abc1");
        userDTO.setCompany(userDTO.getCompany());
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setDescription("Admin");
        userDTO.setRole(roleDTO);
//        adminGreenTech1 = new UserDTO(3L, "admin@greentech.com", "Abc1", "Abc1", "Mary", "Grant", "+1 (234) 345-4362", new RoleDTO(2L, "Admin"), new CompanyDTO(), true);
//        adminGreenTech2 = new UserDTO(2L, "admin2@greentech.com", "Abc1", "Abc1", "Garrison", "Short", "+1 (234) 356-7865", new RoleDTO(2L, "Admin"), new CompanyDTO(), false);

    }

    @Test
    public void listUsersHttpRequestTest() throws Exception {
       List<UserDTO> userDTOList=userService.findAllByLoggedInUser();
       assertEquals(4,userDTOList.size());


    }

    @Test
    public void createUserTest() throws Exception {

        MockMvcBuilders.standaloneSetup(userController)
                        .build()
                                .perform(MockMvcRequestBuilders.get("/users/create"));
        mvc.perform(MockMvcRequestBuilders.get("/users/create")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());




    }

    @Test
    public void testUnsuccessfulCreate() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.post("/create")
                .param("name", "Garrison")
                .param("lastname", "Short")
                .param("email", "   ");
        mvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("user-create"));


    }


    @Test
    public void deleteTest() throws Exception {  //passed
        assertTrue(userRepository.findById(1L).isPresent());  //make sure user exist
        RequestBuilder request = MockMvcRequestBuilders.get("/delete/" + adminGreenTech2.getId());
        MvcResult mvcResult = mvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"))
                .andReturn();   //"users/list"
        // assertFalse(userRepository.findById(1L).isPresent());
    }

    //@Test
    private String toJsonString(final Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(obj);

    }
}
