package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserNameAndIsDeleted(String username, Boolean deleted);
    Optional<User> findByUserName(String username);
}
