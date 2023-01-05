package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.enums.InvoiceType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {


    private final MapperUtil mapperUtil;

    private final InvoiceService invoiceService;

    private final InvoiceProductRepository invoiceProductRepository;

    public InvoiceProductServiceImpl(MapperUtil mapperUtil, @Lazy InvoiceService invoiceService, InvoiceProductRepository invoiceProductRepository) {

        this.mapperUtil = mapperUtil;

        this.invoiceService = invoiceService;

        this.invoiceProductRepository = invoiceProductRepository;
    }


    @Override
    public InvoiceProductDTO findById(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findById(id), new InvoiceProductDTO());
    }

    @Override
    public List<InvoiceProductDTO> findByInvoiceProductId(Long id) {
        return invoiceProductRepository.findByInvoiceId(id).stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
                .collect(Collectors.toList());


    }


    @Override
    public InvoiceProductDTO addInvoiceProduct(Long id, InvoiceProductDTO invoiceProductDTO) {
        InvoiceDTO invoiceDTO = invoiceService.findById(id);

        if(invoiceProductDTO.getTotal() != null){

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

        List<InvoiceProduct> invoiceProduct = invoiceProductRepository.findByInvoiceId(id);

        invoiceProduct.stream().forEach(invoiceProduct1 -> delete(invoiceProduct1.getId()));


    }


}
