package com.cydeo.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String userName;
    private String passWord;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String phone;
    private RoleDTO role;
    //private CompanyDTO company;
    private boolean isOnlyAdmin;


}
