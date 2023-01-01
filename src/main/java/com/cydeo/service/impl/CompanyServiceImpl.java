package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.entity.Company;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
            log.info(" Company activated : " + companyDTO.getTitle());
        } else {
            companyDTO.setCompanyStatus(CompanyStatus.PASSIVE);
            companyRepository.save(mapperUtil.convert(companyDTO, new Company()));
            log.info(" Company Deactivated : " + companyDTO.getTitle());
        }

    }

    @Override
    public void update(CompanyDTO companyDTO, Long id) {

        Company company = companyRepository.findById(id).get();
        companyDTO.setId(id);
        companyDTO.setCompanyStatus(company.getCompanyStatus());
        companyDTO.getAddress().setId(company.getAddress().getId());
        companyRepository.save(mapperUtil.convert(companyDTO, new Company()));
        // TODO: 12/30/22 check return type of this update method


//        Company company = companyRepository.findById(companyDTO.getId()).get();
//        company.setTitle(companyDTO.getTitle());
//        company.setPhone(companyDTO.getPhone());
//        company.setWebsite(companyDTO.getWebsite());
//        company.setAddress(mapperUtil.convert(companyDTO.getAddress(), new Address()));
//        companyRepository.save(company);


    }
}
