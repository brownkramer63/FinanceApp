package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Role;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
        if (clientVendor.isPresent()) {
            ClientVendor updatedClientVendor = clientVendor.get();

            updatedClientVendor.setClientVendorName(clientVendorDTO.getClientVendorName());
            updatedClientVendor.setClientVendorType(clientVendorDTO.getClientVendorType());
            updatedClientVendor.setWebsite(clientVendorDTO.getWebsite());
            updatedClientVendor.setPhone(clientVendorDTO.getPhone());
            updatedClientVendor.setCompany(clientVendor.get().getCompany()); //check this one
            updatedClientVendor.setId(clientVendorDTO.getId());
            updatedClientVendor.setAddress(clientVendor.get().getAddress());//check this one

            clientVendorRepository.save(updatedClientVendor);
            //review the logic for this one
        }
    }

    @Override
    public List<ClientVendorDTO> listAllClientVendors() {
        List<ClientVendor> clientVendors= clientVendorRepository.findAll();
        return clientVendors.stream().map(clientVendor ->mapperUtil.convert(clientVendor,new ClientVendorDTO())).collect(Collectors.toList());
    }

}
