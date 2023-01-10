package com.cydeo.service;

import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.User;
import com.cydeo.repository.InvoiceRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface ReportingService {



    List<InvoiceProductDTO> generateReport();
    //need user so i know which invoices to pull dont know which ones

    Map<String, BigDecimal> profitLossDataMap();

    BigDecimal InvoiceTotalPerTheMonth(Month month);

    Map<Integer, Month> MapOfDifferentMonths();

    Map<Integer,String> ReduceToMonth();

}
