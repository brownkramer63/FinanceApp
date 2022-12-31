package com.cydeo.controller;

import com.cydeo.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()

public class DashboardController {

    @GetMapping("/dashboard")
    public String navigateToDashboard(Model model){

        return "dashboard";

    }

}

