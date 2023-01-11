package com.cydeo.entity;


import com.cydeo.entity.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;


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
@Where(clause = "is_deleted = false")
public class InvoiceProduct extends BaseEntity {

    private Integer quantity;
    private BigDecimal price;
    private Integer tax;
    private BigDecimal profitLoss;
    private Integer remainingQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    private BigDecimal total;


}
