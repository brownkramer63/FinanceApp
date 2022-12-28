package com.cydeo.repository;

import com.cydeo.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {


    //List<Company> findCompaniesOrderByCompanyStatusAndTitle(CompanyStatus companyStatus, String title);

    @Query("SELECT c FROM Company c WHERE c.id <> 1 ORDER BY c.companyStatus, c.title ASC ")
    List<Company> findCompaniesOrderByCompanyStatusAndTitle();
}
