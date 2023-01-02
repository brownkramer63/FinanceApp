package com.cydeo.service;

import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ReportingService {

    List<InvoiceProductDTO> generateReport();
    //need user so i know which invoices to pull dont know which ones

    Map<String, BigDecimal> profitLossPerMonth();
}
