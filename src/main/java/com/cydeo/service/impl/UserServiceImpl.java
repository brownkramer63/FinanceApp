package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<UserDTO> getUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(user -> mapperUtil.convert(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return mapperUtil.convert(user, new UserDTO());
    }

    @Override
    public void save(UserDTO userDTO) {
        userDTO.setPassword(userDTO.getPassword());
        userDTO.setConfirmPassword(userDTO.getPassword());
        User user = mapperUtil.convert(userDTO, new User());
        userRepository.save(user);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {

        Optional<User> user = userRepository.findById(userDTO.getId());
        User convertedUser = mapperUtil.convert(userDTO, new User());
        convertedUser.setId(user.get().getId());
        convertedUser.setPassword(user.get().getPassword());
        userRepository.save(convertedUser);
        return findById(userDTO.getId());
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
        User user = userRepository.findUserByUserNameAndIsDeleted(username, false);
        return mapperUtil.convert(user, new UserDTO());
    }




}
