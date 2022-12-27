package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getUsers();
    UserDTO findById(Long id);
    void save(UserDTO userDTO);
    UserDTO update (UserDTO userDTO);
    //void delete(Long id);
}
