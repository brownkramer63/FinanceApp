package com.cydeo.service;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;

import java.util.List;

public interface InvoiceProductService {


    InvoiceProductDTO findById(Long id);
    List<InvoiceProductDTO> findByInvoiceProductId(Long id);

    void update(InvoiceDTO invoiceDTO, Long id);

    void removeInvoiceProduct(Long id);
    void save(Long id, InvoiceProductDTO invoiceProductDTO);



}
