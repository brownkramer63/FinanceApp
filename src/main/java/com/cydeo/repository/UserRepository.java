package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    User findUserByUsernameAndIsDeleted(String username, Boolean deleted);
    Optional<User> findByUsername(String username);
    List <User> findAllByCompanyId(Long id);
    List<User> findAllByRole_DescriptionOrderByCompany(String description);
    List<User> findUsersByRole_Description(String description);
    @Query(value = "select * from users" +
            "join companies c on c.id = users.company_id" +
            "join roles r on r.id = users.role_id" +
            "where description = ?! and company_id=?!", nativeQuery = true)
    List<User> findAllByCompanyIdAndRoleContains(Long id, String description);

}
