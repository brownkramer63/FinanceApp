package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.security.SecurityService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.ReportingService;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportingServiceImpl implements ReportingService {

    private final InvoiceProductService invoiceProductService;
    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final InvoiceServiceImpl invoiceService;

    private final SecurityService securityService;

    private  final InvoiceProductRepository invoiceProductRepository;

    public ReportingServiceImpl(InvoiceProductService invoiceProductService, InvoiceRepository invoiceRepository, MapperUtil mapperUtil, InvoiceServiceImpl invoiceService, SecurityService securityService, InvoiceProductRepository invoiceProductRepository) {
        this.invoiceProductService = invoiceProductService;
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.invoiceService = invoiceService;
        this.securityService = securityService;
        this.invoiceProductRepository = invoiceProductRepository;
    }


    @Override
    public List<InvoiceProductDTO> generateReport() {
        return null;
    }

    @Override
    public Map<String, BigDecimal> profitLossDataMap() {
    Map<String, BigDecimal> profitPerAMonthMap= new HashMap<>();
    int counter=1;
   while (profitPerAMonthMap.size()< MapOfDifferentMonths().size()){
       profitPerAMonthMap.put((ReduceToMonth().get(counter)),InvoiceTotalPerTheMonth(MapOfDifferentMonths().get(counter)));
       counter++;
   }

        return profitPerAMonthMap;
    }

    @Override
    public BigDecimal InvoiceTotalPerTheMonth(Month month) {
        BigDecimal total=new BigDecimal(0);
        List<InvoiceProduct> monthOfInvoices= invoiceProductService.findAllByCompanyAndInvoiceTypeAndInvoiceStatus(
            mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company()), InvoiceType.SALES, InvoiceStatus.APPROVED).stream()
                .filter(invoiceProduct -> invoiceProduct.getInvoice().getDate().getMonth().equals(month)).collect(Collectors.toList());
        for (InvoiceProduct each:monthOfInvoices
        ) {
            total=total.add(each.getProfitLoss());
        }
        return total;
    }

    @Override
    public Map<Integer, Month> MapOfDifferentMonths() {
        List<InvoiceProduct> listofMonths = invoiceProductService.findAllByCompanyAndInvoiceTypeAndInvoiceStatus(
                mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company()), InvoiceType.SALES, InvoiceStatus.APPROVED);

        Map<Integer, Month> findings= new HashMap<>();
        int placement=1;
        for (InvoiceProduct each:listofMonths
             ) { //need to fix my validation here its messing up everything

         if (!findings.containsValue(each.getInvoice().getDate().getMonth())){
             findings.put(placement, each.getInvoice().getDate().getMonth());
             placement++;
         }
        }
        return findings;
    }

    public Map<Integer,String> ReduceToMonth(){
        int counter=1;
        DateTimeFormatter df= DateTimeFormatter.ofPattern("y MMMM");
        Map<Integer, Month> findingsToConvert= new HashMap<>();
        Map<Integer,String> convertedDates= new HashMap<>();
        List<InvoiceProduct> listofMonths = invoiceProductService.findAllByCompanyAndInvoiceTypeAndInvoiceStatus(
                mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company()), InvoiceType.SALES, InvoiceStatus.APPROVED);
        for (InvoiceProduct each:listofMonths
        ) {

            if (!findingsToConvert.containsValue(each.getInvoice().getDate().getMonth())){
                findingsToConvert.put(counter, each.getInvoice().getDate().getMonth());
                convertedDates.put(counter, each.getInvoice().getDate().format(df));
                counter++;
            }
        }
return convertedDates;
    }

}
