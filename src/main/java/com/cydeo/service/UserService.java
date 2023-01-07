package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO findById(Long id);
    UserDTO save(UserDTO userDTO);
    UserDTO update (UserDTO userDTO);
    void delete(Long id);
    UserDTO findByUsername(String username);
    List<UserDTO> findAllByLoggedInUser();
    boolean isOnlyAdmin(UserDTO userDTO);
    boolean isEmailAlreadyExists(UserDTO userDTO);
}
