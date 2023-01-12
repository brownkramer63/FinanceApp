package com.cydeo.service;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import java.math.BigDecimal;
import java.util.List;

public interface InvoiceService {

    InvoiceDTO findById(Long id);
    List<InvoiceDTO> listAllInvoicesByType(InvoiceType invoiceType);

    List<InvoiceDTO> listAllInvoice();

    void approve(Long id);

    InvoiceDTO create(InvoiceDTO invoiceDTO, InvoiceType invoiceType);

    boolean existsById(Long id);

    InvoiceDTO getNewPurchaseInvoice();

    InvoiceDTO getNewSalesInvoice();

    InvoiceDTO save(InvoiceDTO invoiceDTO, InvoiceType invoiceType);

    InvoiceDTO update(InvoiceDTO invoiceDTO, Long id);

    BigDecimal calculateProfitLoss(Long id);

    BigDecimal totalTaxOfInvoice(Long id);

    BigDecimal totalPriceOfInvoice(Long id);

    void deleteByInvoiceId(Long id);
    boolean checkIfStockIsEnough(Long id);

    void updateQuantityInStock(Long id);
    void updateQuantityAfterApproval(Long id);
    InvoiceDTO findByInvoiceId(Long id);
    BigDecimal totalCostOfApprovedInvoices();
    BigDecimal totalSalesOfApprovedInvoices();

    List<InvoiceDTO> listAllApprovedInvoices();



}