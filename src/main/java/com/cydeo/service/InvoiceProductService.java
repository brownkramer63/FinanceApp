package com.cydeo.service;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.InvoiceProduct;

import java.util.List;

public interface InvoiceProductService {


    InvoiceProductDTO findById(Long id);
    List<InvoiceProductDTO> findByInvoiceProductId(Long id);

    void update(InvoiceDTO invoiceDTO, Long id);

    void removeInvoiceProduct(Long id);
    InvoiceProductDTO addInvoiceProduct(Long id, InvoiceProductDTO invoiceProductDTO);

    void delete(Long id);



}
