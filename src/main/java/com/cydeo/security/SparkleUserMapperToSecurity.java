package com.cydeo.security;

import com.cydeo.entity.User;
import com.cydeo.enums.CompanyStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Slf4j
public class SparkleUserMapperToSecurity implements UserDetails {

    private final User user;

    public SparkleUserMapperToSecurity(@Lazy User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        GrantedAuthority authority = new SimpleGrantedAuthority(this.user.getRole().getDescription());
        log.info("logged in user role is " + authority.getAuthority());
        return List.of(authority);


    }

    @Override
    public String getPassword() {
        var pass = user.getPassword();
        log.info("password of user : " + pass);
        return pass;
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getCompany().getCompanyStatus().equals(CompanyStatus.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public Long getId() {
        return this.user.getId();
    }


    /**
     * @return logged-in user UI dropdown menu item as firstname and lastname
     */
    public String getFullNameForProfile() {
        return this.user.getFirstname() + " " + this.user.getLastname();
    }

    /**
     * This method is defined to show logged-in user's company title for simplicity
     *
     * @return The title of logged-in user's Company in String
     */
    public String getCompanyTitleForProfile() {
        return this.user.getCompany().getTitle().toUpperCase();
    }
}
