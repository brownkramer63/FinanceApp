package com.cydeo.service.impl;


import com.cydeo.dto.UserDTO;
import com.cydeo.entity.*;
import com.cydeo.exception.UserNotFoundException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import com.cydeo.security.SecurityService;
import com.cydeo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, SecurityService securityService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> findAllByLoggedInUser() {

        UserDTO loggedInUser = securityService.getLoggedInUser();
        log.info("LoggedIn user is; " + loggedInUser.getRole().getDescription());
        List<User> allByCompany = userRepository.getAllByCompanyAndRole();
        List<User> allByCompanyAndRole = userRepository.getAllByCompanyAndRole();


        if (loggedInUser.getRole().getDescription().equals("Root User")) {
            log.info("getting all the admins");
            return allByCompanyAndRole.stream()
                    .filter(user -> user.getRole().getDescription().equals("Admin"))
                    .map(user -> mapperUtil.convert(user, new UserDTO()))
                    .peek(userDTO -> {
                        if (isOnlyAdmin(userDTO)) {
                            userDTO.setOnlyAdmin(true);
                        }
                    })
                    .collect(Collectors.toList());

        } else if (loggedInUser.getRole().getDescription().equals("Admin")) {

            return allByCompany.stream()
                    .filter(each -> each.getCompany().getId().equals(loggedInUser.getCompany().getId()))
                    .map(each -> mapperUtil.convert(each, new UserDTO()))
                    .peek(userDTO -> {
                        if (isOnlyAdmin(userDTO)) {
                            userDTO.setOnlyAdmin(true);
                        }
                    })
                    .collect(Collectors.toList());
        }
        return allByCompany.stream().map(each -> mapperUtil.convert(each, new UserDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id){
        Optional<User> user = Optional.ofNullable(userRepository.findById(id)).orElseThrow(() -> new UserNotFoundException("User does not exist"));
        UserDTO userDTO = mapperUtil.convert(user, new UserDTO());
        if(user.isPresent()){
            if (isOnlyAdmin(userDTO)){
                userDTO.setOnlyAdmin(true);
            }
        }
        return userDTO;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = mapperUtil.convert(userDTO, new User());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        return mapperUtil.convert(user, new UserDTO());
    }

    @Override
    public UserDTO update(UserDTO userDTO) {

        Optional<User> user = userRepository.findById(userDTO.getId());
        if(isOnlyAdmin(userDTO)){
            userDTO.setOnlyAdmin(true);
            return userDTO;
        }
        User convertedUser = mapperUtil.convert(userDTO, new User());
        convertedUser.setId(user.get().getId());
        convertedUser.setPassword(user.get().getPassword());
        convertedUser.setEnabled(true);                                   //jan11 added bug fix
        userRepository.save(convertedUser);
        return mapperUtil.convert(convertedUser, new UserDTO());
    }

    @Override
    public boolean isOnlyAdmin(UserDTO userDTO) {

        long admin = userRepository.findAllByCompanyId(userDTO.getCompany().getId()).stream()
                .filter(user -> user.getRole().getDescription().equals("Admin"))
                .count();

        return admin == 1;
    }

    @Override
    public boolean isEmailAlreadyExists(UserDTO userDTO) {
        return userRepository.findByUsername(userDTO.getUsername()).isPresent();
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).get();
        user.setIsDeleted(true);
        user.setUsername(user.getUsername() + "-" + user.getId());
        userRepository.save(user);

    }

    @Override
    public UserDTO findByUsername(String username) {

        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User does not exist")));
        return mapperUtil.convert(user, new UserDTO());
    }


}







