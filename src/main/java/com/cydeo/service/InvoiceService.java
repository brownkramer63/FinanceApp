package com.cydeo.service;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.entity.Invoice;
import com.cydeo.enums.InvoiceType;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceService {

    InvoiceDTO findById(Long id);

    void delete(Long id);
     List<InvoiceDTO> listAllInvoice();

     void approve(Long id);

     InvoiceDTO create(InvoiceDTO invoiceDTO, InvoiceType invoiceType);

     List<InvoiceDTO> listAllInvoicesByType(InvoiceType invoiceType);


    InvoiceDTO getNewPurchaseInvoice();
    InvoiceDTO getNewSalesInvoice();

    InvoiceDTO save(InvoiceDTO invoiceDTO, InvoiceType invoiceType);
}
