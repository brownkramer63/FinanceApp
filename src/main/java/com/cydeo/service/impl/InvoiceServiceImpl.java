package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final InvoiceProductService invoiceProductService;

    private final CompanyService companyService;



    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil, InvoiceProductService invoiceProductService, CompanyService companyService) {
        this.invoiceRepository = invoiceRepository;

        this.mapperUtil = mapperUtil;
        this.invoiceProductService = invoiceProductService;
        this.companyService = companyService;

    }

    @Override
    public InvoiceDTO findById(Long id) {

        return mapperUtil.convert(invoiceRepository.findById(id), new InvoiceDTO());

    }

    @Override
    public void delete(Long id) {
        Optional<Invoice> getInvoice = invoiceRepository.findById(id);

        if(getInvoice.isPresent()){
            getInvoice.get().setIsDeleted(true);
            invoiceRepository.save(getInvoice.get());


        }
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
    public List<InvoiceDTO> listAllInvoicesByType(InvoiceType invoiceType) {
        //TODO @nihano add method to the companyService
       // CompanyDTO companyDTO = companyService.findCompanyByUser();
        CompanyDTO companyDTO = companyService.findById(1l);


      List<Invoice> invoiceListByType = invoiceRepository.findAllByCompanyAndInvoiceTypeOrderByDateDesc(mapperUtil.convert(companyDTO, new Company()),invoiceType)
              .stream().sorted(Comparator.comparing(Invoice::getInvoiceNo)).collect(Collectors.toList());

      return invoiceDtoList(invoiceListByType);
    }

    @Override
    public InvoiceDTO getNewPurchaseInvoice() {

       InvoiceDTO invoiceDTO = new InvoiceDTO();
       invoiceDTO.setDate(LocalDate.now());
       invoiceDTO.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
       invoiceDTO.setInvoiceNumber(generateInvoiceNumber(InvoiceType.PURCHASE));

       return invoiceDTO;

    }


    private String generateInvoiceNumber(InvoiceType invoiceType){
        //TODO @nihano add method to the companyService

       // CompanyDTO companyDTO = companyService.findCompanyByUser();
        CompanyDTO companyDTO = companyService.findById(1l);
        Company company = mapperUtil.convert(companyDTO, new Company());

        Invoice invoice = invoiceRepository.findAllByCompanyAndInvoiceTypeOrderByDateDesc(company, invoiceType).stream()
                .max(Comparator.comparing(Invoice::getInvoiceNo)).orElseThrow();

        String invoiceNum = invoice.getInvoiceNo();
        int res = Integer.parseInt(invoiceNum.substring(2)) + 1;
        String numberPart = String.format("%03d", res);
        String generatedNumber = invoiceNum.substring(0, 2) + numberPart;
        return generatedNumber;
    }


    private List<InvoiceDTO> invoiceDtoList(List<Invoice> invoiceList){

        List<InvoiceDTO> invoiceDTOList = invoiceList.stream()
                .map(invoice ->mapperUtil.convert(invoice, new InvoiceDTO()))
                .collect(Collectors.toList());

        List<InvoiceDTO> dtoList = invoiceDTOList.stream()
                .map(invoiceDTO -> {
                    List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.findByInvoiceProductId(invoiceDTO.getId());

                    BigDecimal totalPrice = invoiceProductDTOList.stream().map(invoiceProductDTO ->
                            invoiceProductDTO.getPrice().multiply(BigDecimal.valueOf(invoiceProductDTO.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
                    invoiceDTO.setPrice(totalPrice);

                    BigDecimal totalTax = invoiceProductDTOList.stream().map(invoiceProductDTO ->
                            invoiceProductDTO.getPrice().multiply(BigDecimal.valueOf(invoiceProductDTO.getTax())).divide(BigDecimal.valueOf(100)))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    invoiceDTO.setTax(totalTax.intValueExact());

                    BigDecimal totalPriceWithTax = totalPrice.add(totalTax);
                    invoiceDTO.setTotal(totalPriceWithTax);
                    return invoiceDTO;
                }
                ).collect(Collectors.toList());

        return dtoList;

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