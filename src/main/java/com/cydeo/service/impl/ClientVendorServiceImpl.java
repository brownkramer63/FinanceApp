package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.entity.ClientVendor;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;

import java.util.Optional;

public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final MapperUtil mapperUtil;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public ClientVendorDTO findById(Long id) {

       ClientVendor obj= clientVendorRepository.findById(id).get();
       return mapperUtil.convert(obj, new ClientVendorDTO());
    }

    @Override
    public void save(ClientVendorDTO clientVendorDTO) {
      ClientVendor clientVendor=  mapperUtil.convert(clientVendorDTO, new ClientVendor());
      clientVendorRepository.save(clientVendor);
    }

    @Override
    public void delete(Long id) {
    //need to implement a soft delete for this
    }

    @Override
    public void update(ClientVendorDTO clientVendorDTO) {
    Optional<ClientVendor> clientVendor= clientVendorRepository.findById(clientVendorDTO.getId());
    //now we have client vendor need to reset all fields to update
        ClientVendor updatedClientVendor= mapperUtil.convert(clientVendorDTO, new ClientVendor());

        updatedClientVendor.setClientVendorName(clientVendor.get().getClientVendorName());
        updatedClientVendor.setClientVendorType(clientVendor.get().getClientVendorType());
        updatedClientVendor.setId(clientVendor.get().getId());
//        updatedClientVendor.setAddress(clientVendor.get().getAddress());
//        updatedClientVendor.setCompany(clientVendor.get().getCompany());
//        updatedClientVendor.setPhone(clientVendor.get().getPhone());
//        updatedClientVendor.setWebsite(clientVendor.get().getWebsite());
        //not sure if i did this right actually

    }
}
