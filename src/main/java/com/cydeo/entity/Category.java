package com.cydeo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "categories")
@Getter
@Setter
public class Category extends BaseEntity{

    private String description;
    @ManyToOne
    private Company company;

}
