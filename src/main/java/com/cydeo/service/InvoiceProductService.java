package com.cydeo.service;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.InvoiceProduct;

import java.util.List;

public interface InvoiceProductService {


    InvoiceProductDTO findById(Long id);
    List<InvoiceProductDTO> findByInvoiceProductId(Long id);


    void removeInvoiceProduct( Long id);
    InvoiceProductDTO addInvoiceProduct(Long id, InvoiceProductDTO invoiceProductDTO) throws Exception;

    void delete(Long id);

    public List<InvoiceProductDTO> listAllBasedOnStatusOrderByDateDesc();



}
