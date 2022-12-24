package com.cydeo.entity;


import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import com.sun.xml.bind.v2.TODO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
public class Invoice extends BaseEntity{


    private String invoiceNo;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;
    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;
    @Column(columnDefinition = "DATE")
    private LocalDate date;

  // TODO waiting for others to complete

    @ManyToOne(fetch = FetchType.LAZY)
    private ClientVendor clientVendor;
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;







}
