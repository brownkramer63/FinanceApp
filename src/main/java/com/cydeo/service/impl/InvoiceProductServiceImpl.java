package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.mapper.InvoiceProductMapper;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.service.InvoiceProductService;
import org.springframework.stereotype.Service;


@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final InvoiceProductMapper invoiceProductMapper;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, InvoiceProductMapper invoiceProductMapper) {
        this.invoiceProductRepository = invoiceProductRepository;

        this.invoiceProductMapper = invoiceProductMapper;
    }

    @Override
    public void save(InvoiceProductDTO dto) {

        invoiceProductRepository.save(invoiceProductMapper.convertToEntity(dto));
    }

    @Override
    public void update(InvoiceProductDTO dto) {
        InvoiceProduct invoiceProduct = invoiceProductRepository.findByInvoiceId(dto.getId());

        InvoiceProduct invoiceProductConverted = invoiceProductMapper.convertToEntity(dto);

        invoiceProductConverted.setId(invoiceProduct.getId());

        invoiceProductConverted.setProduct(invoiceProduct.getProduct());

        invoiceProductRepository.save(invoiceProductConverted);

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public InvoiceProductDTO findById(Long id) {

        return invoiceProductMapper.convertToDTO(invoiceProductRepository.findByInvoiceId(id));
    }


}
