package com.cydeo.controller;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.security.SecurityService;
import com.cydeo.service.CompanyService;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final CompanyService companyService;
    private final RoleService roleService;
    private final UserService userService;
    private final SecurityService securityService;

    public UserController(CompanyService companyService, RoleService roleService, UserService userService, SecurityService securityService) {
        this.companyService = companyService;
        this.roleService = roleService;
        this.userService = userService;
        this.securityService = securityService;
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
    public String insertUser(@Valid @ModelAttribute("newUser") UserDTO userDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors() || userService.isEmailAlreadyExists(userDTO)) {
            if (userService.isEmailAlreadyExists(userDTO)) {
                bindingResult.rejectValue("username", " ", "Username is already exists. Please enter a different username");
            }
            model.addAttribute("newUser", userDTO);
            model.addAttribute("userRoles", roleService.listRoles());
            model.addAttribute("companies", companyService.getCompaniesByLoggedInUserForRoot());
            return "user/user-create";
        }
        userService.save(userDTO);
        return "redirect:/users/list";
    }


    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Model model) {
        UserDTO loggedInUser = securityService.getLoggedInUser();
        UserDTO userDTO = userService.findById(id);

        if (userDTO.isOnlyAdmin()) {
            String text = "This user is the only admin of" + userDTO.getCompany().getTitle() +
                    "company. You can't change his/her company";
            redirectAttributes.addFlashAttribute("text", text);
            model.addAttribute("user", text);
        }
        model.addAttribute("user", userDTO);
        model.addAttribute("companies", companyService.getCompaniesByLoggedInUserForRoot());

        if (userDTO.getUsername().equals(loggedInUser.getUsername())) {

            model.addAttribute("userRoles", new RoleDTO(2L, "Admin"));
        }else{

            model.addAttribute("userRoles", roleService.listRoles());
        }
        log.info("User with id:" + userDTO.getId() + " and username: " + userDTO.getUsername() + " is ready to be updated");
        return "user/user-update";
    }

    @PostMapping("/update/{id}")
    public String insertUpdatedUser(@PathVariable("id") Long id, @Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {   //jan11, bug fix
            model.addAttribute("user", userDTO);
            model.addAttribute("userRoles", roleService.listRoles());
            model.addAttribute("companies", companyService.getCompaniesByLoggedInUserForRoot());
            return "user/user-update";
        }
        userDTO.setId(id);
        userService.update(userDTO);
        log.info("User updated: " + userDTO.getUsername());
        return "redirect:/users/list";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users/list";
    }

    @GetMapping()
    public String reset(UserDTO userDTO, Model model) {

        model.addAttribute("newUser", userDTO);

        return "user/user-create";
    }
}



