package com.cydeo.service;


import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.entity.ClientVendor;

import java.util.List;

public interface ClientVendorService {

    ClientVendorDTO findById(Long id);

    public ClientVendorDTO save(ClientVendorDTO clientVendorDTO);

    void delete(Long id);

    ClientVendorDTO update(ClientVendorDTO clientVendorDTO, Long clientVendorId);

    List<ClientVendorDTO> listAllClientVendors();

    public List<ClientVendor> findAllByClientVendorName(String name);

}
