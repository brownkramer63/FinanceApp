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

        ClientVendor obj = clientVendorRepository.findById(id).get();
        return mapperUtil.convert(obj, new ClientVendorDTO());
    }

    @Override
    public void save(ClientVendorDTO clientVendorDTO) {
        clientVendorDTO.setCompany(companyService.getCompaniesByLoggedInUser().get(0));
        ClientVendor clientVendor = mapperUtil.convert(clientVendorDTO, new ClientVendor());
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
