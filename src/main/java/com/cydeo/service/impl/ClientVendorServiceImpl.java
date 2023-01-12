package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.entity.Address;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.repository.InvoiceRepository;
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

    private final InvoiceRepository invoiceRepository;

    private final CompanyService companyService;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, SecurityService securityService, MapperUtil mapperUtil, InvoiceRepository invoiceRepository, CompanyService companyService) {
        this.clientVendorRepository = clientVendorRepository;
        this.securityService = securityService;
        this.mapperUtil = mapperUtil;
        this.invoiceRepository = invoiceRepository;
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
    public void delete(Long id) throws IllegalAccessException {
        Optional <ClientVendor> clientVendor= clientVendorRepository.findById(id);
        if (invoiceRepository.existsById(id)){
            throw new IllegalAccessException("Cannot be deleted as there is an open invoice linked to this Client or Vendor");
        }

        if (clientVendor.isPresent()){
            clientVendor.get().setIsDeleted(true);
            clientVendorRepository.save(clientVendor.get());
        }

    }


    @Override
    public ClientVendorDTO update(ClientVendorDTO clientVendorDTO) {
        Optional<ClientVendor> clientVendor = clientVendorRepository.findById(clientVendorDTO.getId());
        ClientVendor updatedClientVendor = mapperUtil.convert(clientVendorDTO, new ClientVendor()); //convert what we got into new client vendor

        if (clientVendor.isPresent()) { //build whole new clientvendor
            updatedClientVendor.setId(clientVendor.get().getId());
            updatedClientVendor.setCompany(mapperUtil.convert(securityService.getLoggedInUser().getCompany(),new Company()));
            updatedClientVendor.setWebsite(clientVendorDTO.getWebsite());
            updatedClientVendor.setClientVendorType(clientVendorDTO.getClientVendorType());
            updatedClientVendor.setAddress(mapperUtil.convert(clientVendorDTO.getAddress(), new Address()));

            clientVendorRepository.save(updatedClientVendor);
//            clientVendorRepository.delete(clientVendor.get());//added this no ida how to get rid of old clientvendor
    }
        return mapperUtil.convert(updatedClientVendor, new ClientVendorDTO());
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

    @Override
    public List<ClientVendorDTO> findAllClients() {
        return clientVendorRepository.findAll().stream().filter(clientVendor -> clientVendor.getClientVendorType()
                .getValue().equals("Client")).map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDTO())).collect(Collectors.toList());
    }

    @Override
    public List<ClientVendorDTO> findAllVendors() {
        return clientVendorRepository.findAll().stream().filter(clientVendor -> clientVendor.getClientVendorType()
                .getValue().equals("Vendor")).map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDTO())).collect(Collectors.toList());

    }


}
