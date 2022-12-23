package com.cydeo.service;

import com.cydeo.dto.InvoiceProductDTO;

public interface InvoiceProductService {

    void save(InvoiceProductDTO dto);
    void update(InvoiceProductDTO dto);
    void delete(Long id);

    InvoiceProductDTO findById(Long id);

    InvoiceProductDTO<InvoiceProductDTO> listAllTasks();


}
