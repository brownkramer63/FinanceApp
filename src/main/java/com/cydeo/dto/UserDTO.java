package com.cydeo.dto;

import lombok.*;
import javax.validation.constraints.*;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class UserDTO {

    private Long id;
    @NotBlank
    @Email
    private String username;
    @NotBlank
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}")
    private String password;
    @NotNull
    private String confirmPassword;
    @NotBlank
    @Size(max = 15, min = 2)
    private String firstname;
    @NotBlank
    @Size(max = 15, min = 2)
    private String lastname;
    @NotBlank
    @Pattern(regexp = "^\\d{10}$")
    private String phone;
    private RoleDTO role;
    private CompanyDTO company;
    private boolean isOnlyAdmin;


}
