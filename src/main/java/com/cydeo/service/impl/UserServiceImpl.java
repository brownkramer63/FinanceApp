package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.*;
import com.cydeo.exception.UserNotFoundException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.repository.RoleRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.security.SecurityService;
import com.cydeo.service.CompanyService;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, SecurityService securityService, PasswordEncoder passwordEncoder, @Lazy CompanyService companyService, CompanyRepository companyRepository,
                           RoleRepository roleRepository, @Lazy RoleService roleService) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.passwordEncoder = passwordEncoder;
        this.companyService = companyService;
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }

    @Override
    public List<UserDTO> findAllByLoggedInUser() {

        UserDTO loggedInUser = securityService.getLoggedInUser();
        log.info("LoggedIn user is; " + loggedInUser.getRole().getDescription());
        List<User> allByCompany = userRepository.getAllByCompanyAndRole();
        Long companyDTOId = loggedInUser.getCompany().getId();
        List<User> allUsersOfSpecificCompany = userRepository.findAllByCompanyId(companyDTOId);// all users of a company

        if (loggedInUser.getRole().getDescription().equals("Root User")) {
            log.info("getting all the admins");
            return allByCompany.stream()
                    .filter(user -> user.getRole().getDescription().equals("Admin"))
                    .map(user -> mapperUtil.convert(user, new UserDTO()))
                    .peek(userDTO -> {
                        if (isOnlyAdmin(userDTO)) {
                            userDTO.setOnlyAdmin(true);
                            log.info("Updated user is : "+ userDTO.getUsername());
                        }
                    })
                    .collect(Collectors.toList());

        } else if (loggedInUser.getRole().getDescription().equals("Admin")) {
            if (isOnlyAdmin(loggedInUser)) {
                loggedInUser.setOnlyAdmin(true);
            }
            return allUsersOfSpecificCompany.stream()
                    .map(each -> mapperUtil.convert(each, new UserDTO()))
                    .collect(Collectors.toList());
        }
        return allByCompany.stream().map(each -> mapperUtil.convert(each, new UserDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id)).orElseThrow(() -> new UserNotFoundException("User does not exist"));
        UserDTO userDTO = mapperUtil.convert(user, new UserDTO());
        if (user.isPresent()) {
            if (userDTO.getRole().getDescription().equals("Admin")) {
                if (isOnlyAdmin(userDTO)) {
                    userDTO.setOnlyAdmin(true);
                    return userDTO;
                }
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

        Optional<User> user = userRepository.findById(userDTO.getId());  //get me user I want to update from UI
        User convertedUser = mapperUtil.convert(userDTO, new User());    // we have entity now
        convertedUser.setId(user.get().getId());                         //
        convertedUser.setPassword(user.get().getPassword());
        convertedUser.setEnabled(true); //jan11 added bug fix
        if (isOnlyAdmin(userDTO)) {                                      // when updating admin, saves as admin
            convertedUser.setRole(user.get().getRole());
        }
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







