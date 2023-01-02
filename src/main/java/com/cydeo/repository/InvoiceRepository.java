package com.cydeo.repository;


import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {



    List<Invoice> findAllByCompanyAndInvoiceTypeOrderByDateDesc(Company company, InvoiceType invoiceType);

}
