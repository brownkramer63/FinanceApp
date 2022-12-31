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

    @GetMapping("/create")
    public String createUserForm(Model model) {

        model.addAttribute("newUser", new UserDTO());
        model.addAttribute("userRoles", roleService.listRoles());
        model.addAttribute("companies", companyService.listAllCompanies());

        return "/user/user-create";
    }
    @PostMapping("/create")
    public String insertUser(@ModelAttribute("user") UserDTO userDTO){
        userService.save(userDTO);
        return "redirect:/user/user-list";
    }


    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.listRoles());
        model.addAttribute("companies", companyService.listAllCompanies());

        return "/user/user-update";
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id")Long id) {
        userService.delete(id);
        return "redirect:/user/user-list";
    }
}



