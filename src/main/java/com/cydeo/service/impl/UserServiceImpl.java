package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.*;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.repository.RoleRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.RoleService;
import com.cydeo.security.SecurityService;
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
    private final CompanyRepository companyRepository;
    private final CompanyService companyService;
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final RoleService roleService;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, @Lazy RoleService roleService,
                           SecurityService securityService, CompanyRepository companyRepository,
                           RoleRepository roleRepository, @Lazy CompanyService companyService,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.roleService = roleService;
        this.securityService = securityService;
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
        this.companyService = companyService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> findAllByLoggedInUser() {

        UserDTO loggedInUser = securityService.getLoggedInUser();

        List<User> userList = userRepository.getAllByOrderByCompanyAndRole("Admin");
        List<User> allByCompany = userRepository.getAllByCompanyAndRole();


        if (loggedInUser.getRole().getDescription().equals("Root User")) {
            return userList.stream().map(user -> mapperUtil.convert(user, new UserDTO()))
                    .collect(Collectors.toList());

        } else if (loggedInUser.getRole().getDescription().equals("Admin")) {

            return allByCompany.stream()
                    .filter(each -> each.getCompany().getId().equals(loggedInUser.getCompany().getId()))
                    .map(each -> mapperUtil.convert(each, new UserDTO()))
                    .collect(Collectors.toList());
        }
        return allByCompany.stream().map(each -> mapperUtil.convert(each, new UserDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return mapperUtil.convert(user, new UserDTO());
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
        User convertedUser = mapperUtil.convert(userDTO, new User());
        convertedUser.setId(user.get().getId());
        convertedUser.setPassword(user.get().getPassword());
        userRepository.save(convertedUser);
        return mapperUtil.convert(convertedUser, new UserDTO());
    }

    public boolean isOnlyAdmin(UserDTO userDTO) {

        List<User> admin = userRepository.findAllByCompanyId(userDTO.getCompany().getId()).stream()
                .filter(user -> user.getRole().getDescription().equals("Admin"))
                .collect(Collectors.toList());

        return admin.size() == 1;
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

        Optional<User> user = userRepository.findByUsername(username);
        return mapperUtil.convert(user, new UserDTO());
    }


}







