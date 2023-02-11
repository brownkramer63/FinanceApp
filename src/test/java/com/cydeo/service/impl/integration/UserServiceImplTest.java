package com.cydeo.service.impl.integration;

import com.cydeo.dto.UserDTO;
import com.cydeo.motherOftests.TestData;
import com.cydeo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.assertj.core.api.Assertions;

import java.util.List;

import static com.cydeo.motherOftests.TestData.userDTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService;

    @Test
    @Transactional
    @WithMockUser(username = "admin@greentech.com", password = "Abc1", roles = "Admin")
    void findAllByLoggedInUser() {
        List<UserDTO> userDTOList=userService.findAllByLoggedInUser();
        Assertions.assertThat(userDTOList).hasSize(4);
        assertThat(userDTOList.get(0).getUsername()).isEqualTo("admin@greentech.com");
    }

    @Test
    @Transactional
    void findById() {
        UserDTO userDTO= userService.findById(1L);
        Assertions.assertThat(userDTO.getUsername()).isEqualTo("root@cydeo.com");
    }

    @Test
    void save() {
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin@greentech.com", password = "Abc1", roles = "Admin")
    void update() {
        UserDTO userToTest = TestData.userDTO;
        userToTest.setId(1L);
        UserDTO updatedUserDTO = userService.update(userToTest);
        assertThat(updatedUserDTO.getId()).isEqualTo(1L);
        //assertThat(updatedUserDTO.getUsername()).isEqualTo(userToTest);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin@greentech.com", password = "Abc1", roles = "Admin")
    void isOnlyAdmin() {
    }

    @Test
    @WithMockUser(username = "admin@greentech.com", password = "Abc1", roles = "Admin")
    void isEmailAlreadyExists() {
        UserDTO testDTO = userDTO;
        boolean emailAlreadyExists = userService.isEmailAlreadyExists(userDTO);
        assertThat(userDTO).isEqualTo(userDTO);
    }

    @Test
    @Transactional
    @WithMockUser(username = "manager@greentech.com", password = "Abc1", roles = "Manager")
    void delete() {
        UserDTO testUser = TestData.userDTO;
        userService.delete(userDTO.getId());
        assertThat(testUser).isNotNull();
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin@greentech.com", password = "Abc1", roles = "Admin")
    void findByUsername() {
        UserDTO userDTO = userService.findByUsername("admin@greentech.com");
        assertThat(userDTO.getUsername()).isEqualTo("admin@greentech.com");

    }
}