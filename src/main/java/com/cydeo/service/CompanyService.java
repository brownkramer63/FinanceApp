package com.cydeo.service;

import com.cydeo.dto.CompanyDTO;


import java.util.List;

public interface CompanyService {

    CompanyDTO findById(Long Id);

    List<CompanyDTO> listAllCompanies();

    List<CompanyDTO> listAllCompaniesOrderByStatusAndTitle();

    void save(CompanyDTO companyDTO);

    void activateDeactivateCompanyStatus(Long id);

    void update(CompanyDTO companyDTO);


}
