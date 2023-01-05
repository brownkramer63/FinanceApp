package com.cydeo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        var roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("Root User")) {
            response.sendRedirect("/companies/list");
        } else if (roles.contains("Admin")) {
            response.sendRedirect("/users/list");
        } else if (roles.contains("Manager")) {
            response.sendRedirect("/products/list");
        } else if (roles.contains("Employee")) {
            response.sendRedirect("/products/list");
        }
        //todo manager and employee need to land to dashboard page. Since dahsboard page is not active yet
        // both will land product list after page fix by @entisar
        //that will be /dashboard
    }
}
