package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.RoleRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.security.SecurityService;
import com.cydeo.service.CompanyService;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;
    private final UserService userService;
    private final SecurityService securityService;
    private final CompanyService companyService;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil, UserService userService, SecurityService securityService, CompanyService companyService,
                           UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
        this.securityService = securityService;
        this.companyService = companyService;
        this.userRepository = userRepository;
    }

    @Override
    public RoleDTO findById(Long id) {
        return mapperUtil.convert(roleRepository.findById(id), new RoleDTO());
    }

    @Override
    public List<RoleDTO> listRoles() {

        UserDTO loggedInUser = securityService.getLoggedInUser();

        List<Role> roleList = roleRepository.findAll();                     //findRolesBy(loggedInUser.getRole().getId());  //find roles by logged in user
        if (loggedInUser.getRole().getDescription().equals("Root User")) {
            log.info("List " + roleList.size());
            return roleList.stream()
                    .filter(role -> role.getDescription().equals("Admin"))
                    .map(role -> mapperUtil.convert(role, new RoleDTO()))
                    .collect(Collectors.toList());

        } else if (loggedInUser.getRole().getDescription().equals("Admin")) {                   //if admin loggedIn
            List<User> userList = userRepository.findAllByCompanyId(loggedInUser.getCompany().getId());
            log.info("User Roles size " + userList.size() + loggedInUser.getCompany().getTitle());
            return userList.stream()
                    .filter(user -> !user.getRole().getDescription().equals("Root User"))
                    .map(role -> mapperUtil.convert(role, new RoleDTO()))
                    .collect(Collectors.toList());

        }
        return Collections.emptyList();

    }

}