package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByIdAndIsDeleted(Long id, Boolean deleted);
    User findUserByUsernameAndIsDeleted(String username, Boolean deleted);

    Optional<User> findByUsername(String username);
}
