package com.cydeo.service;


import com.cydeo.dto.ClientVendorDTO;

import java.util.List;

public interface ClientVendorService {

    ClientVendorDTO findById(Long id);

    public void save(ClientVendorDTO clientVendorDTO);

    void delete(Long id);

    void update(ClientVendorDTO clientVendorDTO);

    List<ClientVendorDTO> listAllClientVendors();

}
