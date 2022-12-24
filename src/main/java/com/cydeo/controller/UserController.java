package com.cydeo.controller;

import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final CompanyService companyService;
    private final RoleService roleService;
    private final UserService userService;

    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @GetMapping("/list")
    public String listUsers(Model model){

        model.addAttribute("companies", companyService.listCompanies());
        model.addAttribute("roles", roleService.listRoles());
        model.addAttribute("users", userService.listUsers());

        return "/user/user-list";

    }

}
