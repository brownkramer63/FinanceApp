package com.cydeo.service.impl;

import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.User;
import com.cydeo.service.ReportingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingServiceImpl implements ReportingService {


    @Override
    public List<InvoiceProduct> generateReport() {
        return null;
    }
}
