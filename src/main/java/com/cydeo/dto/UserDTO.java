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
    @NotBlank(message = "Username is required field.")
    @Email(message = "Email must be in a valid email format")
    private String username;
    @NotBlank(message = "Password is required field.")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}", message =
    "should be at least be 4 characters long and needs to contain 1 capital letter, 1 small letter and 1 special character or number.")
    private String password;
    @NotNull(message = "Password should match)")
    private String confirmPassword;
    @NotBlank(message = "First name is required field" )
    @Size(max = 50, min = 2, message ="must be between 2-50 characters long")
    private String firstname;
    @NotBlank(message = "Last name is required field")
    @Size(max = 50, min = 2, message = "must be between 2-50 characters long")
    private String lastname;
    @NotBlank(message = "Phone is required field")
    @Pattern(regexp = "^(\\+\\d{1,2}\\s?)?1?\\-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$", message = "must be in any valid phone number format")
    private String phone;
    @NotNull(message = "Please select a role")
    private RoleDTO role;
    @NotNull(message = "Please select a company")
    private CompanyDTO company;
    private boolean isOnlyAdmin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        checkConfirmPassword();
    }

    public void checkConfirmPassword() {
        if (this.password == null || this.confirmPassword == null) {
            return;
        } else if (!this.password.equals(this.confirmPassword)) {
            this.confirmPassword = null;
        }
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public boolean isOnlyAdmin() {
        return isOnlyAdmin;
    }

    public void setOnlyAdmin(boolean onlyAdmin) {
        isOnlyAdmin = onlyAdmin;
    }

}
