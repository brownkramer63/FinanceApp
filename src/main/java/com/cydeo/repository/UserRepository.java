package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    User findUserByUsernameAndIsDeleted(String username, Boolean deleted);
    //User findUserByUsername(String username);
    Optional<User> findByUsername(String username);
    List <User> findAllByCompanyId(Long id);
    @Query("select u from User u join Role r on u.role.id = r.id where u.role.description = ?1 order by u.company.title, u.role.description")
    List<User> getAllByOrderByCompanyAndRole(String description);
    @Query("select u from User u order by u.company.title, u.role.description")
    List<User> getAllByCompanyAndRole();
    User findUserByCompanyId(Long id);

}
