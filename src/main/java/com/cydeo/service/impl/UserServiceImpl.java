package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil,@Lazy RoleService roleService,
                           SecurityService securityService, CompanyRepository companyRepository,
                           RoleRepository roleRepository,@Lazy CompanyService companyService,
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

        List<User> userList = userRepository.findAllByRole_DescriptionOrderByCompany("Admin");

        if (loggedInUser.getRole().getDescription().equals("Root User")) {
            return userList.stream().map(user -> mapperUtil.convert(user, new UserDTO()))
                    .collect(Collectors.toList());

        } else if (loggedInUser.getRole().getDescription().equals("Admin")) {
            List<User> allByCompanyId = userRepository.findAllByCompanyId(loggedInUser.getCompany().getId());
            return allByCompanyId.stream().map(each -> mapperUtil.convert(each, new UserDTO())).collect(Collectors.toList());
        }
        return Collections.emptyList();
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

    @Override
    public void delete(Long id) {
        Optional<User> user = Optional.ofNullable(userRepository.findByIdAndIsDeleted(id, false));
        user.get().setIsDeleted(true);
        user.get().setUsername(user.get().getUsername() + "-" + user.get().getId());
        userRepository.save(user.get());
    }
    @Override
    public UserDTO findByUsername(String username) {

        User user = userRepository.findUserByUsernameAndIsDeleted(username, false);
        return mapperUtil.convert(user, new UserDTO());
    }


}
