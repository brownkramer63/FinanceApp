package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.Product;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.security.SecurityService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.ReportingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    private final InvoiceProductRepository invoiceProductRepository;

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
        Map<String, BigDecimal> profitPerAMonthMap = new HashMap<>();
        int counter = 1;
        while (profitPerAMonthMap.size() != MapOfDifferentMonths().size()) {
            profitPerAMonthMap.put((ReduceToMonth().get(counter)), InvoiceTotalPerTheMonth(MapOfDifferentMonths().get(counter)));
            counter++;
        }

        return profitPerAMonthMap;
    }

    @Override
    public BigDecimal InvoiceTotalPerTheMonth(Month month) {
        BigDecimal total = new BigDecimal(0);
        List<InvoiceProduct> monthOfInvoices = invoiceProductService.findAllByCompanyAndInvoiceTypeAndInvoiceStatus(
                        mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company()), InvoiceType.SALES, InvoiceStatus.APPROVED).stream()
                .filter(invoiceProduct -> invoiceProduct.getInvoice().getDate().getMonth().equals(month)).collect(Collectors.toList());
        for (InvoiceProduct each : monthOfInvoices
        ) {
//            total = total.add(each.getProfitLoss()); //new formula add here
            total=total.add(profitLossPerInvoice(each));
        }
        return total;

    }

    @Override
    public Map<Integer, Month> MapOfDifferentMonths() {
        List<InvoiceProduct> listofMonths = invoiceProductService.findAllByCompanyAndInvoiceTypeAndInvoiceStatus(
                mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company()), InvoiceType.SALES, InvoiceStatus.APPROVED);

        Map<Integer, Month> findings = new HashMap<>();
        int placement = 1;
        for (InvoiceProduct each : listofMonths
        ) {

            if (!findings.containsValue(each.getInvoice().getDate().getMonth())) {
                findings.put(placement, each.getInvoice().getDate().getMonth());
                placement++;
            }
        }
        return findings;
    }

    public Map<Integer, String> ReduceToMonth() {
        int counter = 1;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("y MMMM");
        Map<Integer, Month> findingsToConvert = new HashMap<>();
        Map<Integer, String> convertedDates = new HashMap<>();
        List<InvoiceProduct> listofMonths = invoiceProductService.findAllByCompanyAndInvoiceTypeAndInvoiceStatus(
                mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company()), InvoiceType.SALES, InvoiceStatus.APPROVED);
        for (InvoiceProduct each : listofMonths
        ) {

            if (!findingsToConvert.containsValue(each.getInvoice().getDate().getMonth())) {
                findingsToConvert.put(counter, each.getInvoice().getDate().getMonth());
                convertedDates.put(counter, each.getInvoice().getDate().format(df));
                counter++;
            }
        }
        return convertedDates;
    }


    @Override
    public BigDecimal profitLossPerInvoice(InvoiceProduct invoiceProduct) {
        Integer taxRate = invoiceProduct.getPrice().multiply(BigDecimal.valueOf(invoiceProduct.getTax()).divide(BigDecimal.valueOf(100))).intValue();
        BigDecimal totalPrice = invoiceProduct.getPrice().multiply(BigDecimal.valueOf(invoiceProduct.getQuantity()));
        BigDecimal totalPriceWithTax = totalPrice.add(BigDecimal.valueOf(taxRate).multiply(BigDecimal.valueOf(invoiceProduct.getQuantity())));
       //total sale price above
        int quantityOfInvoiceProduct= invoiceProduct.getQuantity();
        Product typeOfProduct= invoiceProduct.getProduct();
        List<InvoiceProduct> listOfPurchaseInvoicesOfProductType= invoiceProductRepository.findAll().stream().filter(invoiceProduct1 -> invoiceProduct1.getInvoice().getInvoiceType().getValue().equals("Purchase"))
                .filter(invoiceProduct1 -> invoiceProduct1.getProduct().equals(typeOfProduct)).collect(Collectors.toList());
        //list of purchase invoices of product type above now can find median cost
        int totalQuantityOfProduct= 0;
        BigDecimal totalPriceOfProduct=new BigDecimal(0);
        for (InvoiceProduct each:listOfPurchaseInvoicesOfProductType
             ) {
            totalQuantityOfProduct=totalQuantityOfProduct+each.getQuantity();
            totalPriceOfProduct=totalPriceOfProduct.add(each.getPrice());
        }
        BigDecimal medianProductCost=totalPriceOfProduct.divide(BigDecimal.valueOf(totalQuantityOfProduct));
//median cost found above
BigDecimal profitOrLossPerInvoice= totalPriceWithTax.subtract(medianProductCost.multiply(BigDecimal.valueOf(quantityOfInvoiceProduct)));

        return profitOrLossPerInvoice;
    }

    //same for this one
    @Override
    public List<InvoiceProductDTO> getStockReport() {
        return invoiceProductService.listAllBasedOnStatusOrderByDateDesc();
    }


}
