package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_deleted=false")
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private boolean enabled;
    @ManyToOne
    private Role role;
    @ManyToOne
    private Company company;





}
