package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {


    private final MapperUtil mapperUtil;

    private final InvoiceService invoiceService;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceProductRepository invoiceProductRepository;

    public InvoiceProductServiceImpl(MapperUtil mapperUtil, @Lazy InvoiceService invoiceService, InvoiceRepository invoiceRepository, InvoiceProductRepository invoiceProductRepository) {

        this.mapperUtil = mapperUtil;
        this.invoiceService = invoiceService;
        this.invoiceRepository = invoiceRepository;
        this.invoiceProductRepository = invoiceProductRepository;
    }


    @Override
    public InvoiceProductDTO findById(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findById(id), new InvoiceProductDTO());
    }

    @Override
    public List<InvoiceProductDTO> findByInvoiceProductId(Long id) {
//        return invoiceProductRepository.fin(id).stream()
//                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
//                .collect(Collectors.toList());

        return null;
    }

    @Override
    public void update(InvoiceDTO invoiceDTO, Long id) {

        Optional<Invoice> invoice = invoiceRepository.findById(invoiceDTO.getId());
        Invoice convertedInvoice = mapperUtil.convert(invoiceDTO, new Invoice());

        if (invoice.isPresent()) {
            convertedInvoice.setInvoiceNo(convertedInvoice.getInvoiceNo());
            convertedInvoice.setClientVendor(convertedInvoice.getClientVendor());
            convertedInvoice.setDate(convertedInvoice.getDate());

        }
        invoiceRepository.save(invoice.orElseThrow());

    }

    @Override
    public void removeInvoiceProduct(Long id) {
        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id).orElseThrow();
        invoiceProduct.setIsDeleted(true);
        invoiceProductRepository.save(invoiceProduct);
    }

    @Override
    public void save(Long id, InvoiceProductDTO invoiceProductDTO) {
        InvoiceDTO invoiceDTO = invoiceService.findById(id);
        InvoiceProductDTO invoiceProductDTO1 = new InvoiceProductDTO();
        invoiceProductDTO1.setInvoice(invoiceDTO);
        invoiceProductDTO1.setProduct(invoiceProductDTO.getProduct());
        invoiceProductDTO1.setQuantity(invoiceProductDTO.getQuantity());
        invoiceProductDTO1.setPrice(invoiceDTO.getPrice());
        invoiceProductDTO1.setTax(invoiceDTO.getTax());
        invoiceProductRepository.save(mapperUtil.convert(invoiceProductDTO1, new InvoiceProduct()));


    }
}
