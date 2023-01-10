package com.cydeo.repository;

import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {

    List<InvoiceProduct> findByInvoiceId(Long id);

    List<InvoiceProduct> findAllByInvoice_CompanyAndInvoice_InvoiceStatusAndInvoice_InvoiceType(Company company,InvoiceStatus invoiceStatus, InvoiceType invoiceType);
}
