package com.cydeo.entity.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class BaseEntityListener {

    @PrePersist
    public void onPrePersist(BaseEntity baseEntity) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        baseEntity.insertDateTime = LocalDateTime.now();
        baseEntity.lastUpdateDateTime = LocalDateTime.now();

        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
            baseEntity.insertUserId = principal.getId();
            baseEntity.lastUpdateUserId = principal.getId();
        }
    }

    @PreUpdate
    public void onPreUpdate(BaseEntity baseEntity) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        baseEntity.lastUpdateDateTime = LocalDateTime.now();

        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
            baseEntity.lastUpdateUserId = principal.getId();
        }
    }
}
