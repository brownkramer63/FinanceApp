package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.InvoiceDTO;
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
import com.cydeo.repository.ProductRepository;
import com.cydeo.security.SecurityService;
import com.cydeo.service.CompanyService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private final SecurityService securityService;

    private final ProductRepository productRepository;



    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil, InvoiceProductService invoiceProductService, CompanyService companyService, InvoiceProductRepository invoiceProductRepository, SecurityService securityService, ProductRepository productRepository) {
        this.invoiceRepository = invoiceRepository;

        this.mapperUtil = mapperUtil;
        this.invoiceProductService = invoiceProductService;
        this.companyService = companyService;


        this.invoiceProductRepository = invoiceProductRepository;
        this.securityService = securityService;
        this.productRepository = productRepository;
    }

    @Override
    public InvoiceDTO findById(Long id) {

        return mapperUtil.convert(invoiceRepository.findById(id), new InvoiceDTO());

    }

    @Override
    public List<InvoiceDTO> listAllInvoicesByType(InvoiceType invoiceType) {

        CompanyDTO companyDTO = companyService.getCompanyByLoggedInUser();

        log.info("company name for invoices" + companyDTO.getTitle());
        Company company = mapperUtil.convert(companyDTO, new Company());

        List<InvoiceDTO> invoiceListByType =

                invoiceRepository.findAllByCompanyAndInvoiceType(company ,invoiceType)
                        .stream()
                        .map(invoice -> {
                            InvoiceDTO invoiceDTO = mapperUtil.convert(invoice, new InvoiceDTO());
                            invoiceDTO.setTax(totalTaxOfInvoice(invoiceDTO.getId()).intValue());
                            invoiceDTO.setPrice(totalPriceOfInvoice(invoiceDTO.getId()).subtract(totalTaxOfInvoice(invoiceDTO.getId())));
                            invoiceDTO.setTotal(totalPriceOfInvoice(invoiceDTO.getId()).subtract(totalTaxOfInvoice(invoiceDTO.getId())).add(totalTaxOfInvoice(invoiceDTO.getId())));


                            return invoiceDTO;

                        })
                        .sorted(Comparator.comparing(InvoiceDTO::getInvoiceNo))
                        .collect(Collectors.toList());




        return invoiceListByType;
    }

    public BigDecimal totalTaxOfInvoice(Long id){
        List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.findAllInvoiceProductByInvoiceId(id);

        if(invoiceProductDTOList != null){

            return invoiceProductDTOList.stream().map(invoiceProductDto -> {
                BigDecimal price = invoiceProductDto.getPrice();
                Integer quantityOfProduct = invoiceProductDto.getQuantity();
                price = price.multiply(BigDecimal.valueOf(quantityOfProduct));
                BigDecimal tax = BigDecimal.valueOf(invoiceProductDto.getTax()).divide(BigDecimal.valueOf(100));
                return price.multiply(tax);
            }).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }










    public  BigDecimal totalPriceOfInvoice(Long id){

        List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.findAllInvoiceProductByInvoiceId(id);

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
            // stream invoice
            // for each invoice product we need quantity and remaining quantity
            // q in stock = q in stock + quantity
            // rem q -> q in stock
            List<InvoiceProductDTO> invoiceProducts = invoiceProductService.findAllInvoiceProductByInvoiceId(id);
                           invoiceProducts .forEach(invoiceProductDTO -> {
                           Integer remainingQuantity = invoiceProductDTO.getQuantity() + invoiceProductDTO.getProduct().getQuantityInStock();

                           invoiceProductDTO.setRemainingQuantity(remainingQuantity);
                          invoiceProductDTO.getProduct().setQuantityInStock(remainingQuantity);



                               ;
                           });


            invoice.get().setDate(LocalDate.now());
            invoiceRepository.save(invoice.get());
        }



    }


    public void approvePurchaseInvoice(Long id){

        //
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

        CompanyDTO company = companyService.getCompanyByLoggedInUser();
        invoiceDTO.setCompanyDTO(mapperUtil.convert(company, new CompanyDTO()));
        invoiceDTO.setInvoiceType(invoiceType);
        invoiceDTO.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        Invoice invoice = invoiceRepository.save(mapperUtil.convert(invoiceDTO, new Invoice()));

        return mapperUtil.convert(invoice, new InvoiceDTO());

    }




    private String generateInvoiceNumber(InvoiceType invoiceType){



        CompanyDTO companyDTO =  companyService.getCompanyByLoggedInUser();
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

        invoiceDTO.setCompanyDTO(companyService.getCompanyByLoggedInUser());
        invoiceDTO.setInvoiceType(invoiceType);
        invoiceDTO.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        invoiceRepository.save(mapperUtil.convert(invoiceDTO, new Invoice()));

        return invoiceDTO;

    }

    @Override
    public InvoiceDTO update(InvoiceDTO invoiceDTO, Long id) {


        Invoice invoice = invoiceRepository.findById(id).orElseThrow();
        Invoice convertedInvoice = mapperUtil.convert(invoiceDTO, new Invoice());

         convertedInvoice.setInvoiceNo(convertedInvoice.getInvoiceNo());
         invoice.setClientVendor(convertedInvoice.getClientVendor());
         convertedInvoice.setDate(convertedInvoice.getDate());
        invoiceRepository.save(invoice);
        return invoiceDTO;



        }




    public void deleteByInvoiceId(Long id){

        Invoice invoice = invoiceRepository.findById(id).orElseThrow();
        invoice.setIsDeleted(true);
        invoiceRepository.save(invoice);
        invoiceProductService.deleteProductByInvoiceId(id);

    }


    public void updateQuantityInStock(Long id){

        Invoice invoice = invoiceRepository.findById(id).orElseThrow();
        List<InvoiceProduct> invoiceProduct = invoiceProductRepository.findByInvoiceId(id);
        if(invoice.getInvoiceType().equals(InvoiceType.PURCHASE)) {
            for (InvoiceProduct each : invoiceProduct) {
                Product product = productRepository.findById(each.getProduct().getId()).orElseThrow();
                product.setQuantityInStock(product.getQuantityInStock() + each.getQuantity());
                productRepository.save(product);
            }
        }
        else {
            for (InvoiceProduct each : invoiceProduct) {
                Product product = productRepository.findById(each.getProduct().getId()).orElseThrow();
                product.setQuantityInStock(product.getQuantityInStock() - each.getQuantity());
                productRepository.save(product);
            }
        }

    }

    public void updateQuantityAfterApproval(Long id){
        Invoice invoice = invoiceRepository.findById(id).orElseThrow();
        List<InvoiceProduct> invoiceProduct = invoiceProductRepository.findByInvoiceId(id);
        if(invoice.getInvoiceType().equals(InvoiceType.PURCHASE) && invoice.getInvoiceStatus().equals(InvoiceStatus.APPROVED)){
            for(InvoiceProduct each: invoiceProduct){

                InvoiceProduct invoiceProduct1 = invoiceProductRepository.findById(each.getId()).orElseThrow();
                invoiceProduct1.setRemainingQuantity(invoiceProduct1.getQuantity());
                invoiceProduct1.setProfitLoss(BigDecimal.valueOf(10));
                invoiceProductRepository.save(invoiceProduct1);
                Product product = productRepository.findById(each.getProduct().getId()).orElseThrow();
                product.setQuantityInStock(invoiceProduct1.getRemainingQuantity());

                }
            }else if(invoice.getInvoiceType().equals(InvoiceType.SALES) && invoice.getInvoiceStatus().equals(InvoiceStatus.APPROVED)){
                List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByInvoiceId(id);

                for(InvoiceProduct each: invoiceProductList){

                InvoiceProduct invoiceProduct1 = invoiceProductRepository.findById(each.getId()).orElseThrow();
                invoiceProduct1.setRemainingQuantity(invoiceProduct1.getProduct().getQuantityInStock() - invoiceProduct1.getQuantity());
                invoiceProduct1.setProfitLoss(BigDecimal.valueOf(10));
                invoiceProductRepository.save(invoiceProduct1);
                Product product = productRepository.findById(each.getProduct().getId()).orElseThrow();
                product.setQuantityInStock(invoiceProduct1.getRemainingQuantity());

            }

            }
        }


    public boolean checkIfStockIsEnough(InvoiceProductDTO invoiceProductDTO, Long id){


        InvoiceProduct invoiceProducts = invoiceProductRepository.findInvoiceProductById(id);

            if(invoiceProductDTO.getQuantity() > invoiceProducts.getProduct().getQuantityInStock()){
                return false;
            }


        return true;
    }

    public List<InvoiceProductDTO> printInvoice(Long id){


        return null;
//        return invoiceProductRepository.findByInvoiceId(id)
//                .stream().map(invoiceProduct -> {
//
//
//
//
//                }).collect(Collectors.toList());
    }


}
