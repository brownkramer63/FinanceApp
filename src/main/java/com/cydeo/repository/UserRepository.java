package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByIdAndIsDeleted(Long id, Boolean deleted);
    User findUserByUserNameAndIsDeleted(String username, Boolean deleted);
}
