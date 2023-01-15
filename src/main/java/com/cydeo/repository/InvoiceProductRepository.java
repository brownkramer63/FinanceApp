package com.cydeo.repository;

import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.Product;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {

    List<InvoiceProduct> findByInvoiceId(Long id);
    void deleteInvoiceProductsById(Long id);
    InvoiceProduct findInvoiceProductById(Long id);

    List<InvoiceProduct> findAllByInvoice_CompanyAndInvoice_InvoiceStatusAndInvoice_InvoiceType(Company company,InvoiceStatus invoiceStatus, InvoiceType invoiceType);
    @Query(value = "SELECT * FROM invoice_products ip JOIN invoices i ON i.id =ip.invoice_id WHERE i.invoice_status ='APPROVED' ORDER BY i.date DESC ", nativeQuery = true)
    List<InvoiceProduct> retrieveAllBasedOnStatusOrderByDateDesc();


}
