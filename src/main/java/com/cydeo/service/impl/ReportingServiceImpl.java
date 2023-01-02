package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.ReportingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class ReportingServiceImpl implements ReportingService {

    private final InvoiceProductService invoiceProductService;

    public ReportingServiceImpl(InvoiceProductService invoiceProductService) {
        this.invoiceProductService = invoiceProductService;
    }


    @Override
    public List<InvoiceProductDTO> generateReport() {
        return null;
    }
    //need invoice product implementation service class before i can make

    @Override
    public Map<String, BigDecimal> profitLossPerMonth() {
        return null;
    }
    //same for this one
}
