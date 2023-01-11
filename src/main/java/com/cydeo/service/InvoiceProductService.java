package com.cydeo.service;

import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;


import java.util.List;

public interface InvoiceProductService {


    InvoiceProductDTO findById(Long id);
    List<InvoiceProductDTO> findAllInvoiceProductByInvoiceId(Long id);


    void removeInvoiceProduct( Long id);
    InvoiceProductDTO addInvoiceProduct(Long id, InvoiceProductDTO invoiceProductDTO) throws Exception;

    void delete(Long id);
    void deleteProductByInvoiceId(Long id);
    List<InvoiceProductDTO> printInvoice(Long id);

    public List<InvoiceProductDTO> listAllBasedOnStatusOrderByDateDesc();

    public List<InvoiceProduct> findAllByCompanyAndInvoiceTypeAndInvoiceStatus(Company company, InvoiceType invoiceType, InvoiceStatus invoiceStatus);


}
