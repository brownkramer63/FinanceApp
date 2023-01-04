package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.security.SecurityService;
import com.cydeo.service.CompanyService;
import com.cydeo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;

    private final UserRepository userRepository;

    private final UserService userService;

    private final SecurityService securityService;

    public CompanyServiceImpl(CompanyRepository companyRepository, MapperUtil mapperUtil, UserRepository userRepository, UserService userService, SecurityService securityService) {
        this.companyRepository = companyRepository;
        this.mapperUtil = mapperUtil;
        this.userRepository = userRepository;
        this.userService = userService;
        this.securityService = securityService;
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
    public CompanyDTO save(CompanyDTO companyDTO) {

        Company company = mapperUtil.convert(companyDTO, new Company());
        company.setCompanyStatus(CompanyStatus.PASSIVE);
        companyRepository.save(company);

        return companyDTO;
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
    public CompanyDTO update(CompanyDTO companyDTO, Long id) {

        Company company = companyRepository.findById(id).get();
        companyDTO.setId(id);
        companyDTO.setCompanyStatus(company.getCompanyStatus());
        companyDTO.getAddress().setId(company.getAddress().getId());
        companyRepository.save(mapperUtil.convert(companyDTO, new Company()));
        // TODO: 12/30/22 check return type of this update method

        return companyDTO;

    }

    @Override
    public List<CompanyDTO> getCompaniesByLoggedInUserForRoot() {
        //if currentUser = "Root User", all companies except Cydeo
        //else only his/her company

        User user = mapperUtil.convert(securityService.getLoggedInUser(), new User());

        if (user.getRole().getDescription().equals("Root User")) {
            List<Company> companyList = companyRepository.findCompaniesOrderByCompanyTitle();

            return companyList.stream().map(company -> mapperUtil.convert(company, new CompanyDTO())).collect(Collectors.toList());

        } else {
            Company company = user.getCompany();
            CompanyDTO companyDTO = mapperUtil.convert(company, new CompanyDTO());
            return Arrays.asList(companyDTO);
        }
    }

    @Override
    public CompanyDTO getCompanyByLoggedInUser() {

        return securityService.getLoggedInUser().getCompany();

    }

}
