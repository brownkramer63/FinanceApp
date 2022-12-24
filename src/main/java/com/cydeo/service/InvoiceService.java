package com.cydeo.service;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.entity.Invoice;

public interface InvoiceService {

    InvoiceDTO findById(Long id);

    void delete(Long id);
}
