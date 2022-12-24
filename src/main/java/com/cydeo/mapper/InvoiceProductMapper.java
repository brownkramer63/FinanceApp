package com.cydeo.mapper;


import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.InvoiceProduct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class InvoiceProductMapper {

    private ModelMapper modelMapper;

    public InvoiceProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public InvoiceProduct convertToEntity(InvoiceProductDTO dto){
        return modelMapper.map(dto, InvoiceProduct.class);
    }

    public InvoiceProductDTO convertToDTO(InvoiceProduct entity){
        return modelMapper.map(entity, InvoiceProductDTO.class);
    }

}
