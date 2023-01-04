package com.cydeo.service;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.enums.InvoiceType;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceService {

    InvoiceDTO findById(Long id);
    List<InvoiceDTO> listAllInvoicesByType(InvoiceType invoiceType);
    void delete(Long id);

     List<InvoiceDTO> listAllInvoice();

     void approve(Long id);

     InvoiceDTO create(InvoiceDTO invoiceDTO, InvoiceType invoiceType);




    InvoiceDTO getNewPurchaseInvoice();
    InvoiceDTO getNewSalesInvoice();

    InvoiceDTO save(InvoiceDTO invoiceDTO, InvoiceType invoiceType);

    BigDecimal calculateProfitLoss(Long id);
}
