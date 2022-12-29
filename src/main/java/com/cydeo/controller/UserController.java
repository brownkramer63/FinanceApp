package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.CompanyService;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {


    private final CompanyService companyService;
    private final RoleService roleService;
    private final UserService userService;

    public UserController(CompanyService companyService, RoleService roleService, UserService userService) {
        this.companyService = companyService;
        this.roleService = roleService;
        this.userService = userService;
    }


    @GetMapping("/list")
    public String listUsers(Model model) {

        model.addAttribute("users", userService.getUsers());

        return "/user/user-list";
    }

    @PostMapping("/create")
    public String createUser(Model model) {

        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", roleService.listRoles());
        model.addAttribute("companies", companyService.listAllCompanies());

        return "redirect:/user/user-list";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.listRoles());
        model.addAttribute("companies", companyService.listAllCompanies());

        return "/user/user-update";
    }

    //public String deleteUser(){

}



