package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;


    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil) {
        this.invoiceRepository = invoiceRepository;

        this.mapperUtil = mapperUtil;
    }

    @Override
    public InvoiceDTO findById(Long id) {

        return mapperUtil.convert(invoiceRepository.findById(id), new InvoiceDTO());

    }

    @Override
    public void delete(Long id) {
        invoiceRepository.deleteById(id);

    }
}