package com.cydeo.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
//
//    public CompanyDTO getCompany() {
//        return company;
//    }
//
//    public void setCompany(CompanyDTO company) {
//        this.company = company;
//    }

    public boolean isOnlyAdmin() {
        return isOnlyAdmin;
    }

    public void setOnlyAdmin(boolean onlyAdmin) {
        this.isOnlyAdmin = onlyAdmin;
    }
}
