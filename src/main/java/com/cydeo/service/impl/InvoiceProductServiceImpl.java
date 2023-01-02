package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.service.InvoiceProductService;
import org.springframework.stereotype.Service;


@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {


    private final MapperUtil mapperUtil;

    public InvoiceProductServiceImpl( MapperUtil mapperUtil) {

        this.mapperUtil = mapperUtil;
    }


    @Override
    public void save(InvoiceDTO dto) {


    }

    @Override
    public void update(InvoiceProductDTO dto) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public InvoiceProductDTO findById(Long id) {
        return null;
    }
}
