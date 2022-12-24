package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.entity.Invoice;
import com.cydeo.mapper.InvoiceMapper;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;


    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public InvoiceDTO findById(Long id) {

        return invoiceMapper.convertToDTO(invoiceRepository.findById(id).get());


    }

    @Override
    public void delete(Long id) {
        invoiceRepository.deleteById(id);

    }
}