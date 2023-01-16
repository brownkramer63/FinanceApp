package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import com.cydeo.exception.RoleNotFoundException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.RoleRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.security.SecurityService;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil, SecurityService securityService,
                           UserRepository userRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public RoleDTO findById(Long id) {
        return mapperUtil.convert(roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role does not exist")), new RoleDTO());
    }

    @Override
    public List<RoleDTO> listRoles() {

        UserDTO loggedInUser = securityService.getLoggedInUser();

        List<Role> roleList = roleRepository.findAll();
        if (loggedInUser.getRole().getDescription().equals("Root User")) {
            log.info("List of users: " + roleList.size());
            return roleList.stream()
                    .filter(role -> role.getDescription().equals("Admin"))
                    .map(role -> mapperUtil.convert(role, new RoleDTO()))
                    .collect(Collectors.toList());

        } else if (loggedInUser.getRole().getDescription().equals("Admin")) {
            List<User> userList = userRepository.findAllByCompanyId(loggedInUser.getCompany().getId());
            long admin = userList.stream()
                    .filter(user -> user.getRole().getDescription().equals("Admin"))
                    .count();
            if (admin==1){
                RoleDTO convert = mapperUtil.convert(roleRepository.findById(2L), new RoleDTO());
                return List.of(convert);
            }
            log.info("Users size: " + userList.size() + " - " +loggedInUser.getCompany().getTitle());
            return roleList.stream()
                    .filter(role -> !role.getDescription().equals("Root User"))
                    .map(role -> mapperUtil.convert(role, new RoleDTO()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();

    }
}
