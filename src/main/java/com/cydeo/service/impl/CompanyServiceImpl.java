package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.entity.Address;
import com.cydeo.entity.Company;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;

    public CompanyServiceImpl(CompanyRepository companyRepository, MapperUtil mapperUtil) {
        this.companyRepository = companyRepository;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public CompanyDTO findById(Long Id) {

        Company company = companyRepository.findById(Id).get();
        return mapperUtil.convert(company, new CompanyDTO());
    }

    @Override
    public List<CompanyDTO> listAllCompanies() {

        List<Company> companyList = companyRepository.findAll();
        return companyList.stream().map(company -> mapperUtil.convert(company, new CompanyDTO())).collect(Collectors.toList());
    }

    @Override
    public List<CompanyDTO> listAllCompaniesOrderByStatusAndTitle() {
        List<Company> companyList = companyRepository.findCompaniesOrderByCompanyStatusAndTitle();
        return companyList.stream().map(company -> mapperUtil.convert(company, new CompanyDTO())).collect(Collectors.toList());
    }


    @Override
    public void save(CompanyDTO companyDTO) {

        companyRepository.save(mapperUtil.convert(companyDTO, new Company()));
    }

    @Override
    public void activateDeactivateCompanyStatus(Long id) {

        CompanyDTO companyDTO = findById(id);
        if (companyDTO.getCompanyStatus().getValue().equals("Passive")) {
            companyDTO.setCompanyStatus(CompanyStatus.ACTIVE);
            companyRepository.save(mapperUtil.convert(companyDTO, new Company()));
        } else {
            companyDTO.setCompanyStatus(CompanyStatus.PASSIVE);
            companyRepository.save(mapperUtil.convert(companyDTO, new Company()));
        }

    }

    @Override
    public void update(CompanyDTO companyDTO) {

        Company company = companyRepository.findByTitle(companyDTO.getTitle());
        company.setTitle(companyDTO.getTitle());
        company.setPhone(companyDTO.getPhone());
        company.setWebsite(companyDTO.getWebsite());
        company.setAddress(mapperUtil.convert(companyDTO.getAddress(), new Address()));
        companyRepository.save(company);

    }


}
