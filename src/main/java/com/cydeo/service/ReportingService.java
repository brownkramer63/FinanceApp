package com.cydeo.service;

import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.User;

import java.util.List;

public interface ReportingService {

    List<InvoiceProduct> generateReport();
    //need user so i know which invoices to pull
}
