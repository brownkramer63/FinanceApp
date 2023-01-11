package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import com.cydeo.enums.ProductUnit;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private int lowLimitAlert;
    private String name;

    @Enumerated(EnumType.STRING)
    private ProductUnit productUnit;
    private int quantityInStock;
    @ManyToOne (fetch = FetchType.LAZY)
    private Category category;

    @OneToMany(mappedBy = "product")
    List<InvoiceProduct > invoiceProduct;
}
