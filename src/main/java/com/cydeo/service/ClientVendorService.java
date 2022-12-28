package com.cydeo.service;


import com.cydeo.dto.ClientVendorDTO;

public interface ClientVendorService {

    ClientVendorDTO findById(Long id);

    public void save(ClientVendorDTO clientVendorDTO);

    void delete(Long id);

    void update(ClientVendorDTO clientVendorDTO);

}
