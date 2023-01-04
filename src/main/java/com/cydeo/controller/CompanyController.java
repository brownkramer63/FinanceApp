package com.cydeo.controller;


import com.cydeo.dto.CompanyDTO;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @GetMapping("/list")
    public String listCompanies(Model model) {

        //should listAllCompanies  (sort by status and title (repository), id=1 will not be listed)
        model.addAttribute("companies", companyService.listAllCompaniesOrderByStatusAndTitle());

        return "company/company-list";
    }


    @GetMapping("/update/{id}")
    public String editCompany(@PathVariable("id") Long id, Model model) {

        model.addAttribute("company", companyService.findById(id));
        return "company/company-update"; //may use redirect after finishing update?
    }

    @PostMapping("/update/{id}")
    public String editCompany(@PathVariable("id") Long id, @ModelAttribute("company") @Valid CompanyDTO companyDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return "company/company-update";
        }

        companyService.update(companyDTO, id);

        return "redirect:/companies/list";

    }

    @GetMapping("/activate/{id}")
    public String activate(@PathVariable("id") Long id) {

        companyService.activateDeactivateCompanyStatus(id);

        return "redirect:/companies/list";
    }

    @GetMapping("/deactivate/{id}")
    public String deactivate(@PathVariable("id") Long id) {

        companyService.activateDeactivateCompanyStatus(id);

        return "redirect:/companies/list";
    }

    @GetMapping("/create")
    public String createCompany(Model model) {


        model.addAttribute("newCompany", new CompanyDTO());

        return "company/company-create";
    }

    @PostMapping("/create")
    public String insertCompany(@ModelAttribute("company") @Valid CompanyDTO companyDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return "company/company-create";
        }

        companyService.save(companyDTO);

        return "redirect:/companies/list";

    }




}
