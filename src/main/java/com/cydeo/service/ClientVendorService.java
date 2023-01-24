package com.cydeo.service;


import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;

import java.util.List;
import java.util.Optional;

public interface ClientVendorService {

    ClientVendorDTO findById(Long id);

    public ClientVendorDTO save(ClientVendorDTO clientVendorDTO);

    void delete(Long id) throws IllegalAccessException;

//    void update(ClientVendorDTO clientVendorDTO, Long clientVendorId);

//    ClientVendorDTO update(ClientVendorDTO clientVendorDTO, Long clientVendorId);
    ClientVendorDTO update(ClientVendorDTO clientVendorDTO);

    List<ClientVendorDTO> listAllClientVendors();

    public List<ClientVendor> findAllByClientVendorName(String name);

    public List<ClientVendorDTO> findAllClients();

    public List<ClientVendorDTO> findAllVendors();

    boolean findIfCompanyHasOpenInvoices(Long id);

    boolean findIfCompanyNameExists(ClientVendorDTO clientVendorDTO);

    Optional<ClientVendor> findClientVendorById(Long id);

}
