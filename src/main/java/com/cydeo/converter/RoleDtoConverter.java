package com.cydeo.converter;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleDtoConverter {

    private final ModelMapper mapper;
    public RoleDtoConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }
    public Role convertToEntity(RoleDTO dto){
        return mapper.map(dto, Role.class);
    }

    public RoleDTO convertToDto(Role entity){
        return mapper.map(entity, RoleDTO.class);
    }


}
