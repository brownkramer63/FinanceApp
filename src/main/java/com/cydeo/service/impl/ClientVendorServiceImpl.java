package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.entity.ClientVendor;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.security.SecurityService;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.CompanyService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;

    private final CompanyService companyService;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, SecurityService securityService, MapperUtil mapperUtil, CompanyService companyService) {
        this.clientVendorRepository = clientVendorRepository;
        this.securityService = securityService;
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
    }

    @Override
    public ClientVendorDTO findById(Long id) {

      ClientVendor obj = clientVendorRepository.findById(id).orElseThrow(() ->new NoSuchElementException("clientVendor does not exist"));
        return mapperUtil.convert(obj, new ClientVendorDTO());
    }

    @Override
    public ClientVendorDTO save(ClientVendorDTO clientVendorDTO) {
        clientVendorDTO.setCompany(companyService.getCompanyByLoggedInUser());
        ClientVendor clientVendor = mapperUtil.convert(clientVendorDTO, new ClientVendor());

        ClientVendor save = clientVendorRepository.save(clientVendor);
        return mapperUtil.convert(save,new ClientVendorDTO());
    }

    @Override
    public void delete(Long id) {
        Optional <ClientVendor> clientVendor= clientVendorRepository.findById(id);
        if (clientVendor.isPresent()){
            clientVendor.get().setIsDeleted(true);
            clientVendorRepository.save(clientVendor.get());
        }else {
            throw new NoSuchElementException("client or vendor does not exist with select Id");
        }
    }

    @Override
    public ClientVendorDTO update(ClientVendorDTO clientVendorDTO, Long clientVendorId) {
        Optional<ClientVendor> clientVendor = clientVendorRepository.findById(clientVendorId);
        ClientVendor updatedClientVendor = mapperUtil.convert(clientVendorDTO, new ClientVendor());
        updatedClientVendor.setId(clientVendorId);
        ClientVendor savedClientVendor=clientVendorRepository.save(updatedClientVendor);
        return mapperUtil.convert(savedClientVendor, new ClientVendorDTO());

    }

    @Override
    public List<ClientVendorDTO> listAllClientVendors() {
        return clientVendorRepository.findAll(Sort.by("clientVendorType")).stream().filter(clientVendor ->
                clientVendor.getCompany().getId().equals(securityService.getLoggedInUser().getCompany().getId())).
                filter(clientVendor -> clientVendor.getIsDeleted().equals(false)).map(
                clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDTO())).collect(Collectors.toList());

    }

    @Override
    public List<ClientVendor> findAllByClientVendorName(String name) {
        return clientVendorRepository.findAll().stream().filter(clientVendor -> clientVendor.getClientVendorName().equals(name)).collect(Collectors.toList());

    }


}
