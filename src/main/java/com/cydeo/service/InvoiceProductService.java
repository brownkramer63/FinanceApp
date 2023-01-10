package com.cydeo.service;

import com.cydeo.dto.InvoiceProductDTO;

import java.util.List;

public interface InvoiceProductService {


    InvoiceProductDTO findById(Long id);
    List<InvoiceProductDTO> findAllInvoiceProductByInvoiceId(Long id);


    void removeInvoiceProduct( Long id);
    InvoiceProductDTO addInvoiceProduct(Long id, InvoiceProductDTO invoiceProductDTO) throws Exception;

    void delete(Long id);
    void deleteProductByInvoiceId(Long id);



}
