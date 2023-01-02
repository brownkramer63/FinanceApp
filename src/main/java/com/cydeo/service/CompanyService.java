package com.cydeo.service;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.entity.Company;


import java.util.List;

public interface CompanyService {

    CompanyDTO findById(Long Id);

    List<CompanyDTO> listAllCompanies();

    List<CompanyDTO> listAllCompaniesOrderByStatusAndTitle();

    CompanyDTO save(CompanyDTO companyDTO);

    void activateDeactivateCompanyStatus(Long id);

    CompanyDTO update(CompanyDTO companyDTO, Long id);

    List<CompanyDTO> getCompaniesByLoggedInUser();




}
