package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.security.SecurityService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {


    private final MapperUtil mapperUtil;

    private final InvoiceService invoiceService;

    private final InvoiceProductRepository invoiceProductRepository;
    private final SecurityService securityService;


    public InvoiceProductServiceImpl(MapperUtil mapperUtil, @Lazy InvoiceService invoiceService, InvoiceProductRepository invoiceProductRepository, SecurityService securityService) {

        this.mapperUtil = mapperUtil;

        this.invoiceService = invoiceService;

        this.invoiceProductRepository = invoiceProductRepository;
        this.securityService = securityService;
    }


    @Override
    public InvoiceProductDTO findById(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findById(id), new InvoiceProductDTO());
    }

    @Override
    public List<InvoiceProductDTO> findAllInvoiceProductByInvoiceId(Long id) {
        return invoiceProductRepository.findByInvoiceId(id).stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
                .collect(Collectors.toList());


    }


    @Override
    public InvoiceProductDTO addInvoiceProduct(Long id, InvoiceProductDTO invoiceProductDTO) {

        InvoiceDTO invoiceDTO = invoiceService.findById(id);


        if(invoiceProductDTO.getTotal()!=null){
            invoiceProductDTO.setInvoice(invoiceDTO);
            InvoiceProduct invoiceProduct = invoiceProductRepository.findById(invoiceProductDTO.getId()).orElseThrow();
            invoiceProduct.setProfitLoss(invoiceProductDTO.getProfitLoss());
            invoiceProduct.setRemainingQuantity(invoiceProductDTO.getRemainingQuantity());

            invoiceProductRepository.save(invoiceProduct);

            return mapperUtil.convert(invoiceProduct, new InvoiceProductDTO());


        }else{
            InvoiceProductDTO invoiceProduct = new InvoiceProductDTO();
            invoiceProduct.setInvoice(invoiceDTO);
            invoiceProduct.setPrice(invoiceProductDTO.getPrice());
            invoiceProduct.setQuantity(invoiceProductDTO.getQuantity());
            invoiceProduct.setTax(invoiceProductDTO.getTax());
            invoiceProduct.setProfitLoss(invoiceProductDTO.getProfitLoss());
            invoiceProduct.setProduct(invoiceProductDTO.getProduct());
            invoiceProduct.setTotal(invoiceProductDTO.getPrice()
                    .multiply(BigDecimal.valueOf(invoiceProductDTO.getQuantity()).multiply(BigDecimal.valueOf(invoiceProductDTO.getTax()).divide(BigDecimal.valueOf(100))))
                    .add(invoiceProductDTO.getPrice().multiply(BigDecimal.valueOf(invoiceProductDTO.getQuantity()))));

            InvoiceProduct invoiceProduct_res = mapperUtil.convert(invoiceProduct, new InvoiceProduct());

            InvoiceProduct invoiceProduct_con = invoiceProductRepository.save(invoiceProduct_res);

            return mapperUtil.convert(invoiceProduct_con,new InvoiceProductDTO());

        }

    }







    @Override
        public void removeInvoiceProduct (Long id){
           InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id).orElseThrow();

            invoiceProduct.setIsDeleted(true);
            invoiceProductRepository.save(invoiceProduct);

        }


    @Override
    public void delete(Long id) {

        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id).orElseThrow();
        invoiceProduct.setIsDeleted(true);
        invoiceProductRepository.save(invoiceProduct);
    }

    public void deleteProductByInvoiceId(Long id){
        UserDTO loggedInUser = securityService.getLoggedInUser();
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByInvoiceId(id);

        invoiceProductList.stream()
                .filter(invoiceProduct -> invoiceProduct.getInvoice().getCompany().getId()
                        .equals(loggedInUser.getCompany().getId())).forEach(invoiceProduct -> delete(invoiceProduct.getId()));
    }

    public List<InvoiceProductDTO> printInvoice(Long id){

        return invoiceProductRepository.findByInvoiceId(id)
                .stream().map(invoiceProduct -> {
                    InvoiceProductDTO invoiceProductDTO = mapperUtil.convert(invoiceProduct, new InvoiceProductDTO());
                    BigDecimal totalPrice = invoiceProduct.getPrice().multiply(BigDecimal.valueOf(invoiceProduct.getQuantity()));
                    Integer taxRate = invoiceProduct.getPrice().multiply(BigDecimal.valueOf(invoiceProduct.getTax()).divide(BigDecimal.valueOf(100))).intValue();
                    BigDecimal totalPriceWithTax = totalPrice.add(BigDecimal.valueOf(taxRate).multiply(BigDecimal.valueOf(invoiceProductDTO.getQuantity())));
                    invoiceProductDTO.setTotal(totalPriceWithTax);
                    return invoiceProductDTO;

                }).collect(Collectors.toList());
    }




}
