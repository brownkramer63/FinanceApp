package com.cydeo.entity.common;

import com.cydeo.entity.User;
import com.cydeo.enums.CompanyStatus;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal(@Lazy User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        GrantedAuthority authority = new SimpleGrantedAuthority(this.user.getRole().getDescription());
        return List.of(authority);

        /*
          List<GrantedAuthority> authorityList = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(this.user.getRole().getDescription());
        authorityList.add(authority);
         */

    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getCompany().getCompanyStatus().equals(CompanyStatus.PASSIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
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
        return this.user.getFirstName() + " " + this.user.getLastName();
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
