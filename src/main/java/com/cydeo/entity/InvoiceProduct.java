package com.cydeo.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "invoiceProducts")
@Getter
@Setter
@NoArgsConstructor
public class InvoiceProduct extends BaseEntity{

    private Integer quantity;
    private BigDecimal price;
    private Integer tax;
    private BigDecimal profitLoss;
    private Integer remainingQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;


}
