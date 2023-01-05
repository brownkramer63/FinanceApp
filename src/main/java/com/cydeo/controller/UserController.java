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

        model.addAttribute("users", userService.findAllByLoggedInUser());

        return "user/user-list";
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {

        model.addAttribute("newUser", new UserDTO());
        model.addAttribute("userRoles", roleService.listRoles());
        model.addAttribute("companies", companyService.getCompaniesByLoggedInUserForRoot());

        return "user/user-create";
    }

    @PostMapping("/create")
    public String insertUser(@ModelAttribute("user") UserDTO userDTO) {
        userService.save(userDTO);
        return "redirect:/users/list";
    }

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("userRoles", roleService.listRoles());
        model.addAttribute("companies", companyService.listAllCompanies());

        return "user/user-update";
    }

    @PostMapping("/update/{id}")
    public String insertUpdatedUser(@PathVariable("id") Long id, Model model, UserDTO userDTO) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("userRoles", roleService.listRoles());
        model.addAttribute("companies", companyService.listAllCompanies());
        userDTO.setId(id);
        userService.update(userDTO);
        return "redirect:/users/list";
    }

        @GetMapping("/delete/{id}")
        public String deleteUser (@PathVariable("id") Long id){
            userService.delete(id);
            return "redirect:/users/list";
        }
    }



