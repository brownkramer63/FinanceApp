package com.cydeo.service;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;

public interface InvoiceProductService {

    void save(InvoiceDTO dto);
    void update(InvoiceProductDTO dto);
    void delete(Long id);

    InvoiceProductDTO findById(Long id);



}
