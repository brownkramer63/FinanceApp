package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.entity.ClientVendor;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.security.SecurityServiceImpl;
import com.cydeo.service.ClientVendorService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final UserRepository userRepository;
    private final SecurityServiceImpl securityService;
    private final MapperUtil mapperUtil;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, UserRepository userRepository, SecurityServiceImpl securityService, MapperUtil mapperUtil) {
        this.clientVendorRepository = clientVendorRepository;
        this.userRepository = userRepository;
        this.securityService = securityService;
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
        Optional<ClientVendor> clientVendor = clientVendorRepository.findById(clientVendorDTO.getId());
        ClientVendor updatedClientVendor = mapperUtil.convert(clientVendorDTO, new ClientVendor());
        if (clientVendor.isPresent()) {
            updatedClientVendor.setId(clientVendor.get().getId());
            clientVendorRepository.save(updatedClientVendor);
        }
    }

    @Override
    public List<ClientVendorDTO> listAllClientVendors() {
        return clientVendorRepository.findAll(Sort.by("clientVendorType")).stream().filter(clientVendor ->
                clientVendor.getCompany().getId().equals(securityService.getLoggedInUser().getCompany().getId())).map(
                        clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDTO())).collect(Collectors.toList());

    }

    @Override
    public List<ClientVendor> findAllByClientVendorName(String name) {
       return clientVendorRepository.findAll().stream().filter(clientVendor -> clientVendor.getClientVendorName().equals(name)).collect(Collectors.toList());

    }


}
