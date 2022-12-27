package com.cydeo.converter;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    private final ModelMapper mapper;

    public UserDtoConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    User convertToEntity(UserDTO id){
        return mapper.map(id, User.class);
    }

    UserDTO convertToDto(User id){
        return mapper.map(id, UserDTO.class);
    }
}
