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

    User convertToEntity(UserDTO dto){
        return mapper.map(dto, User.class);
    }

    UserDTO convertToDto(User entity){
        return mapper.map(entity, UserDTO.class);
    }
}
