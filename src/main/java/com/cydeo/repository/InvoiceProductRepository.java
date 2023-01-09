package com.cydeo.repository;

import com.cydeo.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {

    List<InvoiceProduct> findByInvoiceId(Long id);

    @Query(value = "SELECT * FROM invoice_products ip JOIN invoices i ON i.id =ip.invoice_id WHERE i.invoice_status ='APPROVED' ORDER BY i.date DESC ", nativeQuery = true)
    List<InvoiceProduct> retrieveAllBasedOnStatusOrderByDateDesc();


}
