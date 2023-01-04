package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final InvoiceProductService invoiceProductService;

    private final CompanyService companyService;
    private final InvoiceProductRepository invoiceProductRepository;



    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil, InvoiceProductService invoiceProductService, CompanyService companyService, InvoiceProductRepository invoiceProductRepository) {
        this.invoiceRepository = invoiceRepository;

        this.mapperUtil = mapperUtil;
        this.invoiceProductService = invoiceProductService;
        this.companyService = companyService;


        this.invoiceProductRepository = invoiceProductRepository;
    }

    @Override
    public InvoiceDTO findById(Long id) {

        return mapperUtil.convert(invoiceRepository.findById(id), new InvoiceDTO());

    }

    @Override
    public List<InvoiceDTO> listAllInvoicesByType(InvoiceType invoiceType) {

        CompanyDTO companyDTO = companyService.getCompaniesByLoggedInUser().get(0);

        log.info("company name for invoices" + companyDTO.getTitle());
        Company company = mapperUtil.convert(companyDTO, new Company());

        List<InvoiceDTO> invoiceListByType =

                invoiceRepository.findAllByCompanyAndInvoiceType(company ,invoiceType)
                        .stream()
                        .map(invoice -> {
                            InvoiceDTO invoiceDTO = mapperUtil.convert(invoice, new InvoiceDTO());
                            invoiceDTO.setTax(totalTaxOfInvoice(invoiceDTO.getId()).intValueExact());
                            invoiceDTO.setPrice(totalPriceOfInvoice(invoice.getId()));
                            invoiceDTO.setTotal(totalPriceOfInvoice(invoice.getId()).subtract(totalTaxOfInvoice(invoice.getId())));


                            return invoiceDTO;

                        })
                        .sorted(Comparator.comparing(InvoiceDTO::getInvoiceNo))
                        .collect(Collectors.toList());




        return invoiceListByType;
    }


    private  BigDecimal totalTaxOfInvoice(Long id){

        List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.findByInvoiceProductId(id);

        if(invoiceProductDTOList != null){

           invoiceProductDTOList.stream().map(invoiceProductDTO -> {

               BigDecimal price = invoiceProductDTO.getPrice().multiply(BigDecimal.valueOf(invoiceProductDTO.getQuantity()));
               BigDecimal tax = BigDecimal.valueOf(invoiceProductDTO.getTax()).divide(BigDecimal.valueOf(100));
               return price.multiply(tax);
                   }).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return BigDecimal.ZERO;

    }



    private  BigDecimal totalPriceOfInvoice(Long id){

        List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.findByInvoiceProductId(id);

        if(invoiceProductDTOList != null){

            BigDecimal price =  invoiceProductDTOList.stream().map(invoiceProductDTO -> {

                BigDecimal priceWithoutTax = invoiceProductDTO.getPrice().multiply(BigDecimal.valueOf(invoiceProductDTO.getQuantity()));

                return priceWithoutTax;
            }).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal tax = totalTaxOfInvoice(id);
            BigDecimal totalPrice = tax.add(price);
            return totalPrice;
        }
        return BigDecimal.ZERO;
    }


    @Override
    public BigDecimal calculateProfitLoss(Long id) {
        return null;
    }



    @Override
    public void delete(Long id) {
       Invoice invoice = invoiceRepository.findById(id).orElseThrow();


            invoice.setIsDeleted(true);
            invoiceRepository.save(invoice);
            invoiceProductService.delete(id);

        }


    @Override
    public List<InvoiceDTO> listAllInvoice() {
        return invoiceRepository.findAll().stream()
                .map(invoice -> mapperUtil.convert(invoice, new InvoiceDTO()))
                .collect(Collectors.toList());

    }



    @Override
    public void approve(Long id) {

        Optional<Invoice> invoice = invoiceRepository.findById(id);

        if(invoice.isPresent()){
            invoice.get().setInvoiceStatus(InvoiceStatus.APPROVED);
            invoiceRepository.save(invoice.get());
        }
    }


    @Override
    public InvoiceDTO getNewPurchaseInvoice() {

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setDate(LocalDate.now());
        invoiceDTO.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        invoiceDTO.setInvoiceNo(generateInvoiceNumber(InvoiceType.PURCHASE));

        return invoiceDTO;

    }

    @Override
    public InvoiceDTO getNewSalesInvoice() {

            InvoiceDTO invoiceDTO = new InvoiceDTO();
            invoiceDTO.setDate(LocalDate.now());
            invoiceDTO.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
            invoiceDTO.setInvoiceNo(generateInvoiceNumber(InvoiceType.SALES));

            return invoiceDTO;
    }

    @Override
    public InvoiceDTO save(InvoiceDTO invoiceDTO, InvoiceType invoiceType) {

        CompanyDTO company = companyService.getCompaniesByLoggedInUser().get(0);
        invoiceDTO.setCompanyDTO(mapperUtil.convert(company, new CompanyDTO()));
        invoiceDTO.setInvoiceType(invoiceType);
        invoiceDTO.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        invoiceRepository.save(mapperUtil.convert(invoiceDTO, new Invoice()));

        return invoiceDTO;

    }




    private String generateInvoiceNumber(InvoiceType invoiceType){



        CompanyDTO companyDTO =  companyService.getCompaniesByLoggedInUser().get(0);
        Company company = mapperUtil.convert(companyDTO, new Company());

        Invoice invoice = invoiceRepository.findAllByCompanyAndInvoiceType(company, invoiceType).stream()
                .max(Comparator.comparing(Invoice::getInvoiceNo)).orElseThrow();

        String invoiceNum = invoice.getInvoiceNo();
        int res = Integer.parseInt(invoiceNum.substring(2)) + 1;
        String numberPart = String.format("%03d", res);
        String generatedNumber = invoiceNum.substring(0, 2) + numberPart;
        return generatedNumber;
    }



    @Override
    public InvoiceDTO create(InvoiceDTO invoiceDTO, InvoiceType invoiceType) {

        Company company = invoiceRepository.findAll().stream()
                .findAny().get().getCompany();
        invoiceDTO.setCompanyDTO(mapperUtil.convert(company, new CompanyDTO()));
        invoiceDTO.setInvoiceType(invoiceType);
        invoiceDTO.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        invoiceRepository.save(mapperUtil.convert(invoiceDTO, new Invoice()));

        return invoiceDTO;

    }



}