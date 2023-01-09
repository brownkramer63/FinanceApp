package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.ReportingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportingServiceImpl implements ReportingService {

    private final InvoiceProductService invoiceProductService;
    private final InvoiceRepository invoiceRepository;

    private InvoiceProductRepository invoiceProductRepository;

    public ReportingServiceImpl(InvoiceProductService invoiceProductService, InvoiceRepository invoiceRepository, InvoiceProductRepository invoiceProductRepository) {
        this.invoiceProductService = invoiceProductService;
        this.invoiceRepository = invoiceRepository;
        this.invoiceProductRepository = invoiceProductRepository;
    }


    @Override
    public List<InvoiceProductDTO> generateReport() {
        return null;
    }

    @Override
    public Map<LocalDate, BigDecimal> profitLossDataMap() {
    Map<LocalDate, BigDecimal> profitPerAMonthMap= new HashMap<>();
    int counter=1;
   while (profitPerAMonthMap.size()< MapOfDifferentMonths().size()){
       profitPerAMonthMap.put(MapOfDifferentMonths().get(counter),InvoiceTotalPerTheMonth(MapOfDifferentMonths().get(counter)));
       counter++;
   }

        return profitPerAMonthMap;
    }

    @Override
    public BigDecimal InvoiceTotalPerTheMonth(LocalDate month) {
        BigDecimal total=new BigDecimal(0);
        List<InvoiceProduct> monthofinvoices= invoiceProductRepository.findAll().stream().filter(invoiceProduct ->
                invoiceProduct.getLastUpdateDateTime().getMonth().equals(month.getMonth())).collect(Collectors.toList());
        for (InvoiceProduct each:monthofinvoices
             ) {
          total=total.add(each.getProfitLoss());
        }
        return total;
    }

    @Override
    public Map<Integer, LocalDate> MapOfDifferentMonths() {
        List<InvoiceProduct> listofMonths = invoiceProductRepository.findAll();
        Map<Integer, LocalDate> findings= new HashMap<>();
        int placement=1;
        for (InvoiceProduct each:listofMonths
             ) { //need to fix my validation here its messing up everything

         if (!findings.containsValue(each.getInvoice().getDate().getMonth())){
             findings.put(placement, LocalDate.from(each.getInvoice().getDate().getMonth()));
             placement++;
         }
        }
        return findings;
    }


}
