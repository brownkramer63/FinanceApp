package com.cydeo.controller;

import com.cydeo.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping()

public class DashboardController {

    @GetMapping("/dashboard")
    public String navigateToDashboard(Model model){
        Map<String,Integer> map=  new TreeMap<>();
        map.put("totalCost",0);
        map.put("totalSales",0);
        map.put("profitLoss",0);
        model.addAttribute("summaryNumbers",map);
        return "dashboard";

    }

}

