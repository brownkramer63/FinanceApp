package com.cydeo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String userName;
    @Column(nullable = false)
    private String passWord;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean enabled;
    @ManyToOne
    private Role role;
    @ManyToOne
    private Company company;





}
